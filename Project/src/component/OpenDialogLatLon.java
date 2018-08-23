package component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import function.searchFile;
import function.searchFileName;
import main_form.picture;
import main_form.work_form;
import type.str2;

import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.text.Format;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class OpenDialogLatLon extends JDialog{
	private JComboBox comboBox;
	private JTextField txtCusersmegroupgmailcompictures;
	private JTextField txtCusersmegroupgmailcompicturesindex;
	
	class DigitFilter extends DocumentFilter {
	    private static final String DIGITS = "\\d+";

	    @Override
	    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {

	        if (string.matches(DIGITS)) {
	            super.insertString(fb, offset, string, attr);
	        }
	    }

	    @Override
	    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
	        if (string.matches(DIGITS)) {
	            super.replace(fb, offset, length, string, attrs);
	        }
	    }
	}
	
		public OpenDialogLatLon() throws ParseException
		{
			super(work_form.getFrame(), "Изменение размера", true);
			setTitle("\u041E\u0442\u043A\u0440\u044B\u0442\u044C \u0444\u0430\u0439\u043B");
			setLocationRelativeTo(null);
			// При активизации кнопки ОК диалогове окно закрывается.
	 
					JButton ok = new JButton("\u041E\u0442\u043A\u0440\u044B\u0442\u044C");
					
					JPanel panel = new JPanel();
					panel.add(ok);
					getContentPane().add(panel, BorderLayout.SOUTH);
					
					JButton end = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
					panel.add(end);
					
					JPanel panel_1 = new JPanel();
					getContentPane().add(panel_1, BorderLayout.CENTER);
					panel_1.setLayout(null);
					
					JLabel lbllat = new JLabel("\u0428\u0438\u0440\u043E\u0442\u0430 (lat):");
					lbllat.setIcon(null);
					lbllat.setBounds(10, 36, 116, 14);
					panel_1.add(lbllat);
					
					JLabel lbllon = new JLabel("\u0414\u043E\u043B\u0433\u043E\u0442\u0430 (lon):");
					lbllon.setIcon(null);
					lbllon.setBounds(10, 65, 116, 14);
					panel_1.add(lbllon);
					//doc1.setDocumentFilter(new DigitFilter());
					
					JLabel label_2 = new JLabel("\u041A\u043E\u0440\u043D\u0435\u0432\u043E\u0439 \u043A\u0430\u0442\u0430\u043B\u043E\u0433:");
					label_2.setBounds(10, 11, 126, 14);
					panel_1.add(label_2);
					
					txtCusersmegroupgmailcompictures = new JTextField();
					txtCusersmegroupgmailcompictures.setColumns(10);
					txtCusersmegroupgmailcompictures.setBounds(136, 8, 308, 20);
					panel_1.add(txtCusersmegroupgmailcompictures);
					
					JButton btnNewButton = new JButton("...");
					
					btnNewButton.setToolTipText("\u0423\u043A\u0430\u0437\u0430\u0442\u044C \u043A\u0430\u0442\u0430\u043B\u043E\u0433 \u0441 \u0444\u0430\u0439\u043B\u0430\u043C\u0438");
					btnNewButton.setBounds(454, 8, 30, 21);
					panel_1.add(btnNewButton);
					
					
					JFormattedTextField formattedTextField;
					DecimalFormatSymbols otherSymbols_ = new DecimalFormatSymbols(Locale.US);
					String pattern_ = "##0.00000000";
					DecimalFormat decimalFormat_ = new DecimalFormat(pattern_, otherSymbols_);
					formattedTextField = new JFormattedTextField(decimalFormat_);
					/*
					NumberFormat dnf;
					dnf = DecimalFormat.getInstance();
					dnf.setMaximumFractionDigits(8);
					dnf.setMinimumFractionDigits(0);
					dnf.setMaximumIntegerDigits(3);
					dnf.setMinimumIntegerDigits(1);
					formattedTextField = new JFormattedTextField(dnf);
					*/
					
					formattedTextField.setText("0.00000000");
					formattedTextField.setBounds(136, 33, 95, 20);
					panel_1.add(formattedTextField);
					
					JFormattedTextField formattedTextField_1;
					DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
					String pattern = "##0.00000000";
					DecimalFormat decimalFormat = new DecimalFormat(pattern, otherSymbols);
					formattedTextField_1 = new JFormattedTextField(decimalFormat);
					/*
					NumberFormat dnf1;
					dnf1 = DecimalFormat.getInstance();
					dnf1.setMaximumFractionDigits(8);
					dnf1.setMinimumFractionDigits(0);
					dnf1.setMaximumIntegerDigits(3);
					dnf1.setMinimumIntegerDigits(1);
					formattedTextField_1 = new JFormattedTextField(dnf1);
					*/
					
					
					formattedTextField_1.setText("0.00000000");
					formattedTextField_1.setBounds(136, 62, 95, 20);
					panel_1.add(formattedTextField_1);
					
					txtCusersmegroupgmailcompicturesindex = new JTextField();
					txtCusersmegroupgmailcompicturesindex.setColumns(10);
					txtCusersmegroupgmailcompicturesindex.setBounds(241, 33, 203, 20);
					txtCusersmegroupgmailcompicturesindex.setVisible(false);
					panel_1.add(txtCusersmegroupgmailcompicturesindex);
					JFileChooser jf1 = new JFileChooser();
					jf1.setFileSelectionMode(JFileChooser.FILES_ONLY);
					
					JLabel label = new JLabel("\u0418\u043C\u044F \u0444\u0430\u0439\u043B\u0430:");
					label.setVisible(false);
					label.setBounds(241, 65, 64, 14);
					panel_1.add(label);
					
					JLabel label_1 = new JLabel("");
					label_1.setVisible(false);
					label_1.setBackground(Color.GREEN);
					label_1.setBounds(315, 65, 169, 14);
					panel_1.add(label_1);
					
					JLabel lblNewLabel = new JLabel("<html>Разрешение<br>топографической<br>основы</html>");
					lblNewLabel.setBounds(10, 90, 126, 48);
					panel_1.add(lblNewLabel);
					
					JComboBox comboBox = new JComboBox();
					comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "30s", "1deg", "2deg", "2m", "3m", "5m", "10m", "1km", "5km", "10km"}));
					comboBox.setSelectedIndex(0);
					comboBox.setToolTipText("");
					this.comboBox=comboBox;
					comboBox.setBounds(136, 93, 308, 20);
					panel_1.add(comboBox);
					setSize(510, 217);
					
					
					//Обработка фокуса
					/*
					if(!formattedTextField.getText().equals(null) && !formattedTextField_1.getText().equals(null) && !txtCusersmegroupgmailcompictures.getText().equals(null) && !txtCusersmegroupgmailcompicturesindex.getText().equals(null))
					{
						String find = "";
						//Поиск имени искомого файла
						String rd[];
						rd = new String[5];
						rd[0]="--locate";
						rd[1]=formattedTextField.getText();
						rd[2]=formattedTextField_1.getText();
						rd[3]="Output";
						rd[4]="PNG";
						//try {
						//	find  = searchFileName.fName(rd, txtCusersmegroupgmailcompicturesindex.getText() + "\\" + "");
						//} catch (Exception e) {
							// TODO Auto-generated catch block
						//	e.printStackTrace();
						//}
					}
					formattedTextField_1.addFocusListener(new CustomListener(formattedTextField_1, label_1, formattedTextField, txtCusersmegroupgmailcompicturesindex, txtCusersmegroupgmailcompictures));
					formattedTextField.addFocusListener(new CustomListener(formattedTextField_1, label_1, formattedTextField, txtCusersmegroupgmailcompicturesindex, txtCusersmegroupgmailcompictures));
					*/
					
					
					JFileChooser jf = new JFileChooser();
					//разрешить только выбор папок
					jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					
					btnNewButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							int  result = jf.showOpenDialog(null);
				            if(result==JFileChooser.APPROVE_OPTION)
				            {
				            	//Выбрали каталог
				            	File f = jf.getSelectedFile();
				            	if (f.isDirectory())
				            	{
				            		File[] filesInDir = f.listFiles();
				            		txtCusersmegroupgmailcompictures.setText(jf.getSelectedFile().toString());
				            	}
				            }
						}
					});
					
					//События
					ok.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							//
							isFileSearch(formattedTextField, formattedTextField_1);
							/*
							if(!formattedTextField.equals(null) && !formattedTextField_1.equals(null) && !txtCusersmegroupgmailcompictures.equals(null) && !txtCusersmegroupgmailcompicturesindex.equals(null))
							{
								String find = "";
								//Поиск имени искомого файла
								String rd[];
								rd = new String[5];
								rd[0]="--locate";
								rd[1]=formattedTextField.getText();
								rd[2]=formattedTextField_1.getText();
								rd[3]="Output";
								rd[4]="PNG";
								try {
									find  = searchFileName.fName(rd, txtCusersmegroupgmailcompicturesindex.getText() + "\\" + "");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//Поиск файла
								searchFile.newPoisk(txtCusersmegroupgmailcompictures.getText(), find);
								String fileLoad = searchFile.getResult();
								if(fileLoad.equals(""))
								{
									JOptionPane J = new JOptionPane();
									JOptionPane.showMessageDialog(J,"<html><h2>Файл с именем: "+ find +" не найден в данном каталоге!!!</i>");
									return;
								}
								else//Загрузка файла если он найден
								{
									picture.SearchOpenFIle(txtCusersmegroupgmailcompicturesindex.getText(), fileLoad, find);
								}
							}*/
							setVisible(false);
						}
					});
					
					/*
					formattedTextField_1.addKeyListener(new KeyListener() {
			       		  public void keyPressed(KeyEvent e) {
			       		  }

			       		public void keyReleased(KeyEvent e) {
			       			if (e.getKeyCode() == KeyEvent.VK_ENTER){
			       				isFileName(formattedTextField_1, label_1, formattedTextField, txtCusersmegroupgmailcompicturesindex, txtCusersmegroupgmailcompictures);
			       		    }
			       		}

			       		  public void keyTyped(KeyEvent e) {
			       		  }

			       		  });
					
					formattedTextField.addKeyListener(new KeyListener() {
			       		  public void keyPressed(KeyEvent e) {
			       		  }

			       		public void keyReleased(KeyEvent e) {
			       			if (e.getKeyCode() == KeyEvent.VK_ENTER){
			       				isFileName(formattedTextField_1, label_1, formattedTextField, txtCusersmegroupgmailcompicturesindex, txtCusersmegroupgmailcompictures);
			       		    }
			       		}

			       		  public void keyTyped(KeyEvent e) { 
			       		  }

			       		  });*/
					
					end.addActionListener(new ActionListener() {
						 
						public void actionPerformed(ActionEvent event) {
							setVisible(false);
						}
						
					});
		}
		
		/*
		private void isFileName(JFormattedTextField jftf1, JLabel label1, JFormattedTextField jftf, JTextField jtf, JTextField jtf_) {
			String find = "";
			//Поиск имени искомого файла
			String rd[];
			rd = new String[5];
			rd[0]="--locate";
			rd[1]=jftf.getText();
			rd[2]=jftf1.getText();
			rd[3]="Output";
			rd[4]="PNG";
			label1.setOpaque(false);
			if(!jftf.getText().equals("") && !jftf1.getText().equals("") && !jtf_.getText().equals("") && !jtf.getText().equals(""))
			{
				try {
					label1.setText(searchFileName.fName(rd, jtf.getText() + "\\" + ""));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			//Поиск файла
			searchFile.newPoisk(txtCusersmegroupgmailcompictures.getText(), label1.getText());
			String fileLoad = searchFile.getResult();
			if(fileLoad.equals(""))
			{
				label1.setBackground(Color.RED);
			}
			else//Загрузка файла если он найден
			{
				label1.setBackground(Color.GREEN);
			}
			label1.setOpaque(true);
			label1.updateUI();
		}*/
		
		/**
		 * Вычисление имени файла
		 * @param formattedTextField
		 * @param formattedTextField_1
		 */
		private void isFileSearch(JFormattedTextField formattedTextField, JFormattedTextField formattedTextField_1) {
			int fl = 0;


			String rootDir = txtCusersmegroupgmailcompictures.getText();

	        File root = new File(rootDir);
	        File[] files = root.listFiles();
	        int i = 0;

	        while(i<files.length) {
	            File firstElement = files[i];
	            File[] subFiles = null;

	            if (firstElement.isDirectory()) {
	                subFiles = firstElement.listFiles();
	            }
	            else {
	                i++;
	                continue;
	            }
	            File[] temp = new File[files.length + subFiles.length];
	            for (int j = 0; j<=i; j++) 
	                temp[j] = files[j];
	            for (int k = 0; k<subFiles.length; k++)
	                temp[i+1+k] = subFiles[k];
	            for (int m = i+1; m<files.length; m++)
	                temp[m+subFiles.length] = files[m];

	            files = temp;
	            i++;
	        }

	        for (File file : files) {
	        	if((file.getPath().lastIndexOf("index")==file.getPath().length()-5) 	) {
	        		//System.out.println(file.getPath());
	        		System.out.println(file.getPath().substring(0, file.getPath().length()-5));//Найден index файл
	        		txtCusersmegroupgmailcompicturesindex.setText(file.getPath());
	        
			
			if(!formattedTextField.equals(null) && !formattedTextField_1.equals(null) && !txtCusersmegroupgmailcompictures.equals(null))
			{
				str2 find = null;
				//Поиск имени искомого файла
				String rd[];
				rd = new String[5];
				rd[0]="--locate";
				rd[1]=formattedTextField.getText();
				rd[2]=formattedTextField_1.getText();
				rd[3]="Output";
				rd[4]="PNG";
				try {
					find  = searchFileName.fName(rd, txtCusersmegroupgmailcompicturesindex.getText() + "\\");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Поиск файла
				String str = searchFile.newPoisk(txtCusersmegroupgmailcompictures.getText(), find, comboBox.getSelectedItem().toString());
				String fileLoad = searchFile.getResult();
				if(fileLoad.equals(""))
				{
					//JOptionPane J = new JOptionPane();
					//JOptionPane.showMessageDialog(J,"<html><h2>Файл с именем: "+ find +" не найден в данном каталоге!!!</i>");
					//return;
				}
				else//Загрузка файла если он найден
				{
					picture.SearchOpenFIle(txtCusersmegroupgmailcompicturesindex.getText(), fileLoad, searchFile.fname);
					return;
				}
			}
			
			
			
			
	        	}
	        }
	        JOptionPane J = new JOptionPane();
	        JOptionPane.showMessageDialog(J,"<html><h2>Файл не найден</html>");
	        return;
		}
}
