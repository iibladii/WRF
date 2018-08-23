package main_form;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JLabel;

import function.RotateImage;
import function.Buffer;
import function.CopyImage;
import function.SubPaint;
import type.coordinateXY;

/*
 * Создано для хранения информации о выделенной области
 */
public class workAreas {
	Boolean ch = false;
	BufferedImage iarea;//Тут храним копию изображения выделенной области
	BufferedImage bi;//Тут хранится копия всего изображения до выделения
	private int bx1, bx2, by1, by2;//Базовые координаты (задаём когда обводим)
	private int x1, y1;//Левые верхние координаты
	private int x2, y2;//Правые нижние координаты
	private int dx, dy;//смещение области по x1 y1 относительно точки захвата мышкой
	private int pos;//Позиция в которой был сделан клик
	public static JLabel label;//Тут указываем размеры области выделения
	private int flNoBackground = 1;//0 - выделение с прозрачным фоном; 1 - выделение со сплошным фоном
	private int lr = 6;
	
	/*
	private void Repainting() {
		//BufferedImage bf = new BufferedImage(iarea.getWidth() + 4, iarea.getHeight() + 4,    BufferedImage.TYPE_INT_RGB);
		//Скопируем изображение прорисоваврамку вокруг него
		Graphics g = iarea.getGraphics();
		Graphics2D g2 = (Graphics2D)g;
        Color c = new Color( 0, 0, 150 );
        g2.setColor(c);
        int flo = 0;//0 - вне области/внутри области
        for(int i = 0; i < iarea.getWidth(); i++)
        	for(int j = 0; j < iarea.getHeight(); j++)
        	{
        		if(flo == 0)
        			g.drawLine(i, j, i, j);
        		//Узнаем где находимся (внутри области или вне области)
        		if(new Color(iarea.getRGB(i, j)) == (new Color(0,0,150)))
        		{
        			if(flo == 0)
        				flo = 1;
        			else flo =0;
        		}
        	}
	}
	*/
	
	/**
	 * Получим размеры выделенной области
	 * @return width and height
	 */
	public coordinateXY getWH() {
		return new coordinateXY(x2 - x1, y2 - y1);
	}
	
	public workAreas(JLabel label_) {
		this.label = label_;
		this.label.setText("0.0; 0.0 ПКС");
	}
	
	public static BufferedImage deepCopy(BufferedImage bi) {
	    ColorModel cm = bi.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	
	/**
	 * Инициализация области
	 * @param w width
	 * @param h height
	 */
	public workAreas(int w, int h) {
		//инициализация буфера
        bi = new  BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D d22 = (Graphics2D) bi.createGraphics();
        d22.setColor(Color.white);
        d22.fillRect(0, 0, w, h);
	}
	
	/**
	 * Сохранение всего изображения
	 * @param image сохраняемое изображение
	 */
	public void setImage(BufferedImage image) {
		bi = deepCopy(image);
	}
	
	public BufferedImage getImage() {
		return deepCopy(bi);
	}
	
	public void setCoord(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	/**
	 * Определим прорисовано ли выделение
	 * @return
	 */
	public Boolean ifPaint() {
		if(ch == true) {
			return true;
		}
		else
			return false;
	}
	
	public void setCh(Boolean b) {
		ch = b;
	}
	
	/**
	 * Вызываем когда работаем с любым инструментом помимо выделения
	 * Используется для сброса области
	 */
	public Boolean ifArea() {
		if(ch == true) {
			flNoBackground = 1;//Прорисовка области без прозрачного фона
			ch = false;
			x1=-1000;
			x2=-1000;
			y1=-1000;
			y2=-1000;
			bx1=-1000;
			bx2=-1000;
			by1=-1000;
			by2=-1000;
			label.setText("0.0; 0.0 ПКС");
			return true;
		}
		else
			return false;		
	}
	
	/**
	 * Прорисовка элементов рамки
	 * @param g место куда рисуем
	 */
	public void Draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color(0,0,150));
		ch = true;
		g.drawRect(x1, y1, (x2-x1), (y2-y1));
        g.fillRect(x1-lr/2, y1-lr/2, lr, lr);//Верхний левый
        g.fillRect(x2-lr/2, y1-lr/2, lr, lr);//Верхний правый
        g.fillRect(x1-lr/2, y2-lr/2, lr, lr);//Нижний левый
        g.fillRect(x2-lr/2, y2-lr/2, lr, lr);//Нижний правый
        g.fillRect(x1+(x2-x1)/2-lr/2, y1-lr/2, lr, lr);//Верх центр
        g.fillRect(x1+(x2-x1)/2-lr/2, y2-lr/2, lr, lr);//Низ центр
        g.fillRect(x1-lr/2, y1+(y2-y1)/2-lr/2, lr, lr);//Лево центр
        g.fillRect(x2-lr/2, y1+(y2-y1)/2-lr/2, lr, lr);//Право центр
        label.setText((x2 - x1) + "; " + (y2 - y1) + " ПКС");
	}
	
