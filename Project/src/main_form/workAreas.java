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
 * ������� ��� �������� ���������� � ���������� �������
 */
public class workAreas {
	Boolean ch = false;
	BufferedImage iarea;//��� ������ ����� ����������� ���������� �������
	BufferedImage bi;//��� �������� ����� ����� ����������� �� ���������
	private int bx1, bx2, by1, by2;//������� ���������� (����� ����� �������)
	private int x1, y1;//����� ������� ����������
	private int x2, y2;//������ ������ ����������
	private int dx, dy;//�������� ������� �� x1 y1 ������������ ����� ������� ������
	private int pos;//������� � ������� ��� ������ ����
	public static JLabel label;//��� ��������� ������� ������� ���������
	private int flNoBackground = 1;//0 - ��������� � ���������� �����; 1 - ��������� �� �������� �����
	private int lr = 6;
	
	/*
	private void Repainting() {
		//BufferedImage bf = new BufferedImage(iarea.getWidth() + 4, iarea.getHeight() + 4,    BufferedImage.TYPE_INT_RGB);
		//��������� ����������� ��������������� ������ ����
		Graphics g = iarea.getGraphics();
		Graphics2D g2 = (Graphics2D)g;
        Color c = new Color( 0, 0, 150 );
        g2.setColor(c);
        int flo = 0;//0 - ��� �������/������ �������
        for(int i = 0; i < iarea.getWidth(); i++)
        	for(int j = 0; j < iarea.getHeight(); j++)
        	{
        		if(flo == 0)
        			g.drawLine(i, j, i, j);
        		//������ ��� ��������� (������ ������� ��� ��� �������)
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
	 * ������� ������� ���������� �������
	 * @return width and height
	 */
	public coordinateXY getWH() {
		return new coordinateXY(x2 - x1, y2 - y1);
	}
	
	public workAreas(JLabel label_) {
		this.label = label_;
		this.label.setText("0.0; 0.0 ���");
	}
	
