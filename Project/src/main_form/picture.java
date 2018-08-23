package main_form;

import  java.awt.*;
import  java.awt.geom.*;
import  java.awt.event.*;
import  java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Random;
import java.util.TimerTask;
import java.util.Vector;

import  javax.swing.*;
import  javax.swing.event.*;
import  java.awt.image.*;
import  javax.imageio.*;
import  javax.swing.filechooser.FileFilter;
import javax.swing.text.DefaultEditorKit;

import component.OpenDialogLatLon;
import component.SizeDialog;
import component.dataBox;
import forSerealizate.optionStore;
import function.CopyImage;
import function.ExtractFile;
import function.RepaintPr;
import function.SubPaint;
import function.Zalivka;
import function.ZalivkaAndSearch;
import function.fileWorker;
import function.getStartCoordinate;
import function.reCategoriesColour;
import main_form.picture.TextFileFilter;
import map_modification.Write;
import type.coordinateXY;
import type.mas4;
import map_modification.Read;
 
public class picture
{
    static workAreas wa;// Режим рисования
	/*
	 * 0 - ничего
	 * 1 - перетаскивание
	 * 2 - выделение
	 * 3 - правый левый (середина)
	 */
	int  vArea = 1;//Множитель размера области определяющей пиксель
	int  brush_size = 1;
	
	int  pen_size = 1;
	int  sost=0;//Позиция нажатия
    int  rezhim=0;
    int  xPad;
    int  xf, yf;//Координаты начальной точки линии
    int  yPad;
    int  thickness;
    static Timer time;
    private int tool = 0;//Инструмент использованный на предыдущем шаге
    boolean pressed=false;
    int _x = 0, _y = 0;//Позиция в которой происходит вставка из буфера
    static Color maincolor;// текущий цвет
    static MyFrame f;//Текущее окно
    JCheckBox grid;//Определяет состояние отрисовки сетки
    JCheckBox checkBox;//Определяет состояние
    static MyPanel japan;//Панель на которой происходит рисование
    static int scale = 0;//Ноль чистая картинка;
    static BufferedImage imag;// поверхность рисования
    boolean loading=false;// если мы загружаем картинку
    static int tx, ty;//координаты крсора на холсте
    static String fileName;
    static JTextPane index;//Панель куда будем вставлять данные
    JLabel label;
    ButtonGroup ColorGroup = new ButtonGroup(); //Группа цветов(группа кнопок)
    static JPanel panel_;//Панель на которой рисуем
    /*
    public static BufferedImage deepCopy(BufferedImage bi) {
	    ColorModel cm = bi.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	*/
    