	/**
	 * Прорисовка элементов рамки
	 * @param g место куда рисуем
	 */
	public void Draw(BufferedImage imm) {
		Graphics g = imm.getGraphics();
		Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color(0,0,150));
		ch = true;
		g.drawRect(x1, y1, (x2-x1), (y2-y1));
        g.fillRect(x1-lr/2, y1-lr/2, lr, lr);//Верхний левый
        g.fillRect(x2-lr/2, y1-lr/2, lr, lr);//Верхний правый
        g.fillRect(x1-lr/2, y2-lr/2, lr, lr);//Нижний левый
        g.fillRect(x2-lr/2, y2-lr/2, lr, lr);//Нижний правый
        g.fillRect(x1+(x2-x1)/2-lr/2, y1-lr/2, lr, lr);//Верх центр
        g.fillRect(x1+(x2-x1)/2-lr/2, y2-lr/2, lr, lr);//Низ центр
        g.fillRect(x1-lr/2, y1+(y2-y1)/2-lr/2, lr, lr);//Лево центр
        g.fillRect(x2-lr/2, y1+(y2-y1)/2-lr/2, lr, lr);//Право центр
        label.setText((x2 - x1) + "; " + (y2 - y1) + " ПКС");
	}
	
	/**
	 * Проверка принадлежности точки области
	 * @param x координата x
	 * @param y координата y
	 * @return true если точка принадлежит области
	 */
	public Boolean inArea(int x, int y) {
		if(x > x1 && x < x2 && y > y1 && y < y2)
			return true;
		else return false;
	}
	
	/*
	 * Получим координаты
	 * Вернём курсор, который должен быть в данный момент
	 * Cursor.MOVE_CURSOR - выделенная область
     * N_RESIZE_CURSOR - верхний, нижний
     * NE_RESIZE_CURSOR - правый верхний, левый нижний
     * NW_RESIZE_CURSOR - правый нижний, левый верхний
     * E_RESIZE_CURSOR - правый, левый
	 */
	public int getCursor(int x, int y) {
		int cursor = Cursor.DEFAULT_CURSOR;
		if(x > x1 && x < x2 && y > y1 && y < y2)
			cursor = Cursor.MOVE_CURSOR;
		if((x > x1 - lr/2 && x < x1 + lr/2 && y > y1 - lr/2 && y < y1 + lr/2) || (x > x2 - lr/2 && x < x2 + lr/2 && y > y2 - lr/2 && y < y2 + lr/2))
			cursor = Cursor.NW_RESIZE_CURSOR;
		else
			if((x > x1 - lr/2 && x < x1 + lr/2 && y > y2 - lr/2 && y < y2 + lr/2) || (x > x2 - lr/2 && x < x2 + lr/2 && y > y1 - lr/2 && y < y1 + 5))
				cursor = Cursor.NE_RESIZE_CURSOR;
			else
				if((x > x1+(x2-x1)/2-lr/2 && x < x1+(x2-x1)/2+lr/2 && y > y1 - lr/2 && y < y1 + lr/2) || (x > x1+(x2-x1)/2-lr/2 && x < x1+(x2-x1)/2+lr/2 && y > y2 - lr/2 && y < y2 + lr/2))
					cursor = Cursor.N_RESIZE_CURSOR;
				else
					if((x > x1 - lr/2 && x < x1 + lr/2 && y > y1+(y2-y1)/2-lr/2 && y < y1+(y2-y1)/2+lr/2) || (x > x2 - 5 && x < x2 + lr/2 && y > y1+(y2-y1)/2-lr/2 && y < y1+(y2-y1)/2+lr/2))
						cursor = Cursor.E_RESIZE_CURSOR;
		return cursor;
	}
	
	/*
	 * Получим точку в которую кликнули
	 * 0  0  0  0  0
	 * 0  10 20 30 0
	 * 0  80 90 40 0
	 * 0  70 60 50 0
	 * 0  0  0  0  0
	 */
	public int getDot(int x, int y) {
		int cursor = 0;
		if(x > x1 && x < x2 && y > y1 && y < y2)
			cursor = 90;
		if(x > x1 - 5 && x < x1 + 5 && y > y1 - 5 && y < y1 + 5)
			cursor = 10;
		else
			if(x > x2 - 5 && x < x2 + 5 && y > y2 - 5 && y < y2 + 5)
				cursor = 50;
			else
				if(x > x1 - 5 && x < x1 + 5 && y > y2 - 5 && y < y2 + 5)
					cursor = 30;
				else
					if(x > x2 - 5 && x < x2 + 5 && y > y1 - 5 && y < y1 + 5)
						cursor = 70;
					else
						if(x > x1+(x2-x1)/2-5 && x < x1+(x2-x1)/2+5 && y > y1 - 5 && y < y1 + 5)
							cursor = 20;
						else
							if(x > x1+(x2-x1)/2-5 && x < x1+(x2-x1)/2+5 && y > y2 - 5 && y < y2 + 5)
								cursor = 60;
							else
								if(x > x1 - 5 && x < x1 + 5 && y > y1+(y2-y1)/2-5 && y < y1+(y2-y1)/2+5)
									cursor = 80;
								else
									if(x > x2 - 5 && x < x2 + 5 && y > y1+(y2-y1)/2-5 && y < y1+(y2-y1)/2+5)
										cursor = 40;
		return cursor;
	}

	/**
	 * Устанавливает координату точки в которую произошел клик при перетаскивании области
	 * @param x Координата x
	 * @param y Координата y
	 */
	public void setDeDot(int x, int y) {
		dx = x - x1;
		dy = y - y1;
	}
	
	/**
	 * Прорисовка с перемещением
	 * @param g Место куда рисуем
	 * @param x1_ Координата x
	 * @param y1_ Координата y
	 */
	public void dDraw(Graphics g, int x1_, int y1_) {
		//System.out.println("dx:"+dx+"  dy:"+dy);
		int vx = (x2 - x1);
		int vy = (y2 - y1);
		x1 = x1_ - dx;
		y1 = y1_ - dy;
		x2 = x1 + vx;
		y2 = y1 + vy;
		//System.out.println("x2: "+x2+ "    y2: "+y2);
		Draw(g);
	}
	
	/**
	 * Прорисуем изображение в выделенной области (из буфера)	
	 * @param im Перерисовываемое изображение
	 */
	public void dDrawImage(BufferedImage im) {
		/*
		if(flNoBackground == 1)
		{
			Graphics g = im.getGraphics();
			Graphics2D g2 = (Graphics2D)g;
			for(int x = 0; x < x2 - x1; x++)
				for(int y = 0; y < y2 -y1; y++)
				{
					Color c = new Color(iarea.getRGB(x, y));//<-- color
					//if(c != new Color(0, 0, 150))
					//{
						g2.setColor(c);
						g2.drawLine(x1 + x, y1 + y, x1 + x, y1 + y);
						//}
				}
		}
		else
		{*/
			Graphics g = im.getGraphics();
			Graphics2D g2 = (Graphics2D)g;
			for(int x = 0; x < x2 - x1; x++)
				for(int y = 0; y < y2 -y1; y++)
				{
					Color c = new Color(iarea.getRGB(x, y));//<-- color
					if(!c.equals(new Color(0, 0, 150)))
					{
						g2.setColor(c);
						g2.drawLine(x1 + x, y1 + y, x1 + x, y1 + y);
					}
				}
		//}
	}
	
	/**
	 * Удаление
	 */
	public void delImage(BufferedImage im) {
		
		Graphics g = im.getGraphics();
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fill(new Rectangle2D.Float(0, 0, im.getWidth(), im.getHeight()));
	}
	
	/**
	 * Сохраним выделенную часть изображения без фона
	 */
	public void saveImageNoFon(BufferedImage ima) {
		//x2++;//Поправка на 1 пиксель для корректности вычислений
		//y2++;
		flNoBackground = 0;
		if(x2 - x1 > 0 && y2 - y1 > 0)//нельзя сохранить пустоту
		{
			this.iarea = CopyImage.deepCopy(ima);
		}
		bx1 = x1;
		bx2 = x2;
		by1 = y1;
		by2 = y2;
	}
	
	/**
	 * Сохраним выделенную часть изображения
	 */
	public void saveImage() {
		x2++;//Поправка на 1 пиксель для корректности вычислений
		y2++;
		
		if(x2 - x1 > 0 && y2 - y1 > 0)//нельзя сохранить пустоту
		{
			this.iarea = bi.getSubimage( x1, y1, x2 - x1, y2 - y1);
		}
		bx1 = x1;
		bx2 = x2;
		by1 = y1;
		by2 = y2;
	}
	
	/**
	 * Сохраним выделенную часть изображения
	 */
	public void saveImage(BufferedImage ima) {
		
		if(x2 - x1 > 0 && y2 - y1 > 0)//нельзя сохранить пустоту
		{
			this.iarea = CopyImage.deepCopy(ima);
		}
		bx1 = x1;
		bx2 = x2;
		by1 = y1;
		by2 = y2;
	}
	
	/**
	 * Закрасим место выделения при смещении
	 */
	public void paintDelArea(Graphics g) {
		if(flNoBackground == 1)
		{
		//на месте выделения рисуем белый квадрат
		        Graphics2D g2 = (Graphics2D)g;
		        Color c = new Color( 255, 255, 255 );
		        g2.setColor(c);
				g2.fill(new Rectangle2D.Float(bx1, by1, (bx2-bx1), (by2-by1)));
		}
	}
	
	/**
	 * Закраска области выделения при вырезании
	 */
	public void paintDelThisArea(Graphics g) {
		if(flNoBackground == 1)
		{
		//на месте выделения рисуем белый квадрат
		        Graphics2D g2 = (Graphics2D)g;
		        Color c = new Color( 255, 255, 255 );
		        g2.setColor(c);
				g2.fill(new Rectangle2D.Float(bx1, by1, (bx2-bx1), (by2-by1)));
		}
		/*
		else
		{
			Graphics2D g2 = (Graphics2D)g;
	        Color c = new Color( 255, 255, 255 );
	        g2.setColor(c);
			for(int x = 0; x < x2 - x1; x++)
				for(int y = 0; y < y2 -y1; y++)
				{
					if((new Color(iarea.getRGB(x, y))).equals(new Color(bi.getRGB(x1 + x, y1 + y))))
					{
						g2.setColor(c);
						g2.drawLine(x1 + x, y1 + y, x1 + x, y1 + y);
					}
				}
		}
		*/
	}
	
	public void isTurn() {
		
	}
	
	/**
	 * Вставим выделенную область в буфер
	 */
	public void setBufer() {
		if(ch==true)
		{
			Buffer.setImage(iarea);
		}
		else Buffer.setImage(bi);
	}
	
	/*
	 * Замена цвета
	 */
	public void RepaintColor(BufferedImage bi, Color colorIn, Color colorOut) {
		SubPaint.isRepaint( bi,  colorIn,  colorOut);
	}
	
	/**
	 * Поворот изображения
	 */
	public void RotateImage() {
		iarea = RotateImage.rotate(iarea, 1.5708);
		//Поправка на лишний пиксель
		x2 = x1+iarea.getWidth()-1;
		y2 = y1+iarea.getHeight()-1;		
	}
	
	/*
	 * Масштабирование изображения
	 */
	public void resize3r(int xSize, int ySize) {
		BufferedImage scaled = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = scaled.createGraphics();
        g2.drawImage(iarea, 0, 0, xSize, ySize, null);
        iarea = scaled;
	}
	
	/*
	 * Сдвиг право середина
	 */
	public void isResize40(int xDot, int yDot) {
		if(x1 < xDot)
		{
			//Установим точки
			x2 = xDot;
			//y2 = yDot;
			//Изменим размер
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * Сдвиг лево середина
	 */
	public void isResize80(int xDot, int yDot) {
		if(x2 > xDot)
		{
			//Установим точки
			x1 = xDot;
			//Изменим размер
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * Сдвиг верх середина
	 */
	public void isResize20(int xDot, int yDot) {
		if(y2 > yDot)
		{
			//Установим точки
			y1 = yDot;
			//Изменим размер
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * Сдвиг низ середина
	 */
	public void isResize60(int xDot, int yDot) {
		if(y1 < yDot)
		{
			//Установим точки
			y2 = yDot;
			//Изменим размер
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * Сдвиг верх лево
	 */
	public void isResize10(int xDot, int yDot) {
		if(x2 > yDot && y2 > yDot)
		{
			//Установим точки
			x1 = xDot;
			y1 = yDot;
			//Изменим размер
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * Сдвиг низ право
	 */
	public void isResize50(int xDot, int yDot) {
		if(y1 < yDot && x1 < xDot)
		{
			//Установим точки
			x2 = xDot;
			y2 = yDot;
			//Изменим размер
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * Сдвиг верх право
	 */
	public void isResize30(int xDot, int yDot) {
		if(y1 + (yDot - y1) > y1 && x2 > xDot)
		{
			//Установим точки
			x1 = xDot;
			y2 = yDot;
			//Изменим размер
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * Сдвиг низ лево
	 */
	public void isResize70(int xDot, int yDot) {
		if(x1 + (xDot - x1) > x1 && y2 > yDot)
		{
			//Установим точки
			x2 = xDot;
			y1 = yDot;
			//Изменим размер
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/**
	 * Произвольное масштабирование
	 * @param sx width
	 * @param sy height
	 */
	public void manualResize(int sx, int sy) {
		x2 = x1 + sx;
		y2 = y1 + sy;
		resize3r(sx, sy);
	}
	
	/**
	 * Вставка из буфера
	 * @param x координата по оси x
	 * @param y координата по оси y
	 */
	public void paste() {
		iarea = Buffer.getImage();
		x1 = 0;
		y1 = 0;
		x2 = x1 + iarea.getWidth();
		y2 = y1 + iarea.getHeight();
		dx = 0;
		dy = 0;
	}
	
	public void snapshot() {
		HistoryBuf.add(bi);
	}
	public void l_snapshot() {
		bi = HistoryBuf.left();
	}
	public void r_snapshot() {
		bi = HistoryBuf.right();
	}
}