	public static BufferedImage deepCopy(BufferedImage bi) {
	    ColorModel cm = bi.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	
	/**
	 * ������������� �������
	 * @param w width
	 * @param h height
	 */
	public workAreas(int w, int h) {
		//������������� ������
        bi = new  BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D d22 = (Graphics2D) bi.createGraphics();
        d22.setColor(Color.white);
        d22.fillRect(0, 0, w, h);
	}
	
	/**
	 * ���������� ����� �����������
	 * @param image ����������� �����������
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
	 * ��������� ����������� �� ���������
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
	 * �������� ����� �������� � ����� ������������ ������ ���������
	 * ������������ ��� ������ �������
	 */
	public Boolean ifArea() {
		if(ch == true) {
			flNoBackground = 1;//���������� ������� ��� ����������� ����
			ch = false;
			x1=-1000;
			x2=-1000;
			y1=-1000;
			y2=-1000;
			bx1=-1000;
			bx2=-1000;
			by1=-1000;
			by2=-1000;
			label.setText("0.0; 0.0 ���");
			return true;
		}
		else
			return false;		
	}
	
	/**
	 * ���������� ��������� �����
	 * @param g ����� ���� ������
	 */
	public void Draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color(0,0,150));
		ch = true;
		g.drawRect(x1, y1, (x2-x1), (y2-y1));
        g.fillRect(x1-lr/2, y1-lr/2, lr, lr);//������� �����
        g.fillRect(x2-lr/2, y1-lr/2, lr, lr);//������� ������
        g.fillRect(x1-lr/2, y2-lr/2, lr, lr);//������ �����
        g.fillRect(x2-lr/2, y2-lr/2, lr, lr);//������ ������
        g.fillRect(x1+(x2-x1)/2-lr/2, y1-lr/2, lr, lr);//���� �����
        g.fillRect(x1+(x2-x1)/2-lr/2, y2-lr/2, lr, lr);//��� �����
        g.fillRect(x1-lr/2, y1+(y2-y1)/2-lr/2, lr, lr);//���� �����
        g.fillRect(x2-lr/2, y1+(y2-y1)/2-lr/2, lr, lr);//����� �����
        label.setText((x2 - x1) + "; " + (y2 - y1) + " ���");
	}
	
	/**
	 * ���������� ��������� �����
	 * @param g ����� ���� ������
	 */
	public void Draw(BufferedImage imm) {
		Graphics g = imm.getGraphics();
		Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color(0,0,150));
		ch = true;
		g.drawRect(x1, y1, (x2-x1), (y2-y1));
        g.fillRect(x1-lr/2, y1-lr/2, lr, lr);//������� �����
        g.fillRect(x2-lr/2, y1-lr/2, lr, lr);//������� ������
        g.fillRect(x1-lr/2, y2-lr/2, lr, lr);//������ �����
        g.fillRect(x2-lr/2, y2-lr/2, lr, lr);//������ ������
        g.fillRect(x1+(x2-x1)/2-lr/2, y1-lr/2, lr, lr);//���� �����
        g.fillRect(x1+(x2-x1)/2-lr/2, y2-lr/2, lr, lr);//��� �����
        g.fillRect(x1-lr/2, y1+(y2-y1)/2-lr/2, lr, lr);//���� �����
        g.fillRect(x2-lr/2, y1+(y2-y1)/2-lr/2, lr, lr);//����� �����
        label.setText((x2 - x1) + "; " + (y2 - y1) + " ���");
	}
	
	/**
	 * �������� �������������� ����� �������
	 * @param x ���������� x
	 * @param y ���������� y
	 * @return true ���� ����� ����������� �������
	 */
	public Boolean inArea(int x, int y) {
		if(x > x1 && x < x2 && y > y1 && y < y2)
			return true;
		else return false;
	}
	
	/*
	 * ������� ����������
	 * ����� ������, ������� ������ ���� � ������ ������
	 * Cursor.MOVE_CURSOR - ���������� �������
     * N_RESIZE_CURSOR - �������, ������
     * NE_RESIZE_CURSOR - ������ �������, ����� ������
     * NW_RESIZE_CURSOR - ������ ������, ����� �������
     * E_RESIZE_CURSOR - ������, �����
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
	 * ������� ����� � ������� ��������
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
	 * ������������� ���������� ����� � ������� ��������� ���� ��� �������������� �������
	 * @param x ���������� x
	 * @param y ���������� y
	 */
	public void setDeDot(int x, int y) {
		dx = x - x1;
		dy = y - y1;
	}
	
	/**
	 * ���������� � ������������
	 * @param g ����� ���� ������
	 * @param x1_ ���������� x
	 * @param y1_ ���������� y
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
	 * ��������� ����������� � ���������� ������� (�� ������)	
	 * @param im ���������������� �����������
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
	 * ��������
	 */
	public void delImage(BufferedImage im) {
		
		Graphics g = im.getGraphics();
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fill(new Rectangle2D.Float(0, 0, im.getWidth(), im.getHeight()));
	}
	
	/**
	 * �������� ���������� ����� ����������� ��� ����
	 */
	public void saveImageNoFon(BufferedImage ima) {
		//x2++;//�������� �� 1 ������� ��� ������������ ����������
		//y2++;
		flNoBackground = 0;
		if(x2 - x1 > 0 && y2 - y1 > 0)//������ ��������� �������
		{
			this.iarea = CopyImage.deepCopy(ima);
		}
		bx1 = x1;
		bx2 = x2;
		by1 = y1;
		by2 = y2;
	}
	
	/**
	 * �������� ���������� ����� �����������
	 */
	public void saveImage() {
		x2++;//�������� �� 1 ������� ��� ������������ ����������
		y2++;
		
		if(x2 - x1 > 0 && y2 - y1 > 0)//������ ��������� �������
		{
			this.iarea = bi.getSubimage( x1, y1, x2 - x1, y2 - y1);
		}
		bx1 = x1;
		bx2 = x2;
		by1 = y1;
		by2 = y2;
	}
	
	/**
	 * �������� ���������� ����� �����������
	 */
	public void saveImage(BufferedImage ima) {
		
		if(x2 - x1 > 0 && y2 - y1 > 0)//������ ��������� �������
		{
			this.iarea = CopyImage.deepCopy(ima);
		}
		bx1 = x1;
		bx2 = x2;
		by1 = y1;
		by2 = y2;
	}
	
	/**
	 * �������� ����� ��������� ��� ��������
	 */
	public void paintDelArea(Graphics g) {
		if(flNoBackground == 1)
		{
		//�� ����� ��������� ������ ����� �������
		        Graphics2D g2 = (Graphics2D)g;
		        Color c = new Color( 255, 255, 255 );
		        g2.setColor(c);
				g2.fill(new Rectangle2D.Float(bx1, by1, (bx2-bx1), (by2-by1)));
		}
	}
	
	/**
	 * �������� ������� ��������� ��� ���������
	 */
	public void paintDelThisArea(Graphics g) {
		if(flNoBackground == 1)
		{
		//�� ����� ��������� ������ ����� �������
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
	 * ������� ���������� ������� � �����
	 */
	public void setBufer() {
		if(ch==true)
		{
			Buffer.setImage(iarea);
		}
		else Buffer.setImage(bi);
	}
	
	/*
	 * ������ �����
	 */
	public void RepaintColor(BufferedImage bi, Color colorIn, Color colorOut) {
		SubPaint.isRepaint( bi,  colorIn,  colorOut);
	}
	
	/**
	 * ������� �����������
	 */
	public void RotateImage() {
		iarea = RotateImage.rotate(iarea, 1.5708);
		//�������� �� ������ �������
		x2 = x1+iarea.getWidth()-1;
		y2 = y1+iarea.getHeight()-1;		
	}
	
	/*
	 * ��������������� �����������
	 */
	public void resize3r(int xSize, int ySize) {
		BufferedImage scaled = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = scaled.createGraphics();
        g2.drawImage(iarea, 0, 0, xSize, ySize, null);
        iarea = scaled;
	}
	
	/*
	 * ����� ����� ��������
	 */
	public void isResize40(int xDot, int yDot) {
		if(x1 < xDot)
		{
			//��������� �����
			x2 = xDot;
			//y2 = yDot;
			//������� ������
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * ����� ���� ��������
	 */
	public void isResize80(int xDot, int yDot) {
		if(x2 > xDot)
		{
			//��������� �����
			x1 = xDot;
			//������� ������
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * ����� ���� ��������
	 */
	public void isResize20(int xDot, int yDot) {
		if(y2 > yDot)
		{
			//��������� �����
			y1 = yDot;
			//������� ������
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * ����� ��� ��������
	 */
	public void isResize60(int xDot, int yDot) {
		if(y1 < yDot)
		{
			//��������� �����
			y2 = yDot;
			//������� ������
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * ����� ���� ����
	 */
	public void isResize10(int xDot, int yDot) {
		if(x2 > yDot && y2 > yDot)
		{
			//��������� �����
			x1 = xDot;
			y1 = yDot;
			//������� ������
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * ����� ��� �����
	 */
	public void isResize50(int xDot, int yDot) {
		if(y1 < yDot && x1 < xDot)
		{
			//��������� �����
			x2 = xDot;
			y2 = yDot;
			//������� ������
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * ����� ���� �����
	 */
	public void isResize30(int xDot, int yDot) {
		if(y1 + (yDot - y1) > y1 && x2 > xDot)
		{
			//��������� �����
			x1 = xDot;
			y2 = yDot;
			//������� ������
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/*
	 * ����� ��� ����
	 */
	public void isResize70(int xDot, int yDot) {
		if(x1 + (xDot - x1) > x1 && y2 > yDot)
		{
			//��������� �����
			x2 = xDot;
			y1 = yDot;
			//������� ������
			resize3r(x2 - x1, y2 - y1);
		}
	}
	
	/**
	 * ������������ ���������������
	 * @param sx width
	 * @param sy height
	 */
	public void manualResize(int sx, int sy) {
		x2 = x1 + sx;
		y2 = y1 + sy;
		resize3r(sx, sy);
	}
	
	/**
	 * ������� �� ������
	 * @param x ���������� �� ��� x
	 * @param y ���������� �� ��� y
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