    /**
     * Запуск инструмента аэрозоль
     * @param x координата x
     * @param y координата y
     * @param basicStroke 
     */
    public static void startAerosol(int x, int y, BasicStroke basicStroke) {
    	Graphics g = imag.getGraphics();
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(basicStroke);
        g2.setColor(maincolor);
        
        Random rn = new Random();
        int mix = 0, max = 20 * (int)Math.pow(2.0, scale);//x
        int miy = 0, may = 20 * (int)Math.pow(2.0, scale);//y
        
        
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
          	  int rx1 = rn.nextInt((max - mix + 1) + mix) ,ry1 = rn.nextInt((may - miy + 1) + miy);
          	  int rx2 = rn.nextInt((max - mix + 1) + mix) ,ry2 = rn.nextInt((may - miy + 1) + miy);
          	  int rx3 = rn.nextInt((max - mix + 1) + mix) ,ry3 = rn.nextInt((may - miy + 1) + miy);
          	  int rx4 = rn.nextInt((max - mix + 1) + mix) ,ry4 = rn.nextInt((may - miy + 1) + miy);
          	  //rn.nextInt((max - min + 1) + min);

          	  g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)));
       	  
          	  g2.drawLine(x + rx1, y + ry1, x + rx1, y + ry1);
          	  g2.drawLine(x - rx2, y + ry2, x - rx2, y + ry2);
          	  g2.drawLine(x + rx3, y - ry3, x + rx3, y - ry3);
          	  g2.drawLine(x - rx4, y - ry4, x - rx4, y - ry4);
          	  wa.setImage(imag);//Сохраним в буфере
          	  japan.repaint(); 
            }
        };
        if(time == null || !time.isRunning())
        {
        	time = new Timer(100, taskPerformer);//Таймер
        	time.start();//Пуск
        }
        else
        {
        	time.stop();
        	time = new Timer(100, taskPerformer);//Таймер
        	time.start();//Пуск
        }
        wa.setImage(imag);//Сохраним в буфере
    }
    
    /**
     * JPanel - paint area
     * JMenuBar - main menu
     * JPanel - current collor (for view)
     * JButton - Array of buttons with a palette
     * JButton - Array of buttons with a function (pen, brush, eraser, line, oval, square, pouring, pipette, aerosol, text??)
     * JLabel - mouse coordinete visualisation
     * JEditorPane - file index text
     * JButton - scale less
     * JButton - scale more
     * JButton - scale 100%
     * ScrollPane - scroll panel
     * JButton - rotate 90 grad
     * JLabel - area size
     * JCheckBox - grid in map
     * JCheckBox - current pixel
     * JLabel - coordinate
     * JPanel - colour panel
     * work_form - main windows
     * JtabbedPane instrumental panel
     * JLabel - size label
     */
    public picture(JPanel f, JMenuBar menuBar, JPanel colourPane, JButton masBt[], JButton masFunc[], JLabel lblNewLabel, JTextPane index, JButton btn1, JButton btn2, JButton btn3, ScrollPane scrollPane, JButton rotateBtn, JLabel label, JCheckBox grid, JCheckBox checkBox, JPanel panel_50, work_form frame, JTabbedPane tabbedPane, JLabel label_2)
    {
    	this.grid = grid;
    	this.index = index;
    	this.panel_ = f;
    	this.checkBox = checkBox;
    	wa = new workAreas(label);
        maincolor=colourPane.getBackground();
        
        JMenu fileMenu = new  JMenu("Файл");
        menuBar.add(fileMenu);
        JMenu OptionMenu = new JMenu("Опции");
        menuBar.add(OptionMenu);
        JMenu editMenu = new  JMenu("Редактировать");
        menuBar.add(editMenu);
        JMenu helpMenu = new  JMenu("Помощь");
        menuBar.add(helpMenu);
       /*
        JMenu undoMenu = new  JMenu("Назад");
        editMenu.add(undoMenu);
        JMenu redoMenu = new  JMenu("Вперёд");
        editMenu.add(redoMenu);
        */
        JMenu landscape = new JMenu("Цветовая схема");
        OptionMenu.add(landscape);
           
        Action landColor1Action = new  AbstractAction("IGBP-Modified MODIS 20")
        {
           public void actionPerformed(ActionEvent event)
           {
        	   reCategoriesColour.isCategories(masBt, 1);
        	   maincolor = masBt[0].getBackground();//Сброс цвета
        	   panel_50.setBackground(maincolor);
        	   //landColor1.setIcon(new ImageIcon(SizeDialog.class.getResource("/resource/pen.png")));
           }
        };
        JRadioButtonMenuItem landColor1 = new  JRadioButtonMenuItem(landColor1Action);
        landscape.add(landColor1);
        ColorGroup.add(landColor1);
        
        Action landColor2Action = new  AbstractAction("USGS 24")
        {
           public void actionPerformed(ActionEvent event)
           {
        	   reCategoriesColour.isCategories(masBt, 2);
        	   maincolor = masBt[0].getBackground();//Сброс цвета
        	   panel_50.setBackground(maincolor);
           }
        };
        JRadioButtonMenuItem landColor2 = new  JRadioButtonMenuItem(landColor2Action);
        landscape.add(landColor2);
        ColorGroup.add(landColor2);
        
        Action landColor3Action = new  AbstractAction("34 category")
        {
           public void actionPerformed(ActionEvent event)
           {
        	   reCategoriesColour.isCategories(masBt, 3);
        	   maincolor = masBt[0].getBackground();//Сброс цвета
        	   panel_50.setBackground(maincolor);
           }
        };
        JRadioButtonMenuItem landColor3 = new  JRadioButtonMenuItem(landColor3Action);
        landscape.add(landColor3);
        ColorGroup.add(landColor3);
        
        Action landColor4Action = new  AbstractAction("21 category")
        {
           public void actionPerformed(ActionEvent event)
           {
        	   reCategoriesColour.isCategories(masBt, 4);
        	   maincolor = masBt[0].getBackground();//Сброс цвета
        	   panel_50.setBackground(maincolor);
           }
        };
        JRadioButtonMenuItem landColor4 = new  JRadioButtonMenuItem(landColor4Action);
        landscape.add(landColor4);
        ColorGroup.add(landColor4);
        
        reCategoriesColour.isCategories(masBt, 1);//Выбор цветовой схемы №2
 	    maincolor = masBt[0].getBackground();//Сброс цвета на цвет категории 1
 	    ColorGroup.getElements().nextElement().setSelected(true);
 	    panel_50.setBackground(maincolor);
 	    
 	    Action webAction = new AbstractAction("Справка (WEB)")
 	    		{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ExtractFile ex = new ExtractFile();
					    ex.extract("/resource/info3.html", "info.html");
					    try {
							Thread.sleep(4000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					    
						try {
					    	Desktop desktop = Desktop.getDesktop();
							System.err.println(desktop.isSupported(Desktop.Action.OPEN));
							desktop.open(new java.io.File("info.html"));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
 	    	
 	    		};
 	    JMenuItem webMenu = new  JMenuItem(webAction);
 	    helpMenu.add(webMenu);
 	           
        Action infAction = new  AbstractAction("Справка (Локальная версия)")
        {
           public void actionPerformed(ActionEvent event)
           {//
        	   /*
        	   InputStream initialStream = getClass().getResourceAsStream("/resource/info.html");
				byte[] buffer = null;
				try {
					buffer = new byte[initialStream.available()];
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    try {
					initialStream.read(buffer);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
			    OutputStream outStream = null;
				try {
					outStream = new FileOutputStream(new File("info1.html"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    try {
					outStream.write(buffer,0, buffer.length);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    try {
					outStream.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			    */
			    ExtractFile ex = new ExtractFile();
			    ex.extract("/resource/info.html", "info1.html");
			    ex.extract("/resource/info — копия_html_m71077ef4.png", "info — копия_html_m71077ef4.png");
			    ex.extract("/resource/info — копия_html_m3195a728.gif", "info — копия_html_m3195a728.gif");
			    ex.extract("/resource/info — копия_html_m497c9e1b.gif", "info — копия_html_m497c9e1b.gif");
			    ex.extract("/resource/info — копия_html_m18f243ac.gif", "info — копия_html_m18f243ac.gif");
			    ex.extract("/resource/info — копия_html_m7e8d73fa.png", "info — копия_html_m7e8d73fa.png");	    
			    ex.extract("/resource/info — копия_html_17247d6d.png", "info — копия_html_17247d6d.png");
			    ex.extract("/resource/info — копия_html_5327f1f4.png", "info — копия_html_5327f1f4.png");
			    ex.extract("/resource/info — копия_html_33e8741a.gif", "info — копия_html_33e8741a.gif");
			    ex.extract("/resource/info — копия_html_10b115b8.gif", "info — копия_html_10b115b8.gif");
			    ex.extract("/resource/info — копия_html_4de0c476.png", "info — копия_html_4de0c476.png");
			    try {
					Thread.sleep(4000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    try {
			    	Desktop desktop = Desktop.getDesktop();
					System.err.println(desktop.isSupported(Desktop.Action.OPEN));
					//desktop.open(new java.io.File("C:\\Users\\megroup9gmail.com\\Pictures\\info1.pdf"));
					desktop.open(new java.io.File("info1.html"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    /*
			    Runtime r = Runtime.getRuntime();
			    try {
			    	r.exec("C:\\Users\\megroup9gmail.com\\Pictures\\1.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
           }
        };
        JMenuItem infMenu = new  JMenuItem(infAction);
        helpMenu.add(infMenu);
        
        //Копировать
        Action rMenuAction = new  AbstractAction("Вперёд")
        {
           public void actionPerformed(ActionEvent event)
           {
        	   /*
        	   wa.r_snapshot();   
        	   imag = wa.getImage();
   	  	       japan.repaint();
   	  	       */
        	   funcVC_Y();
           }
        };
        rMenuAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control + Y"));
        JMenuItem rMenu = new  JMenuItem(rMenuAction);
        editMenu.add(rMenu);
        
        Action lMenuAction = new  AbstractAction("Назад")
        {
           public void actionPerformed(ActionEvent event)
           {
        	   /*
        	   wa.l_snapshot();
        	   imag = wa.getImage();
   	  	       japan.repaint();
   	  	       */
        	   funcVC_Z();
           }
        };
        rMenuAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control + Z"));
        JMenuItem lMenu = new  JMenuItem(lMenuAction);
        editMenu.add(lMenu);
        
        editMenu.addSeparator();
        
        Action copyMenuAction = new  AbstractAction("Копировать")
        {
           public void actionPerformed(ActionEvent event)
           {
        	   //wa.setBufer();
        	   funcVK_C();
           }
        };
        rMenuAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control + C"));
        JMenuItem copyMenu = new  JMenuItem(copyMenuAction);
        editMenu.add(copyMenu);
        
        Action cutMenuAction = new  AbstractAction("Вырезать")
        {
           public void actionPerformed(ActionEvent event)
           {
        	   /*
        	   	//Скопируем в буфер
        	   	wa.setBufer();
        	   	//Закрасим
        	   	imag = wa.getImage();//Уберём рамку
   		  		Graphics g = imag.getGraphics();
   		  		wa.paintDelThisArea(g);//Перекрасим область под выделением
   		  		wa.setImage(imag);//Примем изображение за эталон   		  
   		  		wa.ifArea();//Отметим что рамка не отрисовывается
   		  		japan.repaint();
   		  		wa.l_snapshot();
   		  		*/
        	   funcVC_X();
           }
        };
        rMenuAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control + X"));
        JMenuItem cutMenu = new  JMenuItem(cutMenuAction);
        editMenu.add(cutMenu);
        
        Action pasteMenuAction = new  AbstractAction("Вставить")
        {
           public void actionPerformed(ActionEvent event)
           {
        	   this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control + V"));
        	   funcVC_V();
        	   /*
        	   //Получим изображение из буфера
        	  	  wa.paste();
        	  	  //Прорисуем изображение на экране
        	  	  //imag = wa.getImage();
        	        //wa.dDrawImage(imag);
        	  	  //japan.repaint();
        	  	  imag = wa.getImage();
        			  Graphics g = imag.getGraphics();
        	        Graphics2D g2 = (Graphics2D)g;
        	        wa.paintDelArea(g2);//Закрасим исходную область
        	        //wa.isResize70(e.getX(), e.getY());
        	        wa.dDrawImage(imag);
        	  	  wa.Draw(g);//Рисует
        	  	  //lblNewLabel.setText(e.getX()+"; "+e.getY()+" ПКС");
        	  	  japan.repaint();
        	  	  wa.l_snapshot();
        	  	  */
           }
        };
        JMenuItem pasteMenu = new  JMenuItem(pasteMenuAction);
        editMenu.add(pasteMenu);
        
        Action flushAction = new  AbstractAction("Очистить")
        {
           public void actionPerformed(ActionEvent event)
           {
        	   Graphics g = imag.getGraphics();
               Graphics2D g2 = (Graphics2D)g;
               g2.setColor(Color.WHITE);
        	   g2.fill(new Rectangle2D.Float(0, 0, imag.getWidth(), imag.getHeight()));
        	   wa.setImage(imag);//Сохраним в буфере
        	   japan.repaint();
        	   wa.snapshot();//Делаем снимок области
           }
        };
        JMenuItem flushMenu = new  JMenuItem(flushAction);
        editMenu.add(flushMenu); 
        
        Action loadMapAction_ = new  AbstractAction("Открыть")
        {
           public void actionPerformed(ActionEvent event)
           {
              JFileChooser jf= new  JFileChooser();
              int  result = jf.showOpenDialog(null);
               if(result==JFileChooser.APPROVE_OPTION)
                {
            	   if(jf.getSelectedFile().getAbsolutePath().indexOf(".jpg")!=-1 || jf.getSelectedFile().getAbsolutePath().indexOf(".png")!=-1 )
            	   {
            		   try
                       {
                     	  //Подстраиваем размеры формы и панели под размеры изображения
                            fileName = jf.getSelectedFile().getParent();
                            File iF= new  File(fileName);
                            jf.addChoosableFileFilter(new  TextFileFilter(".png"));
                            jf.addChoosableFileFilter(new  TextFileFilter(".jpg"));
                            imag = ImageIO.read(iF);
                            loading=true;
                            //f.setSize(imag.getWidth()+40, imag.getWidth()+80);
                            f.setPreferredSize(new Dimension(imag.getWidth()+40, imag.getHeight()+80));
                            japan.setSize(imag.getWidth(), imag.getWidth());
                            japan.repaint();
                            wa.setImage(imag);//Сохраним в буфер
                            f.updateUI();
                            wa.snapshot();//Делаем снимок области
                         } catch (FileNotFoundException ex) {
                             JOptionPane.showMessageDialog(f, "Такого файла не существует");
                         } 
                         catch (IOException ex) {
                             JOptionPane.showMessageDialog(f, "Исключение ввода-вывода");
                         }
                       catch (Exception ex) {
                         }
            		   //Сохраним данные и вывелем имя файла
            		   optionStore.setFile_PathName(jf.getSelectedFile().getParent(), jf.getSelectedFile().getName());
            		   frame.setTitle("Текущий ландшафт: " + optionStore.getOpenFileName());
                   }
            	   else
            	   if(jf.getSelectedFile().getAbsolutePath().indexOf(".GRD")!=-1 || jf.getSelectedFile().getAbsolutePath().indexOf(".grd")!=-1)
            	   {
            		   try
                       {
                           //Подстраиваем размеры формы и панели под размеры изображения
                            fileName = jf.getSelectedFile().getParent();
                            	String rd[];
                    			rd = new String[3];
                    			//rd[0]="38401-39600.18001-19200";
                    			rd[0]=jf.getSelectedFile().getName();
                    			rd[1]="Output";
                    			rd[2]="GRD";
                    			
                            //imag = ImageIO.read(iF);
                            //loading=true;
                    			Read rdd =new Read();
                    			
                    			imag = rdd.readData(rd, jf.getSelectedFile().getParent()+"\\",index);
                            
                            //f.setSize(imag.getWidth()+40, imag.getWidth()+80);
                            f.setPreferredSize(new Dimension(imag.getWidth()+40, imag.getHeight()+80));
                            japan.setSize(imag.getWidth(), imag.getWidth());
                            japan.repaint();
                            wa.setImage(imag);//Сохраним в буфер
                            f.updateUI();
                            wa.snapshot();//Делаем снимок области
                         } catch (FileNotFoundException ex) {
                             JOptionPane.showMessageDialog(f, "Такого файла не существует");
                         } 
                         catch (IOException ex) {
                             JOptionPane.showMessageDialog(f, "Исключение ввода-вывода");
                         }
                       catch (Exception ex) {
                         }
            		   //Сохраним данные и вывелем имя файла
            		   optionStore.setFile_PathName(jf.getSelectedFile().getAbsolutePath(), jf.getSelectedFile().getName());
            		   frame.setTitle("Текущий ландшафт: " + optionStore.getOpenFileName());
            	   }
            	   else
            	   if(jf.getSelectedFile().getAbsolutePath().indexOf(".ASC")!=-1 || jf.getSelectedFile().getAbsolutePath().indexOf(".asc")!=-1)
            	   {
            		   try
                       {
                           //Подстраиваем размеры формы и панели под размеры изображения
                            fileName = jf.getSelectedFile().getParent();
                            	String rd[];
                    			rd = new String[3];
                    			//rd[0]="38401-39600.18001-19200";
                    			rd[0]=jf.getSelectedFile().getName();
                    			rd[1]="Output";
                    			rd[2]="ASC";
                    			
                            //imag = ImageIO.read(iF);
                            //loading=true;
                    			Read rdd =new Read();
                    			
                    			imag = rdd.readData(rd, jf.getSelectedFile().getParent()+"\\",index);
                            
                            //f.setSize(imag.getWidth()+40, imag.getWidth()+80);
                            f.setPreferredSize(new Dimension(imag.getWidth()+40, imag.getHeight()+80));
                            japan.setSize(imag.getWidth(), imag.getWidth());
                            japan.repaint();
                            wa.setImage(imag);//Сохраним в буфер
                            f.updateUI();
                            wa.snapshot();//Делаем снимок области
                         } catch (FileNotFoundException ex) {
                             JOptionPane.showMessageDialog(f, "Такого файла не существует");
                         } 
                         catch (IOException ex) {
                             JOptionPane.showMessageDialog(f, "Исключение ввода-вывода");
                         }
                       catch (Exception ex) {
                         }
            		   //Сохраним данные и вывелем имя файла
            		   optionStore.setFile_PathName(jf.getSelectedFile().getParent(), jf.getSelectedFile().getName());
            		   frame.setTitle("Текущий ландшафт: " + optionStore.getOpenFileName());
            	   }
            	   else
            	   {
            		   try
            		   {
                        //Подстраиваем размеры формы и панели под размеры изображения
                        //fileName = jf.getSelectedFile().getAbsolutePath();
            			fileName = jf.getSelectedFile().getAbsolutePath();
                       	String rd[];
               			rd = new String[3];
               			//rd[0]="38401-39600.18001-19200";
               			rd[0]=jf.getSelectedFile().getName();
               			rd[1]="Output";
               			rd[2]="PNG";
                        //imag = ImageIO.read(iF);
                        //loading=true;
               			Read rdd =new Read();
               			imag = rdd.readData(rd, jf.getSelectedFile().getParent()+"\\",index);
                        //f.setSize(imag.getWidth()+40, imag.getWidth()+80);
                        f.setPreferredSize(new Dimension(imag.getWidth()+40, imag.getHeight()+80));
                        japan.setSize(imag.getWidth(), imag.getWidth());
                        japan.repaint();
                        wa.setImage(imag);//Сохраним в буфер
                        f.updateUI();
                        wa.snapshot();//Делаем снимок области
                    } 
            		    catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(f, "Такого файла не существует");
                    } 
            		    catch (IOException ex) {
                        JOptionPane.showMessageDialog(f, "Исключение ввода-вывода");
                    }
            		    catch (Exception ex) {
                    }
            		   //Сохраним данные и вывелем имя файла
            		   optionStore.setFile_PathName(jf.getSelectedFile().getParent(), jf.getSelectedFile().getName());
            		   frame.setTitle("Текущий ландшафт: " + optionStore.getOpenFileName());
            		   //Далее выберем категорию цветов
            		   if(dataBox.category_max == 20)
            		   {
            			   reCategoriesColour.isCategories(masBt, 1);
            			   maincolor = masBt[0].getBackground();//Сброс цвета
            			   panel_50.setBackground(maincolor);
            		   }
            		   else
            			   if(dataBox.category_max == 24)
            			   {
            				   reCategoriesColour.isCategories(masBt, 2);
            				   maincolor = masBt[0].getBackground();//Сброс цвета
            				   panel_50.setBackground(maincolor);
            			   }
            		   			if(dataBox.category_max == 21)
            		   			{
            		   				reCategoriesColour.isCategories(masBt, 4);
            		   				maincolor = masBt[0].getBackground();//Сброс цвета
            		   				panel_50.setBackground(maincolor);
            		   			}
            		   			else
            		   			{
            		   				reCategoriesColour.isCategories(masBt, 3);
            		   				maincolor = masBt[0].getBackground();//Сброс цвета
            		   				panel_50.setBackground(maincolor);
            		   			}
            	   }
            	   
            	   
            	   
                }
               scale = 0;
              }
            };
        JMenuItem loadMapMenu_ = new  JMenuItem(loadMapAction_);
        fileMenu.add(loadMapMenu_);
        
        Action openSearchMapAction = new  AbstractAction("Открыть файл с точкой...")
        {
           public void actionPerformed(ActionEvent event)
           {
        	  OpenDialogLatLon dialog = null;
     		  if(dialog == null)
					try {
						dialog = new OpenDialogLatLon();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
     		  dialog.setVisible(true);
     		//Сохраним имя файла
   		   frame.setTitle("Текущий ландшафт: " + optionStore.getOpenFileName());
     		//Далее выберем категорию цветов
   		   if(dataBox.category_max == 20)
   		   {
   			   reCategoriesColour.isCategories(masBt, 1);
   			   maincolor = masBt[0].getBackground();//Сброс цвета
   			   panel_50.setBackground(maincolor);
   		   }
   		   else
   			   if(dataBox.category_max == 24)
   			   {
   				   reCategoriesColour.isCategories(masBt, 2);
   				   maincolor = masBt[0].getBackground();//Сброс цвета
   				   panel_50.setBackground(maincolor);
   			   }
   		   		if(dataBox.category_max == 21)
   		   		{
   		   			reCategoriesColour.isCategories(masBt, 4);
   		   			maincolor = masBt[0].getBackground();//Сброс цвета
   		   			panel_50.setBackground(maincolor);
   		   		}
   		   		else
   		   		{
   		   			reCategoriesColour.isCategories(masBt, 3);
   		   			maincolor = masBt[0].getBackground();//Сброс цвета
   		   			panel_50.setBackground(maincolor);
   		   		}
           }
        };
        JMenuItem openSearchMap = new  JMenuItem(openSearchMapAction);
        fileMenu.add(openSearchMap);
        
        //JMenu ldMenu = new  JMenu("Открыть");
        //fileMenu.add(ldMenu);
        Action saveSimpleMapAction = new  AbstractAction("Сохранить")
        {
           public void actionPerformed(ActionEvent event)
           {
        	 //Смасштабируем до исходной величины/////////////////////////////////////////////////////////////////////////////////////////
        	   if(scale < 0)
     		  {
     			  for(int i=scale; i<0; i++) {
     				  scale+=1;
             		  BufferedImage scaled = new BufferedImage(imag.getWidth()*2, imag.getHeight()*2,BufferedImage.TYPE_INT_RGB);
                       Graphics2D g2 = scaled.createGraphics();
                       g2.drawImage(imag, 0, 0, imag.getWidth()*2, imag.getHeight()*2, null);
                       imag=scaled;
                       
     			  }
     		  }
     		  if(scale > 0)
     		  {
     			  for(int i=scale; i>0; i--) {
     				  scale-=1;
     				  BufferedImage scaled = new BufferedImage(imag.getWidth()/2, imag.getHeight()/2,BufferedImage.TYPE_INT_RGB);
                       Graphics2D g2 = scaled.createGraphics();
                       g2.drawImage(imag, 0, 0, imag.getWidth()/2, imag.getHeight()/2, null);
                       imag=scaled;
     			  }
     		  }
     		  
     		  f.setPreferredSize(new Dimension(imag.getWidth()+40, imag.getWidth()+80));
               japan.setSize(imag.getWidth(), imag.getWidth());
               japan.repaint();
               scrollPane.setScrollPosition(0, 0);//Позиция скрола (надо пересчитать через процентное сотношение)
               wa.setImage(imag);
               scrollPane.repaint();
               label_2.setText("Масштаб: 100%");
               //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
               ////////////////////////////////////////////////////////////////Изображение///////////////////////////////////////////////////
               String ss = optionStore.getOpenFilePath();
        	   if(optionStore.getOpenFilePath().indexOf(".png")==0 || optionStore.getOpenFilePath().indexOf(".jpg")==0)
        	   {
        		   try
                   {
                     fileName = optionStore.getOpenFilePath()+"\\"+optionStore.getOpenFileName();
                     // Смотрим какой фильтр выбран
                     if(optionStore.getOpenFilePath().indexOf(".png")!=0)
                       {
                            ImageIO.write(imag, "png", new  File(fileName));
                       }
                    else
                       {
                           ImageIO.write(imag, "jpeg", new  File(fileName));
                       }             
                   }
                   catch(IOException ex)
                   {
                      JOptionPane.showMessageDialog(f, "Ошибка ввода-вывода");
                   }
        	   }
        	   ////////////////////////////////////////////////////////Ландшафт//////////////////////////////////////////////////////////////
        	   else
        	   {
        		String indexPath = "", savePath = "";
                   	savePath = optionStore.getOpenFilePath()+"\\"+optionStore.getOpenFileName();
                   	indexPath = optionStore.getOpenFilePath()+"\\index";
               
                   if(savePath.equals(""))
                   {
                   	JOptionPane.showMessageDialog(null, "Имя файла не выбрано");
                   }
                   else
                   {
                   	//Сохраним
                   	String rd[];
                   	rd = new String[3];
                   	rd[1]=savePath;
                   	try {
                   		fileWorker fw = new fileWorker();
                   		if(index.getText().equals("")) JOptionPane.showMessageDialog(null, "index файл не найден или метаданные не заполненны");

                   		fw.WriteFile(indexPath, index.getText());
                   		
                   		Write wr = new Write();
                   		wr.writeData(rd, indexPath, imag);	
                   		
                   	} catch (Exception e) {
                   		// TODO Auto-generated catch block
                   		e.printStackTrace();
                   	}
                   }
        	   }
           }
        };
        JMenuItem saveSimpleMapMenu = new  JMenuItem(saveSimpleMapAction);
        fileMenu.add(saveSimpleMapMenu);
        
        
        JMenu sdMenu = new  JMenu("Сохранить как...");
        fileMenu.add(sdMenu);
        
        Action saveMapAction = new  AbstractAction("Ландшафт")
        {
           public void actionPerformed(ActionEvent event)
           {
        	   //Смасштабируем до исходной величины/////////////////////////////////////////////////////////////////////////////////////////
        	   if(scale < 0)
     		  {
     			  for(int i=scale; i<0; i++) {
     				  scale+=1;
             		  BufferedImage scaled = new BufferedImage(imag.getWidth()*2, imag.getHeight()*2,BufferedImage.TYPE_INT_RGB);
                       Graphics2D g2 = scaled.createGraphics();
                       g2.drawImage(imag, 0, 0, imag.getWidth()*2, imag.getHeight()*2, null);
                       imag=scaled;
                       
     			  }
     		  }
     		  if(scale > 0)
     		  {
     			  for(int i=scale; i>0; i--) {
     				  scale-=1;
     				  BufferedImage scaled = new BufferedImage(imag.getWidth()/2, imag.getHeight()/2,BufferedImage.TYPE_INT_RGB);
                       Graphics2D g2 = scaled.createGraphics();
                       g2.drawImage(imag, 0, 0, imag.getWidth()/2, imag.getHeight()/2, null);
                       imag=scaled;
     			  }
     		  }
     		  
     		  f.setPreferredSize(new Dimension(imag.getWidth()+40, imag.getWidth()+80));
               japan.setSize(imag.getWidth(), imag.getWidth());
               japan.repaint();
               scrollPane.setScrollPosition(0, 0);//Позиция скрола (надо пересчитать через процентное сотношение)
               wa.setImage(imag);
               scrollPane.repaint();
               label_2.setText("Масштаб: 100%");
               //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        	   
        	   String indexPath = "", savePath = "";
                	//Укажем путь куда сохранять
                	JFileChooser jf1= new  JFileChooser(optionStore.getOpenFilePath());/////////////////////////////////////////Автовыбор каталога при сохранении
        	   		// Создаем фильтры для файлов
                	TextFileFilter noFilter = new  TextFileFilter("");
                	// Добавляем фильтры
                	jf1.addChoosableFileFilter(noFilter);
                    int  results = jf1.showSaveDialog(null);
                    if(results==JFileChooser.APPROVE_OPTION)
                    {
                    	savePath = jf1.getSelectedFile().getAbsolutePath();
                    	indexPath = jf1.getSelectedFile().getParent()+"\\index";
                    }
                
                    if(savePath.equals(""))
                    {
                    	JOptionPane.showMessageDialog(null, "Имя файла не выбрано");
                    }
                    else
                    {
                    	//Сохраним
                    	String rd[];
                    	rd = new String[3];
                    	rd[1]=savePath;
                    	try {
                    		fileWorker fw = new fileWorker();
                    		if(index.getText().equals("")) JOptionPane.showMessageDialog(null, "index файл не найден или метаданные не заполненны");

                    		fw.WriteFile(indexPath, index.getText());
                    		
                    		Write wr = new Write();
                    		wr.writeData(rd, indexPath, imag);	
                    		
                    	} catch (Exception e) {
                    		// TODO Auto-generated catch block
                    		e.printStackTrace();
                    	}
                    }	
           }
        };
        JMenuItem saveMapMenu = new  JMenuItem(saveMapAction);
        sdMenu.add(saveMapMenu);
        
        //JMenu mapMenu = new  JMenu("Ландшафт");
        //sdMenu.add(mapMenu);
        Action saveasAction = new  AbstractAction(".jpg файл")
        {
           public void actionPerformed(ActionEvent event)
           {
               try
               {
                   JFileChooser jf= new  JFileChooser();
                   // Создаем фильтры для файлов
                   TextFileFilter pngFilter = new  TextFileFilter(".png");
                   TextFileFilter jpgFilter = new  TextFileFilter(".jpg");
                       // Добавляем фильтры
                        jf.addChoosableFileFilter(pngFilter);
                         jf.addChoosableFileFilter(jpgFilter);
                       int  result = jf.showSaveDialog(null);
                       if(result==JFileChooser.APPROVE_OPTION)
                       {
                           fileName = jf.getSelectedFile().getAbsolutePath();
                       }
                 // Смотрим какой фильтр выбран
                 if(jf.getFileFilter()==pngFilter)
                   {
                        ImageIO.write(imag, "png", new  File(fileName+".png"));
                   }
                else
                   {
                       ImageIO.write(imag, "jpeg", new  File(fileName+".jpg"));
                   }             
               }
               catch(IOException ex)
               {
                  JOptionPane.showMessageDialog(f, "Ошибка ввода-вывода");
               }
           }
        };
        JMenuItem saveasMenu = new  JMenuItem(saveasAction);
        sdMenu.add(saveasMenu);

        Action infoAction = new  AbstractAction("О программе")
        {
           public void actionPerformed(ActionEvent event)
           {
        	   JOptionPane J = new JOptionPane();
        	   JOptionPane.showMessageDialog(J,"<html><h2>Редактор ландшафтов</h2><i>Разработчик: ***</i>");
           }
        };
        JMenuItem infoMenu = new  JMenuItem(infoAction);
        fileMenu.add(infoMenu);
        
        
        japan = new  MyPanel();
        japan.setBounds(15,15,540,385);//Поля слева сверху
        japan.setBackground(Color.white);
        japan.setOpaque(true);
        f.add(japan);
        
        /*
        //инициализация буфера
        imag1 = new  BufferedImage(japan.getWidth(), japan.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D d22 = (Graphics2D) imag1.createGraphics();
        d22.setColor(Color.white);
        d22.fillRect(0, 0, japan.getWidth(), japan.getHeight());
        */
        wa = new workAreas(japan.getWidth(), japan.getHeight());//инициализация буфера
        
///////////////////////////////////////////////////////////////////////////////////////////////////
        //Сопоставим кнопки и действия
        for(int i = 0; i < masFunc.length; i++)
        {
        	final int ci=i;
        	masFunc[ci].addActionListener(new  ActionListener()
            {
              public void actionPerformed(ActionEvent event)
              {
            	  //System.out.println("ass=  "+ci);
            	  rezhim=ci;
            	  
            	  //Достанем изображение из буфера если это не первое выделение
            	  if(ci!= 11 && wa.getImage()!=null)
            		  imag = wa.getImage();
            	  
            	  wa.ifArea();//Уберём область выделения
            	  
            	  //Перед выделением сохраним изображение в буфер
            	  if(rezhim == 11 && !wa.ifPaint()) {
            		  //sost = 2;
            		  wa.setImage(imag);
            	  }
            	  
            	  //Определим размер области выделения
            	  vArea = 1;
            	  switch(rezhim) {
            	  case 0:
            		  vArea = 1;
            		  break;
            	  case 1:
            		  vArea = 4;
            		  break;
            	  case 2:
            		  vArea = 6;
            		  break;
            	  }
            	  
            	  japan.repaint(); 
              }
            });
        	///////////////////////////////////////////////////klava////////////
        	masFunc[ci].addKeyListener(new KeyListener() {
       		  public void keyPressed(KeyEvent e) {
       		  }

       		public void keyReleased(KeyEvent e) {
       			if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()){
       				funcVK_C();
       		    }
       			if (e.getKeyCode() == KeyEvent.VK_V && e.isControlDown()){
       				funcVC_V();
       		    }
       			if (e.getKeyCode() == KeyEvent.VK_X && e.isControlDown()){
       				funcVC_X();
       		    }
       			if (e.getKeyCode() == KeyEvent.VK_Y && e.isControlDown()){
       				funcVC_Y();
       			}
       			if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown()){
       				funcVC_Z();
       			}
       			
       		}

       		  public void keyTyped(KeyEvent e) {
       		  //System.out.println("dfndjfnksdjfsdk");  
       		  }

       		  });
        	/////////////////////////////////////////////////////
        }
///////////////////////////////////////////////////////////////////////////////////////
          
          
          
//////////////////////////////////////////////////////////////////
          //Вешаем события на кнопки выбора цвета
          for(int i=0;i<masBt.length;i++) {
        	  final int y=i;
        	  masBt[y].addActionListener(new  ActionListener()
              {  
                public void actionPerformed(ActionEvent event)
                { 
                  maincolor = masBt[y].getBackground();
                }
              });
          }
//////////////////////////////////////////////////////////////////
          //Поворот изображения
          rotateBtn.addMouseListener(new MouseAdapter() {
        	  
        	  public void mouseClicked(MouseEvent event) {
        		  
        		  /*
        		  wa.RotateImage();
        		  //imag = wa.getImage();
        		  wa.dDrawImage(imag);//Нарисуем из буфера
      	  		//wa.setImage(imag);//Примем изображение за эталон
        		  japan.repaint();
        		  */
        		  
        		  
        		  //imag = wa.getImage();
        		  wa.RotateImage();       		  
        		  imag = wa.getImage();
    			  Graphics g = imag.getGraphics();
                  Graphics2D g2 = (Graphics2D)g;
                  wa.paintDelArea(g2);//Закрасим исходную область
                  wa.dDrawImage(imag);
                  wa.Draw(g);
            	  japan.repaint();
        		  
        		  /*
                    //imag1=imag;
                    Graphics g = imag.getGraphics();
                    Graphics2D g2 = (Graphics2D)g;
                    // установка цвета
                    g2.setColor(maincolor);
        		  
                    wa.Draw(g);
                    */

                    japan.repaint();
                    
                    
        	  }   
          });

//////////////////////////////////////////////////////////////////
          //Масштабирование -
          btn1.addMouseListener(new MouseAdapter() {
        	  
        	  public void mouseClicked(MouseEvent event) {
                  
        		if(scale > 0) {
        		  //Восстановим изображение
        		  imag = wa.getImage();//Уберём рамку
         		  wa.ifArea();//Отметим что рамка не отрисовывается
        		  //Масштабируем
        		  scale-=1;
        		  sost = 0;
                  BufferedImage scaled = new BufferedImage(imag.getWidth()/2, imag.getHeight()/2,BufferedImage.TYPE_INT_RGB);
                  Graphics2D g2 = scaled.createGraphics();
                  g2.drawImage(imag, 0, 0, imag.getWidth()/2, imag.getHeight()/2, null);
                  imag=scaled;
                  f.setPreferredSize(new Dimension(imag.getWidth()+40, imag.getWidth()+80));
                  japan.setSize(imag.getWidth(), imag.getWidth());
                  japan.repaint();
                  scrollPane.setScrollPosition(0, 0);//Позиция скрола (надо пересчитать через сотношение)
                  wa.setImage(imag);
                  scrollPane.repaint();
                  label_2.setText("Масштаб: "+Math.pow(2, scale)*100+"%");
        		}
        	  }
        	   
        	  });
          
          //Масштабирование +
          btn2.addMouseListener(new MouseAdapter() {
        	  
        	  public void mouseClicked(MouseEvent event) {

        		if(scale < 2) {
        		  //Восстановим изображение
          		  imag = wa.getImage();//Уберём рамку
           		  wa.ifArea();//Отметим что рамка не отрисовывается
          		  //Масштабируем
        		  scale+=1;
        		  sost = 0;
        		  BufferedImage scaled = new BufferedImage(imag.getWidth()*2, imag.getHeight()*2,BufferedImage.TYPE_INT_RGB);
                  Graphics2D g2 = scaled.createGraphics();
                  g2.drawImage(imag, 0, 0, imag.getWidth()*2, imag.getHeight()*2, null);
                  imag=scaled;
                  f.setPreferredSize(new Dimension(imag.getWidth()+40, imag.getWidth()+80));
                  japan.setSize(imag.getWidth(), imag.getWidth());
                  wa.setImage(imag);
                  japan.repaint();
                  label_2.setText("Масштаб: "+Math.pow(2, scale)*100+"%");
        		} 
        	  }
        	   
        	  });
          
        //Масштабирование 100%
          btn3.addMouseListener(new MouseAdapter() {
        	  
        	  public void mouseClicked(MouseEvent event) {
        		  if(scale < 0)
        		  {
        			  for(int i=scale; i<0; i++) {
        				  scale+=1;
                		  BufferedImage scaled = new BufferedImage(imag.getWidth()*2, imag.getHeight()*2,BufferedImage.TYPE_INT_RGB);
                          Graphics2D g2 = scaled.createGraphics();
                          g2.drawImage(imag, 0, 0, imag.getWidth()*2, imag.getHeight()*2, null);
                          imag=scaled;
                          
        			  }
        		  }
        		  if(scale > 0)
        		  {
        			  for(int i=scale; i>0; i--) {
        				  scale-=1;
        				  BufferedImage scaled = new BufferedImage(imag.getWidth()/2, imag.getHeight()/2,BufferedImage.TYPE_INT_RGB);
                          Graphics2D g2 = scaled.createGraphics();
                          g2.drawImage(imag, 0, 0, imag.getWidth()/2, imag.getHeight()/2, null);
                          imag=scaled;
        			  }
        		  }
        		  
        		  f.setPreferredSize(new Dimension(imag.getWidth()+40, imag.getWidth()+80));
                  japan.setSize(imag.getWidth(), imag.getWidth());
                  japan.repaint();
                  scrollPane.setScrollPosition(0, 0);//Позиция скрола (надо пересчитать через процентное сотношение)
                  wa.setImage(imag);
                  scrollPane.repaint();
                  label_2.setText("Масштаб: 100%");
                  
        	  }
        	   
        	  });
//////////////////////////////////////////////////////////////////////////////////////////////     
          japan.addMouseMotionListener(new  MouseMotionAdapter()
                  {
                      public void mouseDragged(MouseEvent e) 
                      {
                    	  //lblNewLabel.setText(e.getX()/(scale+1.0)+"; "+e.getY()/(scale+1.0)+" ПКС");
                    	  SetCooordinate(e.getX(), e.getY(), lblNewLabel);             	  
	                    	 if(rezhim>2) { //imag=deepCopy(imag1);//Уберём лишние части
	                    		if(rezhim == 11 && !wa.ifPaint())
	                    			  wa.setImage(imag);
	                    		//Восстановим изображение
	                    	  	imag = wa.getImage();
	                    	  	//Скорректируем изображение (добавим данные из области выделения к рисунку)
	                    	  }
	                          if (pressed==true)
	                          {
	                          //imag1=imag;
	                          Graphics g = imag.getGraphics();
	                          Graphics2D g2 = (Graphics2D)g;
	                          // установка цвета
	                          g2.setColor(maincolor);
	                          
	                          // Общие рассчеты для овала и прямоугольника и выделения
	                          int x1=xf, y1=yf, x2=e.getX(), y2=e.getY();
	                          if(xf>xPad)
	                          {
	                          	x2=xf; x1=xPad; 
	                          }
	                          if(yf>yPad)
	                          {
	                          	y2=yf; y1=yPad; 
	                          }
	                          Double vv;
	                          int tx_;
                         	  int ty_;
                         	  int tx_1;
                         	  int ty_1;
                         	  
                         	  tx = e.getX();
   	                          ty = e.getY();    
	                          switch (rezhim)
	                          {
	                              // карандаш
	                              case 0:
	                            	  if(scale == 0)
	                            	  {
	                            		  g2.setStroke(new  BasicStroke(pen_size+1.0f*scale));
	                            		  g2.drawLine(xPad, yPad, e.getX(), e.getY());
	                            		  wa.setImage(imag);//Сохраним в буфере
	                            	  }
	                            	  else
	                            	  {
	                            		  vv = Math.pow(2, scale);
	                                 	  tx_ = (int) ((Math.floor(xPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
	                                 	  ty_ = (int) ((Math.floor(yPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
	                                 	  g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)));
	                                 	  g2.drawLine(tx_, ty_, tx_, ty_);
	                                 	  wa.setImage(imag);//Сохраним в буфере 
	                            	  }
	                            	  vArea = 1;
	                                  break;
	                              // кисть
	                              case 1:
	                            	  vArea = 4;
	                            		  //Найдём реальные координаты пикселя
	                            		  vv = Math.pow(2, scale);
	                                 	  tx_ = (int) ((Math.floor(xPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
	                                 	  ty_ = (int) ((Math.floor(yPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
	                                 	  tx_1 = (int) ((Math.floor(e.getX() / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
	                                 	  ty_1 = (int) ((Math.floor(e.getY() / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
	                                 	  g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)*vArea));
	                                 	  g2.drawLine(tx_, ty_, tx_1, ty_1);
	                                 	  wa.setImage(imag);//Сохраним в буфере
	                                  break;
	                              // ластик
	                              case 2:
	                            	      vArea = 6;
	                            		  vv = Math.pow(2, scale);
	                            		  tx_ = (int) ((Math.floor(xPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
	                            		  ty_ = (int) ((Math.floor(yPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
	                            		  tx_1 = (int) ((Math.floor(e.getX() / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
	                            		  ty_1 = (int) ((Math.floor(e.getY() / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
	                            		  g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)*vArea));
                                 	  	  g2.drawLine(tx_, ty_, tx_1, ty_1);
                                 	  	  wa.setImage(imag);//Сохраним в буфере
	                                  break;
	                              //Выделение
	                              case 11:
	                            	  //System.out.println(sost);//Состояние области выделения
	                            	  if(sost == 2) {
	                            		  //Поиск начальной точки
	                            		  vv = Math.pow(2*vArea, scale);
	                            		  tx_ = (int) ((Math.floor(x1 / vv.intValue()))*vv.intValue());
	                            		  ty_ = (int) ((Math.floor(y1 / vv.intValue()))*vv.intValue());
	                            		  tx_1 = (int) ((Math.floor(e.getX() / vv.intValue()))*vv.intValue()) + vv.intValue();
	                            		  ty_1 = (int) ((Math.floor(e.getY() / vv.intValue()))*vv.intValue()) + vv.intValue();
	                            		  g2.setStroke(new  BasicStroke(1.0f));
                                 	  	  //g2.drawLine(tx_, ty_, tx_1, ty_1);
                                 	  	  wa.setImage(imag);//Сохраним в буфере
	                            		  //Выделяем
                                 	  	  wa.setCoord(tx_, ty_, tx_1, ty_1);//Задаёт координаты
	                            		  //wa.setCoord(x1, y1, x2, y2);//Задаёт координаты
	                            		  wa.saveImage();
	                            		  wa.Draw(g);//Рисует
	                            	  }
	                                  break;  
	                               // линия
	                              case 4:
	                            	  vArea = 1;
	                            	  //Найдём реальные координаты пикселя
	                            	  //Найдём реальные координаты пикселя
                            		  vv = Math.pow(2, scale);
                                 	  tx_ = (int) ((Math.floor(xPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                                 	  ty_ = (int) ((Math.floor(yPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                                 	  tx_1 = (int) ((Math.floor(e.getX() / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                                 	  ty_1 = (int) ((Math.floor(e.getY() / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                                 	  g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)*vArea));
                                 	  //g.drawLine(tx_, ty_, tx_1, ty_1);
	                            	  //Рисуем
	                            	  
	                            	  //g2.setStroke(new  BasicStroke(1.0f+1.0f*scale));
	                                  g.drawLine(xf, yf, e.getX(), e.getY());
	                                  break;
	                             // круг
	                              case 5:
	                            	  g2.setStroke(new  BasicStroke(1.0f+1.0f*scale));
	                                  g.drawOval(x1, y1, (x2-x1), (y2-y1));
	                                  break;
	                             // прямоугольник
	                              case 6:
	                            	  vArea = 1;
	                            	//Поиск начальной точки
                            		  vv = Math.pow(2, scale);
                            		  tx_ = (int) ((Math.floor(x1 / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                            		  ty_ = (int) ((Math.floor(y1 / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                            		  tx_1 = (int) ((Math.floor(x2 / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                            		  ty_1 = (int) ((Math.floor(y2 / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                            		  g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)*vArea));
                            		  g.drawRect(tx_, ty_, (tx_1 - tx_), (ty_1 - ty_));
	                                  break;
	                             // закрашенный круг
	                              case 9:
	                            	  g2.fill(new Ellipse2D.Float(x1, y1, (x2-x1), (y2-y1)));
	                            	  //g.drawOval(x1, y1, (x2-x1), (y2-y1));
	                                  break;
	                             // закрашенный квадрат
	                              case 10:
	                            	//Поиск начальной точки
	                            	  vArea = 1;
		                            	//Поиск начальной точки
	                            		  vv = Math.pow(2, scale);
	                            		  tx_ = (int) ((Math.floor(x1 / vv.intValue()))*vv.intValue());
	                            		  ty_ = (int) ((Math.floor(y1 / vv.intValue()))*vv.intValue());
	                            		  tx_1 = (int) ((Math.floor(x2 / vv.intValue()))*vv.intValue());
	                            		  ty_1 = (int) ((Math.floor(y2 / vv.intValue()))*vv.intValue());
	                            		  g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)*vArea));
                            		      g2.fill(new Rectangle2D.Float(tx_, ty_, (tx_1 - tx_), (ty_1 - ty_)));
	                            	  //g2.fill(new Rectangle2D.Float(x1, y1, (x2-x1), (y2-y1)));
	                                  break;
	                             // аэрозоль
	                              case 12:
	                            	  startAerosol(e.getX(), e.getY(),new  BasicStroke(1.0f+1.0f*scale));
	                            	  break;
	                          }
	                          xPad=e.getX();
	                          yPad=e.getY();
	                          }
	                          japan.repaint();                          
                    	 
                    		  if(sost == 1)
                    		  {
                    			  imag = wa.getImage();
                    			  Graphics g = imag.getGraphics();
    	                          Graphics2D g2 = (Graphics2D)g;
    	                          wa.paintDelArea(g2);//Закрасим исходную область
    	                          //g2.setColor(new Color(0,0,150));
    	                          //wa.setCoord(x1, y1, x2, y2);//Задаёт координаты
    	                          wa.dDrawImage(imag);
                            	  wa.dDraw(g,e.getX(),e.getY());//Рисует
                            	  //lblNewLabel.setText(e.getX()/(scale+1.0)+"; "+e.getY()/(scale+1.0)+" ПКС");
                            	  SetCooordinate(e.getX(), e.getY(), lblNewLabel);
                            	  //wa.setCh(true);
                            	 
                            	  japan.repaint();
                    			  //wa.setCoord(e.);//Задаёт новые координаты для области
                    			  //wa.Draw(g);//Рисует
                    		  }
                    		  if(sost == 40)
                    		  {
                    			  /*
                    			  imag = wa.getImage();
                    			  Graphics g = imag.getGraphics();
    	                          Graphics2D g2 = (Graphics2D)g;
    	                          wa.paintDelArea(g2);//Закрасим исходную область
    	                          wa.dDrawImage(imag);
                            	  wa.Draw(g);//Рисует
                            	  lblNewLabel.setText(e.getX()+"; "+e.getY()+" ПКС");
                            	  japan.repaint();
                            	  */
                    			  imag = wa.getImage();
                    			  Graphics g = imag.getGraphics();
    	                          Graphics2D g2 = (Graphics2D)g;
    	                          wa.paintDelArea(g2);//Закрасим исходную область
    	                          wa.isResize40(e.getX(), e.getY());
    	                          wa.dDrawImage(imag);
                            	  wa.Draw(g);//Рисует
                            	  //lblNewLabel.setText(e.getX()/(scale+1.0)+"; "+e.getY()/(scale+1.0)+" ПКС");
                            	  SetCooordinate(e.getX(), e.getY(), lblNewLabel);
                            	  japan.repaint();
                    		  }
                    		  else
                    		  if(sost == 80)
                    		  {
                    			  imag = wa.getImage();
                    			  Graphics g = imag.getGraphics();
    	                          Graphics2D g2 = (Graphics2D)g;
    	                          wa.paintDelArea(g2);//Закрасим исходную область
    	                          wa.isResize80(e.getX(), e.getY());
    	                          wa.dDrawImage(imag);
                            	  wa.Draw(g);//Рисует
                            	  //lblNewLabel.setText(e.getX()/(scale+1.0)+"; "+e.getY()/(scale+1.0)+" ПКС");
                            	  SetCooordinate(e.getX(), e.getY(), lblNewLabel);
                            	  japan.repaint();
                    		  }
                    		  else
                    		  if(sost == 20)
                    		  {
                    			  imag = wa.getImage();
                    			  Graphics g = imag.getGraphics();
    	                          Graphics2D g2 = (Graphics2D)g;
    	                          wa.paintDelArea(g2);//Закрасим исходную область
    	                          wa.isResize20(e.getX(), e.getY());
    	                          wa.dDrawImage(imag);
                            	  wa.Draw(g);//Рисует
                            	  //lblNewLabel.setText(e.getX()/(scale+1.0)+"; "+e.getY()/(scale+1.0)+" ПКС");
                            	  SetCooordinate(e.getX(), e.getY(), lblNewLabel);
                            	  japan.repaint();
                    		  }
                    		  else
                    		  if(sost == 60)
                    		  {
                    			  imag = wa.getImage();
                    			  Graphics g = imag.getGraphics();
    	                          Graphics2D g2 = (Graphics2D)g;
    	                          wa.paintDelArea(g2);//Закрасим исходную область
    	                          wa.isResize60(e.getX(), e.getY());
    	                          wa.dDrawImage(imag);
                            	  wa.Draw(g);//Рисует
                            	  //lblNewLabel.setText(e.getX()/(scale+1.0)+"; "+e.getY()/(scale+1.0)+" ПКС");
                            	  SetCooordinate(e.getX(), e.getY(), lblNewLabel);
                            	  japan.repaint();
                    		  }
                    		  else
                    		  if(sost == 10)
                    		  {
                    			  imag = wa.getImage();
                    			  Graphics g = imag.getGraphics();
    	                          Graphics2D g2 = (Graphics2D)g;
    	                          wa.paintDelArea(g2);//Закрасим исходную область
    	                          wa.isResize10(e.getX(), e.getY());
    	                          wa.dDrawImage(imag);
                            	  wa.Draw(g);//Рисует
                            	  //lblNewLabel.setText(e.getX()/(scale+1.0)+"; "+e.getY()/(scale+1.0)+" ПКС");
                            	  SetCooordinate(e.getX(), e.getY(), lblNewLabel);
                            	  japan.repaint();
                    		  }
                    		  else
                    		  if(sost == 50)
                    		  {
                    			  imag = wa.getImage();
                    			  Graphics g = imag.getGraphics();
    	                          Graphics2D g2 = (Graphics2D)g;
    	                          wa.paintDelArea(g2);//Закрасим исходную область
    	                          wa.isResize50(e.getX(), e.getY());
    	                          wa.dDrawImage(imag);
                            	  wa.Draw(g);//Рисует
                            	  //lblNewLabel.setText(e.getX()/(scale+1.0)+"; "+e.getY()/(scale+1.0)+" ПКС");
                            	  SetCooordinate(e.getX(), e.getY(), lblNewLabel);
                            	  japan.repaint();
                    		  }
                    		  if(sost == 30)
                    		  {
                    			  imag = wa.getImage();
                    			  Graphics g = imag.getGraphics();
    	                          Graphics2D g2 = (Graphics2D)g;
    	                          wa.paintDelArea(g2);//Закрасим исходную область
    	                          wa.isResize30(e.getX(), e.getY());
    	                          wa.dDrawImage(imag);
                            	  wa.Draw(g);//Рисует
                            	  //lblNewLabel.setText(e.getX()/(scale+1.0)+"; "+e.getY()/(scale+1.0)+" ПКС");
                            	  SetCooordinate(e.getX(), e.getY(), lblNewLabel);
                            	  japan.repaint();
                    		  }
                    		  if(sost == 70)
                    		  {
                    			  imag = wa.getImage();
                    			  Graphics g = imag.getGraphics();
    	                          Graphics2D g2 = (Graphics2D)g;
    	                          wa.paintDelArea(g2);//Закрасим исходную область
    	                          wa.isResize70(e.getX(), e.getY());
    	                          wa.dDrawImage(imag);
                            	  wa.Draw(g);//Рисует
                            	  //lblNewLabel.setText(e.getX()/(scale+1.0)+"; "+e.getY()/(scale+1.0)+" ПКС");
                            	  SetCooordinate(e.getX(), e.getY(), lblNewLabel);
                            	  japan.repaint();
                    		  }
                    		 /* 
                    		  if(wa.getCursor(e.getX(), e.getY())==Cursor.E_RESIZE_CURSOR)
                    		  {
                    			  imag = wa.getImage();
                    			  Graphics g = imag.getGraphics();
    	                          Graphics2D g2 = (Graphics2D)g;
    	                          wa.paintDelArea(g2);//Закрасим исходную область
    	                          //g2.setColor(new Color(0,0,150));
    	                          //wa.setCoord(x1, y1, x2, y2);//Задаёт координаты
    	                          wa.dDrawImage(imag);
                            	 wa.dDraw(g,e.getX(),e.getY());//Рисует
                            	  lblNewLabel.setText(e.getX()+"; "+e.getY()+" ПКС");
                            	  //wa.setCh(true);
                            	 
                            	  japan.repaint();
                    			  //wa.setCoord(e.);//Задаёт новые координаты для области
                    			  //wa.Draw(g);//Рисует
                    		  }
                    		  */
                      }
                      
                      public void mouseMoved(MouseEvent e) {     
                    	  //lblNewLabel.setText(e.getX()/(scale+1.0)+"; "+e.getY()/(scale+1.0)+" ПКС");
                    	  SetCooordinate(e.getX(), e.getY(), lblNewLabel);
                    	  japan.setCursor(Cursor.getPredefinedCursor(wa.getCursor(e.getX(), e.getY())));
                    	  tx = e.getX();
                    	  ty = e.getY();
                    	  japan.repaint();
                      }
                  });
          
          japan.addMouseListener(new  MouseAdapter()
                  {
                     public void mouseClicked(MouseEvent e) {
                     Color c;
                     Double vv;
                  	 int tx_;
                  	 int ty_;
                  	 Double a1;
                  	 Double b1;
                     coordinateXY xy;
                     Graphics g = imag.getGraphics();
                     Graphics2D g2 = (Graphics2D)g;
                     // установка цвета
                          g2.setColor(maincolor);
                          switch (rezhim)
                          {
                              // карандаш
                              case 0:
                            	  vArea = 1;//Разммер кисти = 2
                            	  if(scale == 0) {
                            		  g2.setStroke(new  BasicStroke(1.0f));
                            		  g2.drawLine(xPad, yPad, xPad, yPad);
                            		  wa.setImage(imag);//Сохраним в буфере
                            	  }
                            	  else
                            	  {
                            		  //Найдём нужные точки и скорректируем
                            		  //int tx_ = (int) ((Math.floor(tx / (scale+1)))*(scale + 1)) + (scale + 1)/2;
                                 	  //int ty_ = (int) ((Math.floor(ty / (scale+1)))*(scale + 1)) + (scale + 1)/2;
                            		  vv = Math.pow(2, scale);
                                 	  tx_ = (int) ((Math.floor(xPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                                 	  ty_ = (int) ((Math.floor(yPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                                 	  g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)));
                                 	  g2.drawLine(tx_, ty_, tx_, ty_);
                                 	  wa.setImage(imag);//Сохраним в буфере
                            	  }
                                  break;
                              // кисть
                              case 1:
                            	  vArea = 4;
                            	  vv = Math.pow(2, scale);
                             	  tx_ = (int) ((Math.floor(xPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                             	  ty_ = (int) ((Math.floor(yPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                             	  g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)*vArea));
                             	  g2.drawLine(tx_, ty_, tx_, ty_);
                             	  wa.setImage(imag);//Сохраним в буфере
                            	  /*
                            		  vArea = 2;//Разммер кисти = 2
                            		//Указатель текущего пикселя при увеличении///////////////////
                            		  	 g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)*vArea*2));
                                     	 //Найдём реальные координаты пикселя
                                     	 vv = Math.pow(2*vArea, scale);
                                     	 tx_ = (int) ((Math.floor(tx / vv.intValue()))*vv.intValue());
                                     	 ty_ = (int) ((Math.floor(ty / vv.intValue()))*vv.intValue());
                                     	 a1 = Math.pow(2.0*vArea, scale);
                                     	 b1 = Math.pow(2.0*vArea, scale);
                                         g2.drawLine(tx_ + a1.intValue()/2, ty_ + b1.intValue()/2, tx_ + a1.intValue()/2, ty_ + b1.intValue()/2);
                                         wa.setImage(imag);//Сохраним в буфере
                                         */
                                  break;
                               // ластик
                              case 2:
                            	  vArea = 6;
                            	  vv = Math.pow(2, scale);
                             	  tx_ = (int) ((Math.floor(xPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                             	  ty_ = (int) ((Math.floor(yPad / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                             	  g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)*vArea));
                             	  g2.drawLine(tx_, ty_, tx_, ty_);
                             	  wa.setImage(imag);//Сохраним в буфере
                            	  /*
                            	   vArea = 3;//Разммер кисти = 2
                             		//Указатель текущего пикселя при увеличении///////////////////
                             		  	 g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)*vArea*3));
                                      	 //Найдём реальные координаты пикселя
                                      	 vv = Math.pow(2*vArea, scale);
                                      	 tx_ = (int) ((Math.floor(tx / vv.intValue()))*vv.intValue());
                                      	 ty_ = (int) ((Math.floor(ty / vv.intValue()))*vv.intValue());
                                      	 a1 = Math.pow(2.0*vArea, scale);
                                      	 b1 = Math.pow(2.0*vArea, scale);
                                         g2.drawLine(tx_ + a1.intValue()/2, ty_ + b1.intValue()/2, tx_ + a1.intValue()/2, ty_ + b1.intValue()/2);
                                         */
                              break;
                              /*
                              // текст
                              case 3:
                                  // устанавливаем фокус для панели,
                                  // чтобы печатать на ней текст
                                  japan.requestFocus();
                                  break;
                               */
                              //Заливка
                              case 7:
                                  //Определили цвет
                                  //int x = xPad;
                                  //int y = yPad;
                                  c = new Color(imag.getRGB(xPad, yPad));//<-- color
                                  g2.setColor(c);
                                  
                                  //Красим
                                  xy = new coordinateXY(xPad, yPad);
                                  g2.setStroke(new  BasicStroke(1.0f));//Число пикселей в точке при покраске
                                  Zalivka zl = new Zalivka();
                                  zl.zalivaem(imag, g2, xy, c, maincolor);//Зальём
                                  wa.setImage(imag);//Сохраним в буфере
                                  break;
                              //Штриховка 50x50    
                              case 13:
                            	//Определили цвет
                                  c = new Color(imag.getRGB(xPad, yPad));//<-- color
                                  g2.setColor(c);     
                                  //Красим
                                  xy = new coordinateXY(xPad, yPad);
                                  g2.setStroke(new  BasicStroke(1.0f));//Число пикселей в точке при покраске
                                  RepaintPr r50x50 = new RepaintPr();
                                  r50x50.zalivaem50x50(imag, xy, c, maincolor);//Зальём
                                  wa.setImage(imag);//Сохраним в буфере
                            	  break;
                              //Штриховка 25x75 
                              case 14:
                            	//Определили цвет
                                  c = new Color(imag.getRGB(xPad, yPad));//<-- color
                                  g2.setColor(c);     
                                  //Красим
                                  xy = new coordinateXY(xPad, yPad);
                                  g2.setStroke(new  BasicStroke(1.0f));//Число пикселей в точке при покраске
                                  RepaintPr r25x75 = new RepaintPr();
                                  r25x75.zalivaem25x75(imag, xy, c, maincolor);//Зальём
                                  wa.setImage(imag);//Сохраним в буфере
                            	  break;
                            	  
                                  //Пипетка
                              case 8:
                                  //Определили цвет
                                  maincolor = new Color(imag.getRGB(xPad, yPad));//<-- color
                                  colourPane.setBackground(maincolor);
                                  break;
                                  //Замена цвета
                              case 3:
                            	  wa.RepaintColor(imag, new Color(imag.getRGB(xPad, yPad)), maincolor);
                            	  wa.setImage(imag);//Сохраним в буфере
                            	  break;
                          }
                          xPad=e.getX();
                          yPad=e.getY();
                           
                          pressed=true;
                          japan.repaint();      
                     }
                     
                     public void mousePressed(MouseEvent e) {
                    	 japan.repaint();
                    	 if (e.getButton() == MouseEvent.BUTTON3){
                    		 rezhim = 11;
                    	 }
                    	  wa.setDeDot(e.getX(), e.getY());//Сохраним сдвиги
                          xPad=e.getX();
                          yPad=e.getY();
                          //xf=e.getX();
                          //yf=e.getY();
                          Double vv = Math.pow(2*vArea, scale);
                          xf = (int) ((Math.floor(e.getX() / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                     	  yf = (int) ((Math.floor(e.getY() / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
                          pressed=true;
                          if(rezhim!=11) {
                        	  if(rezhim == 16) {
                        		  wa.setImage(imag);//Сохраним в буфере
                        		  wa.snapshot();//Делаем снимок области
                        		  //Если делаем автовыделение
                            		  //wa.setImage(imag);
                            		  Integer xmi = null, xma = null, ymi = null, yma = null;
                            		  Color c;
                                      coordinateXY xy;
                                      BufferedImage bmp = CopyImage.deepCopy(imag);
                                      Graphics g = bmp.getGraphics();
                                      Graphics2D g2 = (Graphics2D)g;
                            		  //Копируем во временный буфер
                            		  
                            		  Graphics gb = imag.getGraphics();
                                      Graphics2D g2b = (Graphics2D)gb;

                            		  //Красим в синий копию + поиск размеров
                            		  c = new Color(imag.getRGB(e.getX(), e.getY()));//<-- color
                                      g2.setColor(c);
                                      xy = new coordinateXY(xPad, yPad);
                                      g2.setStroke(new  BasicStroke(1.0f));//Число пикселей в точке при покраске
                                      ZalivkaAndSearch zl = new ZalivkaAndSearch();
                                      //mas4 mas = zl.zalivaem(imag, g2, xy, c, maincolor);//Зальём синим цветом
                                      mas4 mas = zl.zalivaem(bmp, g2, xy, c, new Color(0, 0, 150));//Зальём синим цветом
                                      //Скопируем в цвет
                                      
                                      //Сделаем два буфера под вырезанные части изображений с перекрашеной областью и не перекрашеной
                                      BufferedImage bb_ = bmp.getSubimage( mas.getX1(), mas.getY1(), mas.getX2() - mas.getX1() +1, mas.getY2() - mas.getY1() + 1);//Крашеное
                                      BufferedImage bs_ = imag.getSubimage( mas.getX1(), mas.getY1(), mas.getX2() - mas.getX1() +1, mas.getY2() - mas.getY1() + 1);//Не крашеное
                                      //Скопируем в отдельные буферы
                                      BufferedImage bb = CopyImage.deepCopy(bb_);
                                      BufferedImage bs = CopyImage.deepCopy(bs_);
                                      //Перекрасим фон в буфере bs в 0 0 150
                                      Graphics gbb = bb.getGraphics();
                                      Graphics2D g2bb = (Graphics2D)gbb;
                                      Graphics gbs = bs.getGraphics();
                                      Graphics2D g2bs = (Graphics2D)gbs;
                                      g2.setStroke(new  BasicStroke(1.0f));
                                      g2bs.setColor(new Color(0, 0, 150));
                                      for(int i = 0; i < bs.getWidth(); i++)
                                    	  for(int j = 0; j < bs.getHeight(); j++)
                                    	  {
                                    		  Color cc1 = new Color(bb.getRGB(i, j));
                                    		  Color cc2 = new Color(0, 0, 150);
                                    		  if(!cc1.equals(cc2))
                                    		  {
                                    			  gbs.drawLine(i, j, i, j);
                                    		  }
                                    	  }
                                      //Сохраним результат выделения в буфер
                                      
                                      //wa.setCoord(xmi, xma, ymi, yma);//Задаёт координаты
                                      wa.setCoord(mas.getX1(), mas.getY1(), mas.getX2()+1, mas.getY2()+1);//Задаёт координаты
                            		  wa.saveImageNoFon(bs);
                            		  wa.setImage(imag);//Сохраним в буфере
                            		  wa.snapshot();//Делаем снимок области
                            		  //wa.saveImage();
                            		  wa.Draw(imag);//Рисует
                            		  tool = 11;
                            		  rezhim = 11;
                            		  japan.repaint();
                        	  }
                        	  else
                        		  if(rezhim == 15) {
                              		//Если делаем автовыделение
                                  		  wa.setImage(imag);
                                  		  wa.snapshot();//Делаем снимок области
                                  		  Integer xmi = null, xma = null, ymi = null, yma = null;
                                  		  Color c;
                                            coordinateXY xy;
                                            BufferedImage bmp = CopyImage.deepCopy(imag);
                                            Graphics g = bmp.getGraphics();
                                            Graphics2D g2 = (Graphics2D)g;
                                  		  //Копируем во временный буфер
                                  		  
                                  		  Graphics gb = imag.getGraphics();
                                            Graphics2D g2b = (Graphics2D)gb;

                                  		  //Красим в синий копию + поиск размеров
                                  		  c = new Color(imag.getRGB(e.getX(), e.getY()));//<-- color
                                            g2.setColor(c);
                                            xy = new coordinateXY(xPad, yPad);
                                            g2.setStroke(new  BasicStroke(1.0f));//Число пикселей в точке при покраске
                                            ZalivkaAndSearch zl = new ZalivkaAndSearch();
                                            //mas4 mas = zl.zalivaem(imag, g2, xy, c, maincolor);//Зальём синим цветом
                                            mas4 mas = zl.zalivaem(bmp, g2, xy, c, new Color(0, 0, 150));//Зальём синим цветом
                                            //Скопируем в цвет
                                            
                                            //Сделаем два буфера под вырезанные части изображений с перекрашеной областью и не перекрашеной
                                            //BufferedImage bb_ = bmp.getSubimage( mas.getX1(), mas.getY1(), mas.getX2() - mas.getX1() +1, mas.getY2() - mas.getY1() + 1);//Крашеное
                                            BufferedImage bs_ = imag.getSubimage( mas.getX1(), mas.getY1(), mas.getX2() - mas.getX1() +1, mas.getY2() - mas.getY1() + 1);//Не крашеное
                                            //Скопируем в отдельные буферы
                                            //BufferedImage bb = CopyImage.deepCopy(bb_);
                                            BufferedImage bs = CopyImage.deepCopy(bs_);
                                            //Перекрасим фон в буфере bs в 0 0 150
                                            //Сохраним результат выделения в буфер
                                            wa.setImage(imag);//Сохраним в буфере
                                            //wa.setCoord(xmi, xma, ymi, yma);//Задаёт координаты
                                            wa.setCoord(mas.getX1(), mas.getY1(), mas.getX2()+1, mas.getY2()+1);//Задаёт координаты
                                  		  wa.saveImage(bs);
                                  		  //wa.saveImage();
                                  		  wa.setImage(imag);//Сохраним в буфере
                              		      wa.snapshot();//Делаем снимок области
                                  		  wa.Draw(imag);//Рисует
                                  		  tool = 11;
                                  		  rezhim = 11;
                                  		  japan.repaint();
                              	  }
                        		  else
                        			  if(wa.ifArea()==true) {
                        				  imag=wa.getImage();
                        			  }
                        	  
                        	  
                        	  //if() {
                        	//	  wa.setImage(imag);
                        	//  }
                          }
                          else
                          {
                        	  //Если делали перемещение или выделение
                        	  if(tool == 11 && wa.getCursor(e.getX(), e.getY())==Cursor.DEFAULT_CURSOR && (sost==2 || sost == 1 || (sost >= 10 && sost <= 80))) {
                        		   imag = wa.getImage();//Уберём рамку

                        		  	Graphics g = imag.getGraphics();
                        		  	wa.paintDelArea(g);//Перекрасим область под выделением
                        	  		wa.dDrawImage(imag);//Нарисуем из буфера
                        	  		wa.setImage(imag);//Примем изображение за эталон
                        		  
                        	  		wa.ifArea();//Отметим что рамка не отрисовывается
                        	  		sost = 0;//Кликнули не по области
                        	  }
                        	  else sost = 2;//Если выделение не сделано
                        	  
                        	  /*
                        	  if(tool == 11 && wa.getCursor(e.getX(), e.getY())==Cursor.DEFAULT_CURSOR && sost==2 && wa.ifPaint())
                    		  {
                    			  sost = 0;
                    		  }*/
                        	  
                        	  if(wa.getCursor(e.getX(), e.getY())==Cursor.MOVE_CURSOR)
                    		  {
                    			  sost = 1;//Перетаскивание
                    		  }
                        	  //if(wa.getDot(e.getX(), e.getY()) == 4)
                    		  //{
                    			//  sost = 4;//Масштабирование середина право
                    		  //}
                        	  switch(wa.getDot(e.getX(), e.getY())){
                        	  case 40:
                        		  sost = 40;//Масштабирование середина право
                        		  break;
                        	  case 80:
                        		  sost = 80;
                        		  break;
                        	  case 20:
                        		  sost = 20;
                        		  break;
                        	  case 60:
                        		  sost = 60;
                        		  break;
                        	  case 10:
                        		  sost = 10;
                        		  break;
                        	  case 50:
                        		  sost = 50;
                        		  break;
                        	  case 30:
                        		  sost = 30;
                        		  break;
                        	  case 70:
                        		  sost = 70;
                        		  break;
                        	  }
                        	  
                        	  
                          }
                          tool = rezhim;
                          
                          
                          switch (rezhim)
                          {
                          
                              // Аэрозоль
                              case 12:
                            	  startAerosol(e.getX(), e.getY(),new  BasicStroke(1.0f+1.0f*scale));
                                  break;
                          }
                     }
                     
                    public void mouseReleased(MouseEvent e) {
                    	//imag=deepCopy(imag1);
                        Graphics g = imag.getGraphics();
                        Graphics2D g2 = (Graphics2D)g;
                        // установка цвета
                        g2.setColor(maincolor);
                        // Общие рассчеты для овала и прямоугольника
                        int  x1=xf, x2=xPad, y1=yf, y2=yPad;
                                  if(xf>xPad)
                                  {
                                     x2=xf; x1=xPad; 
                                  }
                                  if(yf>yPad)
                                  {
                                     y2=yf; y1=yPad; 
                                  }
                        //imag=imag1; 
                        switch(rezhim)
                        {
                             // линия
                              case 4:
                                 g.drawLine(xf, yf, e.getX(), e.getY());
                                 wa.setImage(imag);//Сохраним в буфере
                                  break;
                             // круг
                              case 5:
                                  g.drawOval(x1, y1, (x2-x1), (y2-y1));
                                  wa.setImage(imag);//Сохраним в буфере
                                  break;
                             // прямоугольник
                              case 6:
                                  //g.drawRect(x1, y1, (x2-x1), (y2-y1));
                                  wa.setImage(imag);//Сохраним в буфере
                                  break;
                             // закрашенный круг
                              case 9:
                            	  g2.fill(new Ellipse2D.Float(x1, y1, (x2-x1), (y2-y1)));
                            	  wa.setImage(imag);//Сохраним в буфере
                            	  //g.drawOval(x1, y1, (x2-x1), (y2-y1));
                                  break;
                             // закрашенный квадрат
                              case 10:
                            	  //g2.fill(new Rectangle2D.Float(x1, y1, (x2-x1), (y2-y1)));
                            	  wa.setImage(imag);//Сохраним в буфере
                                  break;
                                  /*
                               // Выделение
                              case 11:
                                  g.drawRect(x1, y1, (x2-x1), (y2-y1));
                                  g.fillRect(x1-5, y1-5,10, 10);//Верхний левый
                                  g.fillRect(x2-5, y1-5,10, 10);//Верхний правый
                                  g.fillRect(x1-5, y2-5,10, 10);//Нижний левый
                                  g.fillRect(x2-5, y2-5,10, 10);//Нижний правый
                                  g.fillRect(x1+(x2-x1)/2-5, y1-5,10, 10);//Верх центр
                                  g.fillRect(x1+(x2-x1)/2-5, y2-5,10, 10);//Низ центр
                                  g.fillRect(x1-5, y1+(y2-y1)/2-5,10, 10);//Лево центр
                                  g.fillRect(x2-5, y1+(y2-y1)/2-5,10, 10);//Право центр
                                  break;
                                  */
                              case 12:
                            	  time.stop();
                        }
                        int test = rezhim;
                        xf=0; yf=0;
                        pressed=false;
                        japan.repaint();
                        
                        if (e.getButton() == MouseEvent.BUTTON1){
                        	if(rezhim != 11 && rezhim != 15 && rezhim != 16)
                        		wa.snapshot();//Делаем снимок области
                        }
                    }                
                  });
          /*
        japan.addKeyListener(new  KeyAdapter() {
                    public void keyReleased(KeyEvent e)
                    {
                        // устанавливаем фокус для панели,
                       // чтобы печатать на ней текст
                        japan.requestFocus();
                    }
                    public void keyTyped(KeyEvent e) 
                    {
                        if(rezhim==3){
                        Graphics g = imag.getGraphics();
                        Graphics2D g2 = (Graphics2D)g;
                        // установка цвета
                          g2.setColor(maincolor);
                        g2.setStroke(new  BasicStroke(2.0f));
                         
                         String str = new  String("");
                         str+=e.getKeyChar();
                        g2.setFont(new  Font("Arial", 0, 15));
                        g2.drawString(str, xPad, yPad);
                        xPad+=10;
                        // устанавливаем фокус для панели,
                        // чтобы печатать на ней текст
                        japan.requestFocus();
                        japan.repaint();
                        }
                    }
                });
                */
        f.addComponentListener(new  ComponentAdapter() {
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    // если делаем загрузку, то изменение размеров формы
                    // отрабатываем в коде загрузки
                   if(loading==false)
                   {
                    //japan.setSize(f.getWidth()-40, f.getHeight()-80);//Размеры области рисования
                    BufferedImage tempImage = new  BufferedImage(japan.getWidth(), japan.getHeight(), BufferedImage.TYPE_INT_RGB);
                             Graphics2D d2 = (Graphics2D) tempImage.createGraphics();
                        d2.setColor(Color.white);
                        d2.fillRect(0, 0, japan.getWidth(), japan.getHeight());
                    tempImage.setData(imag.getRaster());
                    imag=tempImage;
                    japan.repaint();
                   }
                     loading=false;
                }
                });
        f.setLayout(null);
        f.setVisible(true);
        
        //Изменение размера

        
//Инициализация буферов//////////////////////////////////////////////////////////////
        imag = new  BufferedImage(japan.getWidth(), japan.getHeight(), BufferedImage.TYPE_INT_RGB);
    	Graphics g1 = imag.getGraphics();
        Graphics2D g2 = (Graphics2D)g1;
        g2.setColor(Color.WHITE);
 	    g2.fill(new Rectangle2D.Float(0, 0, imag.getWidth(), imag.getHeight()));
 	    wa.setImage(imag);//Сохраним в буфере
 	    japan.repaint();
 	    wa.snapshot();//Делаем снимок области
/////////////////////////////////////////////////////////////////////////////////////
 	    
////////////////////////////////////////////////////Обработка клавиатуры////////////////  
tabbedPane.addKeyListener(new KeyListener() {
public void keyPressed(KeyEvent e) {
//System.out.println("dfndjfnksdjfsdk");
}

public void keyReleased(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()){
		funcVK_C();
    }
	if (e.getKeyCode() == KeyEvent.VK_V && e.isControlDown()){
		funcVC_V();
    }
	if (e.getKeyCode() == KeyEvent.VK_X && e.isControlDown()){
		funcVC_X();
    }
	if (e.getKeyCode() == KeyEvent.VK_Y && e.isControlDown()){
		funcVC_Y();
	}
	if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown()){
		funcVC_Z();
	}
	
}

public void keyTyped(KeyEvent e) {
//System.out.println("dfndjfnksdjfsdk");  
}

});
///////////////////////////////////////////////////////////////////////////////////////// 
    }
    
    
    /*
     * Изменение размера выделенной области или холста
     */
    public void isResize(JTextField ftf1, JTextField ftf2, JPanel pa) {
    	if(!wa.ifPaint())//Если выделения нет меняем размер холста
    	{	
    		int yy1, yy2;
    		yy1 = Integer.parseInt(ftf1.getText());
    		yy2 = Integer.parseInt(ftf2.getText())+80;
    		japan.setSize(Integer.parseInt(ftf1.getText()), Integer.parseInt(ftf2.getText()));
    		pa.setPreferredSize(new Dimension(yy1, yy2));
    		wa.setImage(imag);
    		imag = wa.getImage();
    		Graphics g = imag.getGraphics();
    		Graphics2D g2 = (Graphics2D)g;
    		wa.setImage(imag);//Сохраним в буфер
   	  		japan.repaint();
    	}
    	else//Если есть выделение
    	{
    		imag = wa.getImage();
    		Graphics g = imag.getGraphics();
    		Graphics2D g2 = (Graphics2D)g;
        	wa.paintDelArea(g2);//Закрасим исходную область
        	wa.manualResize(Integer.parseInt(ftf1.getText()), Integer.parseInt(ftf2.getText()));
        	wa.dDrawImage(imag);
   	  		wa.Draw(g);//Рисует
   	  		//lblNewLabel.setText(e.getX()+"; "+e.getY()+" ПКС");
   	  		japan.repaint();
    	}
     }
  
     class MyFrame extends JFrame
     {
         public void paint(Graphics g)
         {
             super.paint(g);
         }
         public MyFrame(String title)
         {
             super(title);
         }
     }
 
     /**
      * Создание и настройка области рисования
      *
      */
     class MyPanel extends JPanel
     {
         public MyPanel()
         {
             JPopupMenu menuPopup = new JPopupMenu();
             this.setComponentPopupMenu(menuPopup);
             
	    	  ActionListener copyListener = new ActionListener() {
	    	      public void actionPerformed(ActionEvent event) {
	    	    	  wa.setBufer();
	    	      }
	    	  };
	    	  
	    	  ActionListener cutlListener = new ActionListener() {
	    	      public void actionPerformed(ActionEvent event) {
	    	    	//Скопируем в буфер
	          	   	wa.setBufer();
	          	   	//Закрасим
	          	   	imag = wa.getImage();//Уберём рамку
	     		  	Graphics g = imag.getGraphics();
	     		  	wa.paintDelThisArea(g);//Перекрасим область под выделением
	     		  	wa.setImage(imag);//Примем изображение за эталон   		  
	     		  	wa.ifArea();//Отметим что рамка не отрисовывается
	     		  	japan.repaint();
	    	      }
	    	  };
	    	  
	    	  ActionListener pasteListener = new ActionListener() {
	    	      public void actionPerformed(ActionEvent event) {
	    	    	  //Получим изображение из буфера
	    	    	  wa.paste();
	    	    	  //Прорисуем изображение на экране
	    	    	  //imag = wa.getImage();
                      //wa.dDrawImage(imag);
	    	    	  //japan.repaint();


	    	    	  imag = wa.getImage();
        			  Graphics g = imag.getGraphics();
                      Graphics2D g2 = (Graphics2D)g;
                      wa.paintDelArea(g2);//Закрасим исходную область
                      //wa.isResize70(e.getX(), e.getY());
                      wa.dDrawImage(imag);
                	  wa.Draw(g);//Рисует
                	  //lblNewLabel.setText(e.getX()+"; "+e.getY()+" ПКС");
                	  japan.repaint();
	    	    	  
	    	      }
	    	  };
	    	  
	    	    JMenuItem item, item1, item2;
	    	    
	    	    
	    	    menuPopup.add(item = new JMenuItem("Копировать"));
	    	    item.setHorizontalTextPosition(JMenuItem.RIGHT);
	    	    item.addActionListener(copyListener);
	    	    menuPopup.add(item1 = new JMenuItem("Вырезать"));
	    	    item1.setHorizontalTextPosition(JMenuItem.RIGHT);
	    	    item1.addActionListener(cutlListener);
	    	    menuPopup.add(item2 = new JMenuItem("Вставить"));
	    	    item2.setHorizontalTextPosition(JMenuItem.RIGHT);
	    	    item2.addActionListener(pasteListener);
        	 
        	 
        	 
         }
       public void paintComponent (Graphics g)
          {
            if(imag==null)
             {
                 imag = new  BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
                 Graphics2D d2 = (Graphics2D) imag.createGraphics();
                 d2.setColor(Color.white);
                 d2.fillRect(0, 0, this.getWidth(), this.getHeight());
             }
             super.paintComponent(g);  
             //Сетка///////////////////////////////////////////////////////
	         BufferedImage imag1;// поверхность рисования 2
             imag1 = CopyImage.deepCopy(imag);
             Graphics g__ = imag1.getGraphics();
	         Graphics2D g2__ = (Graphics2D)g__;
             if(grid.isSelected()) {
	     		 Color c = new Color(0, 0, 150);//<-- color
	     		 g2__.setColor(c);
	     		 int interval = 50;
	     		 //Рисуем сетку
	     		 for(int i = interval; i < imag1.getWidth(); i += interval)
	     			 g2__.drawLine(i * (scale + 1),0,i * (scale + 1),imag1.getHeight() * (scale + 1));
	     		 for(int i = interval; i < imag1.getHeight(); i += interval)
	    			 g2__.drawLine(0,i * (scale + 1),imag1.getWidth() * (scale + 1),i * (scale + 1));
	     		 //Подпишем сетку
	     		for(int i = interval; i < imag1.getHeight(); i += interval)
	     		{
	     			g2__.setStroke(new  BasicStroke(1.0f));
	     			//String str = new  String(Integer.toString(i));
	     			String str = new  String(Integer.toString(imag1.getHeight() - i));
	     			g2__.setFont(new  Font("Arial", 0, 12));
	     			g2__.drawString(str, 1, i * (scale + 1) - 5);
	     		}
	     		for(int i = interval; i < imag1.getWidth(); i += interval)
	     		{
	     			g2__.setStroke(new  BasicStroke(1.0f));
	     			String str = new  String(Integer.toString(i));
	     			g2__.setFont(new  Font("Arial", 0, 12));
	     			g2__.drawString(str, i * (scale + 1) + 3, 12);
	     		}
             }
     		 //////////////////////////////////////////////////////////////
             BufferedImage imag2;// поверхность рисования 2
             imag2 = CopyImage.deepCopy(imag1);
         	 //Указатель текущего пикселя при увеличении///////////////////
             if(checkBox.isSelected()) {
                 Graphics g___ = imag2.getGraphics();
    	         Graphics2D g2___ = (Graphics2D)g___;
            	 Color c = new Color(0, 0, 150);//<-- color
            	 g2___.setColor(c);
            	 //g2__.drawLine(xp1, yp1, xp2, yp2);
            	 g2___.setStroke(new  BasicStroke(1.0f));
            	 //Найдём реальные координаты пикселя
            	 Double vv = Math.pow(2, scale);
            	 int tx_ = (int) ((Math.floor(tx / vv.intValue()))*vv.intValue());
            	 int ty_ = (int) ((Math.floor(ty / vv.intValue()))*vv.intValue());
            	 Double a1 = Math.pow(2.0, scale)*vArea, b1 = Math.pow(2.0, scale)*vArea;
            	 if(vArea == 1)
            		 g___.drawRect(tx_, ty_, a1.intValue(), b1.intValue());
            	 else
            		 g___.drawRect(tx_ - a1.intValue()/2, ty_ - b1.intValue()/2, a1.intValue(), b1.intValue());
                 /*
                  int tx_ = (int) ((Math.floor(e.getX() / (scale+1)))*(scale + 1));
            	  int ty_ = (int) ((Math.floor(e.getY() / (scale+1)))*(scale + 1));
            	  g2.setStroke(new  BasicStroke((float) Math.pow(2.0, scale)));
            	  g2.drawLine(xPad, yPad, e.getX(), e.getY());
            	  wa.setImage(imag);//Сохраним в буфере
            	  */
             }
             //////////////////////////////////////////////////////////////
             g.drawImage(imag2, 0, 0,this); 
          }
     }
     /**
      * Фильтр картинок
      *
      */
     class TextFileFilter extends FileFilter 
     {
         private String ext;
         public TextFileFilter(String ext)
         {
             this.ext=ext;
         }
         public boolean accept(java.io.File file)
         {
              if (file.isDirectory()) return true;
              return (file.getName().endsWith(ext));
         }
         public String getDescription() 
         {
              return "*"+ext;
         }
     }
     
     /**
      * Узнаем размеры
      */
     public coordinateXY isDimension() {
    	 coordinateXY xy;
    	 if(!wa.ifPaint()) {
    		 xy = new coordinateXY(japan.getWidth(), japan.getHeight());
    	 }
    	 else
    	 {
    		 xy = new coordinateXY(wa.getWH().getX(), wa.getWH().getY());
    	 }
    	 return xy;
     }
     
     /**
      * Обновление области редактирования
      */
     public static void isReapaint() {
    	 japan.repaint();
     }
     
     /**
      * Сохраним запись о текущих координаьтах
      * @param x текущая координата курсора по x
      * @param y текущая координата курсора по y
      * @param lblNewLabel куда вставляем
      */
     public void SetCooordinate(int x, int y, JLabel lblNewLabel) {
    	 int dddx = getStartCoordinate.getStartX(optionStore.getOpenFileName());
		 int dddy = getStartCoordinate.getStartY(optionStore.getOpenFileName());
    	 //Получим
    	 Double vv = Math.pow(2, scale);
    	 int tx_ = (int) (Math.floor(x / vv.intValue()));
    	 int ty_ = imag.getHeight()/(int)(Math.pow(2, scale)) - (int) (Math.floor(y / vv.intValue()));
    	 lblNewLabel.setText((dddx + tx_)+"; "+(dddy - 1 + ty_)+" ПКС");
    	 
    	 
    	 //String formattedDouble1 = new DecimalFormat("#0.00000").format(((double)dataBox.lon_+(((double)tx_+1)*100.0/(double)dataBox.tx_)/10.0));
    	 //String formattedDouble2 = new DecimalFormat("#0.00000").format(((double)dataBox.lat_+(((double)ty_+1)*100.0/(double)dataBox.ty_)/10.0));
    	 //Расчитаем координаты
    	 String formattedDouble1;
    	 String formattedDouble2;
    	 //formattedDouble1 = new DecimalFormat("#0.00000").format(((double)dataBox.lon_ + (((double)tx_+1)*100.0/(double)dataBox.tx_)/10.0));
    	 //formattedDouble2 = new DecimalFormat("#0.00000").format(((double)dataBox.lat_ + (((double)ty_+1)*100.0/(double)dataBox.ty_)/10.0));
    	 formattedDouble1 = new DecimalFormat("#0.00000").format(dataBox.dx*(double)(dddx + tx_)+dataBox.lon_);
    	 formattedDouble2 = new DecimalFormat("#0.00000").format(dataBox.dy*(double)(dddy - 1 + ty_)+dataBox.lat_);
    	 //System.out.println("lon "+(((double)tx_+1)*100.0/(double)dataBox.tx_)/10.0);
    	 dataBox.lblLonLat.setText("Долгота: "+formattedDouble1+"; "+"Широта: "+ formattedDouble2);
     }
     
     /**
      * Откроем найденый файл
      * @param parentPath путь до каталога в котором хранится index файл
      * @param path путь до файла
      * @param fName имя файла
      */
     public static void SearchOpenFIle(String parentPath, String path, String fName) {
    	 try
		   {
          //Подстраиваем размеры формы и панели под размеры изображения
         	String rd[];
 			rd = new String[3];
 			//rd[0]="38401-39600.18001-19200";
 			rd[0]=fName;
 			rd[1]="Output";
 			rd[2]="PNG";
          //imag = ImageIO.read(iF);
          //loading=true;
 			Read rdd =new Read();
 			//imag = rdd.readData(rd, parentPath);
 			/*
 			if(parentPath.endsWith("index"))
 			{
 				String sst = parentPath.substring(0, parentPath.length() - 6);
 				parentPath = sst;
 			}
 			*/
 			imag = rdd.readData(rd, path + "\\",index);
          //f.setSize(imag.getWidth()+40, imag.getWidth()+80);
          panel_.setPreferredSize(new Dimension(imag.getWidth()+40, imag.getHeight()+80));
          japan.setSize(imag.getWidth(), imag.getWidth());
          japan.repaint();
          wa.setImage(imag);//Сохраним в буфер
          panel_.repaint();
          wa.snapshot();//Делаем снимок области
      } 
		    catch (FileNotFoundException ex) {
          JOptionPane.showMessageDialog(panel_, "Такого файла не существует");
      } 
		    catch (IOException ex) {
          JOptionPane.showMessageDialog(panel_, "Исключение ввода-вывода");
      }
		    catch (Exception ex) {
      }
     }
     
     private void isButton3Click() {
         	if(rezhim != 11 && rezhim != 15 && rezhim != 16)
         		wa.snapshot();//Делаем снимок области
    	// japan.repaint();
    	//rezhim = 11;
    	//sost=1;
    	//  wa.setDeDot(0, 0);//Сохраним сдвиги
        //  xPad=0;
       //   yPad=0;
          //xf=e.getX();
          //yf=e.getY();
       //   Double vv = Math.pow(2*vArea, scale);
       //   xf = (int) ((Math.floor(0 / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
     //	  yf = (int) ((Math.floor(0 / vv.intValue()))*vv.intValue()) + vv.intValue()/2;
          pressed=true;
     }
          
          
     
     public void funcVK_C() {
    	//if(rezhim==11) {
			wa.setBufer();
			isButton3Click();
			//System.out.println("copy");
		//}
     }
     public void funcVC_V() {
    	 rezhim=11;
    	 sost=1;
    	//Если делали перемещение или выделение
     	  if(tool == 11  && (sost==2 || sost == 1 || (sost >= 10 && sost <= 80))) {
     		   imag = wa.getImage();//Уберём рамку
     		  	Graphics g = imag.getGraphics();
     		  	wa.paintDelArea(g);//Перекрасим область под выделением
     	  		wa.dDrawImage(imag);//Нарисуем из буфера
     	  		wa.setImage(imag);//Примем изображение за эталон
     	  		wa.ifArea();//Отметим что рамка не отрисовывается
     	  		sost = 0;//Кликнули не по области
     	  }
     	  else sost = 2;//Если выделение не сделано
 		//Получим изображение из буфера
 	  	  wa.paste();
 	  	  //Прорисуем изображение на экране
 	  	  //imag = wa.getImage();
 	        //wa.dDrawImage(imag);
 	  	  //japan.repaint();
 	  	  imag = wa.getImage();
 			  Graphics g = imag.getGraphics();
 	        Graphics2D g2 = (Graphics2D)g;
 	        wa.paintDelArea(g2);//Закрасим исходную область
 	        //wa.isResize70(e.getX(), e.getY());
 	        wa.dDrawImage(imag);
 	  	  wa.Draw(g);//Рисует
 	  	  //lblNewLabel.setText(e.getX()+"; "+e.getY()+" ПКС");
 	  	  japan.repaint();
 	  	  //wa.l_snapshot();
     }
     public void funcVC_X() {
    	 if(rezhim==11) {
    			//Скопируем в буфер
    		   	wa.setBufer();
    		   	//Закрасим
    		   	imag = wa.getImage();//Уберём рамку
    		  		Graphics g = imag.getGraphics();
    		  		wa.paintDelThisArea(g);//Перекрасим область под выделением
    		  		wa.setImage(imag);//Примем изображение за эталон   		  
    		  		wa.ifArea();//Отметим что рамка не отрисовывается
    		  		japan.repaint();
    		  		//wa.l_snapshot();
    			}
     }
     public void funcVC_Z() {
    	 wa.l_snapshot();   
    	 imag = wa.getImage();
    	 japan.repaint();
     }
     public void funcVC_Y() {
    	 wa.r_snapshot();
		 imag = wa.getImage();
		 japan.repaint();
     }
}