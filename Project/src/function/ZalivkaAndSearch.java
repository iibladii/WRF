package function;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import type.coordinateXY;
import type.mas4;

public class ZalivkaAndSearch {
	/*
	 * Graphics2D - area
	 * coordinateXY - coordinate x, y
	 * Color - old color
	 * Color - new color
	 */
	
	/*
	//Рекурсивная заливка !!!!!!!В случае большой фигуры переполнение стека
	public void zalivaem(BufferedImage imag, Graphics2D g2, coordinateXY xy, Color co, Color cn) {
		if(!co.equals(cn)) {
		g2.setColor(cn);
        	if(new Color(imag.getRGB(((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY())).equals(co)) {
        		coordinateXY xy1 = new coordinateXY(((coordinateXY)v.get(0)).getX()+1,((coordinateXY)v.get(0)).getY());
        		g2.drawLine(((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY(), ((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY());
        		zalivaem(imag, g2, xy1, co, cn);
        	}
        	if(new Color(imag.getRGB(((coordinateXY)v.get(0)).getX()-1, ((coordinateXY)v.get(0)).getY())).equals(co)) {
        		coordinateXY xy1 = new coordinateXY(((coordinateXY)v.get(0)).getX()-1,((coordinateXY)v.get(0)).getY());
        		g2.drawLine(((coordinateXY)v.get(0)).getX()-1, ((coordinateXY)v.get(0)).getY(), ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY());
        		zalivaem(imag, g2, xy1, co, cn);
        	}
        	if(new Color(imag.getRGB(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1)).equals(co)) {
        		coordinateXY xy1 = new coordinateXY(((coordinateXY)v.get(0)).getX(),((coordinateXY)v.get(0)).getY()+1);
        		g2.drawLine(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1, ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1);
        		zalivaem(imag, g2, xy1, co, cn);
        	}
        	if(new Color(imag.getRGB(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1)).equals(co)) {
        		coordinateXY xy1 = new coordinateXY(((coordinateXY)v.get(0)).getX(),((coordinateXY)v.get(0)).getY()-1);
        		g2.drawLine(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1, ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1);
        		zalivaem(imag, g2, xy1, co, cn);
        	}
		}
	}
	*/
	
	//С использованием вектора
	public mas4 zalivaem(BufferedImage imag, Graphics2D g2, coordinateXY xy, Color co, Color cn) {
		Integer xma, xmi, yma, ymi;
		xma = 0;
		xmi = imag.getWidth();
		yma = 0;
		ymi = imag.getHeight();
		if(!co.equals(cn)) {
		g2.setColor(new Color(0 ,0, 150));
		Vector v = new Vector();
		v.add(xy);
		while(v.size()>0) {
        	if(((coordinateXY)v.get(0)).getX()<imag.getWidth()-1 && ((coordinateXY)v.get(0)).getX()>0 && ((coordinateXY)v.get(0)).getY()<imag.getHeight()-1 && ((coordinateXY)v.get(0)).getY()>0 && (new Color(imag.getRGB(((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY())).equals(co))) {
        		g2.drawLine(((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY(), ((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY());
        		if((((coordinateXY)v.get(0)).getX() + 1) >= xma)
        			xma = ((coordinateXY)v.get(0)).getX() + 1;
        		if(!v.contains(new coordinateXY(((coordinateXY)v.get(0)).getX()+1,((coordinateXY)v.get(0)).getY())))//Проверка (можно убрать)
        		{
        			v.add(new coordinateXY(((coordinateXY)v.get(0)).getX()+1,((coordinateXY)v.get(0)).getY()));
        		}
        	}
        	if(((coordinateXY)v.get(0)).getX()<imag.getWidth()-1 && ((coordinateXY)v.get(0)).getX()>0 && ((coordinateXY)v.get(0)).getY()<imag.getHeight()-1 && ((coordinateXY)v.get(0)).getY()>0 && (new Color(imag.getRGB(((coordinateXY)v.get(0)).getX()-1, ((coordinateXY)v.get(0)).getY())).equals(co))) {
        		g2.drawLine(((coordinateXY)v.get(0)).getX()-1, ((coordinateXY)v.get(0)).getY(), ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY());
        		if((((coordinateXY)v.get(0)).getX() - 1) <= xmi)
        			xmi = ((coordinateXY)v.get(0)).getX() - 1;
        		if(!v.contains(new coordinateXY(((coordinateXY)v.get(0)).getX()-1,((coordinateXY)v.get(0)).getY())))
        		{
        			v.add(new coordinateXY(((coordinateXY)v.get(0)).getX()-1,((coordinateXY)v.get(0)).getY()));
        		}
        	}
        	if(((coordinateXY)v.get(0)).getX()<imag.getWidth()-1 && ((coordinateXY)v.get(0)).getX()>0 && ((coordinateXY)v.get(0)).getY()<imag.getHeight()-1 && ((coordinateXY)v.get(0)).getY()>0 && (new Color(imag.getRGB(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1)).equals(co))) {
        		g2.drawLine(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1, ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1);
        		if((((coordinateXY)v.get(0)).getY() + 1) >= yma)
        			yma = ((coordinateXY)v.get(0)).getY() + 1;
        		if(!v.contains(new coordinateXY(((coordinateXY)v.get(0)).getX(),((coordinateXY)v.get(0)).getY()+1)))
        		{
        			v.add(new coordinateXY(((coordinateXY)v.get(0)).getX(),((coordinateXY)v.get(0)).getY()+1));
        		}
        	}
        	if(((coordinateXY)v.get(0)).getX()<imag.getWidth()-1 && ((coordinateXY)v.get(0)).getX()>0 && ((coordinateXY)v.get(0)).getY()<imag.getHeight()-1 && ((coordinateXY)v.get(0)).getY()>0 && (new Color(imag.getRGB(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1)).equals(co))) {
        		g2.drawLine(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1, ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1);
        		if((((coordinateXY)v.get(0)).getY() - 1) <= ymi)
        			ymi = ((coordinateXY)v.get(0)).getY() - 1;
        		if(!v.contains(new coordinateXY(((coordinateXY)v.get(0)).getX()+1,((coordinateXY)v.get(0)).getY()-1)))
        		{
        			v.add(new coordinateXY(((coordinateXY)v.get(0)).getX(),((coordinateXY)v.get(0)).getY()-1));
        		}
        	}
        	v.remove(0);
		}
		}
		return new mas4(xmi, ymi, xma, yma);
	}
}