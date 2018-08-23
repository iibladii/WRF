package function;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Vector;

import type.coordinateXY;

public class RepaintPr {
	private BufferedImage imag;//Тут храним копию изображения выделенной области
	
	public static BufferedImage deepCopy(BufferedImage bi) {
	    ColorModel cm = bi.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	//Разбавим 50x50
	public void zalivaem50x50(BufferedImage bi, coordinateXY xy, Color co, Color cn) {
		imag = deepCopy(bi);
		
		Graphics g = imag.getGraphics();
        Graphics2D g2 = (Graphics2D)g;
        Graphics g_ = bi.getGraphics();
        Graphics2D g2_ = (Graphics2D)g_;
        
		if(!co.equals(cn)) {
		g2.setColor(cn);
		g2_.setColor(cn);
		Vector v = new Vector();
		v.add(xy);
		while(v.size()!=0) {
        	if(((coordinateXY)v.get(0)).getX()<imag.getWidth()-1 && ((coordinateXY)v.get(0)).getX()>0 && ((coordinateXY)v.get(0)).getY()<imag.getHeight()-1 && ((coordinateXY)v.get(0)).getY()>0 && (new Color(imag.getRGB(((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY())).equals(co))) {
        		if((((coordinateXY)v.get(0)).getY()) % 2 == 0 )
        			g2_.drawLine(((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY(), ((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY());
        		g2.drawLine(((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY(), ((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY());
        		if(!v.contains(new coordinateXY(((coordinateXY)v.get(0)).getX()+1,((coordinateXY)v.get(0)).getY())))//Проверка (можно убрать)
        			v.add(new coordinateXY(((coordinateXY)v.get(0)).getX()+1,((coordinateXY)v.get(0)).getY()));
        	}
        	if(((coordinateXY)v.get(0)).getX()<imag.getWidth()-1 && ((coordinateXY)v.get(0)).getX()>0 && ((coordinateXY)v.get(0)).getY()<imag.getHeight()-1 && ((coordinateXY)v.get(0)).getY()>0 && (new Color(imag.getRGB(((coordinateXY)v.get(0)).getX()-1, ((coordinateXY)v.get(0)).getY())).equals(co))) {
        		if((((coordinateXY)v.get(0)).getY()) % 2 == 0 )
        			g2_.drawLine(((coordinateXY)v.get(0)).getX()-1, ((coordinateXY)v.get(0)).getY(), ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY());
        		g2.drawLine(((coordinateXY)v.get(0)).getX()-1, ((coordinateXY)v.get(0)).getY(), ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY());
        		if(!v.contains(new coordinateXY(((coordinateXY)v.get(0)).getX()-1,((coordinateXY)v.get(0)).getY())))
        				v.add(new coordinateXY(((coordinateXY)v.get(0)).getX()-1,((coordinateXY)v.get(0)).getY()));
        	}
        	if(((coordinateXY)v.get(0)).getX()<imag.getWidth()-1 && ((coordinateXY)v.get(0)).getX()>0 && ((coordinateXY)v.get(0)).getY()<imag.getHeight()-1 && ((coordinateXY)v.get(0)).getY()>0 && (new Color(imag.getRGB(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1)).equals(co))) {
        		if((((coordinateXY)v.get(0)).getY() + 1) % 2 == 0 )
        			g2_.drawLine(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1, ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1);
        		g2.drawLine(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1, ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1);
        		if(!v.contains(new coordinateXY(((coordinateXY)v.get(0)).getX(),((coordinateXY)v.get(0)).getY()+1)))
        				v.add(new coordinateXY(((coordinateXY)v.get(0)).getX(),((coordinateXY)v.get(0)).getY()+1));
        	}
        	if(((coordinateXY)v.get(0)).getX()<imag.getWidth()-1 && ((coordinateXY)v.get(0)).getX()>0 && ((coordinateXY)v.get(0)).getY()<imag.getHeight()-1 && ((coordinateXY)v.get(0)).getY()>0 && (new Color(imag.getRGB(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1)).equals(co))) {
        		if((((coordinateXY)v.get(0)).getY() - 1) % 2 == 0 )
        			g2_.drawLine(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1, ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1);
        		g2.drawLine(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1, ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1);
        		if(!v.contains(new coordinateXY(((coordinateXY)v.get(0)).getX()+1,((coordinateXY)v.get(0)).getY()-1)))
        				v.add(new coordinateXY(((coordinateXY)v.get(0)).getX(),((coordinateXY)v.get(0)).getY()-1));
        	}
        	v.remove(0);
		}
		}
	}
	
	//Разбавим 25x75
	public void zalivaem25x75(BufferedImage bi, coordinateXY xy, Color co, Color cn) {
		imag = deepCopy(bi);
		
		Graphics g = imag.getGraphics();
        Graphics2D g2 = (Graphics2D)g;
        Graphics g_ = bi.getGraphics();
        Graphics2D g2_ = (Graphics2D)g_;
        
		if(!co.equals(cn)) {
		g2.setColor(cn);
		g2_.setColor(cn);
		Vector v = new Vector();
		v.add(xy);
		while(v.size()!=0) {
        	if(((coordinateXY)v.get(0)).getX()<imag.getWidth()-1 && ((coordinateXY)v.get(0)).getX()>0 && ((coordinateXY)v.get(0)).getY()<imag.getHeight()-1 && ((coordinateXY)v.get(0)).getY()>0 && (new Color(imag.getRGB(((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY())).equals(co))) {
        		if((((coordinateXY)v.get(0)).getY()) % 2 == 0 && (((coordinateXY)v.get(0)).getX()+1) % 2 == 0)
        			g2_.drawLine(((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY(), ((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY());
        		g2.drawLine(((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY(), ((coordinateXY)v.get(0)).getX()+1, ((coordinateXY)v.get(0)).getY());
        		if(!v.contains(new coordinateXY(((coordinateXY)v.get(0)).getX()+1,((coordinateXY)v.get(0)).getY())))//Проверка (можно убрать)
        			v.add(new coordinateXY(((coordinateXY)v.get(0)).getX()+1,((coordinateXY)v.get(0)).getY()));
        	}
        	if(((coordinateXY)v.get(0)).getX()<imag.getWidth()-1 && ((coordinateXY)v.get(0)).getX()>0 && ((coordinateXY)v.get(0)).getY()<imag.getHeight()-1 && ((coordinateXY)v.get(0)).getY()>0 && (new Color(imag.getRGB(((coordinateXY)v.get(0)).getX()-1, ((coordinateXY)v.get(0)).getY())).equals(co))) {
        		if((((coordinateXY)v.get(0)).getY()) % 2 == 0  && (((coordinateXY)v.get(0)).getX()-1) % 2 == 0)
        			g2_.drawLine(((coordinateXY)v.get(0)).getX()-1, ((coordinateXY)v.get(0)).getY(), ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY());
        		g2.drawLine(((coordinateXY)v.get(0)).getX()-1, ((coordinateXY)v.get(0)).getY(), ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY());
        		if(!v.contains(new coordinateXY(((coordinateXY)v.get(0)).getX()-1,((coordinateXY)v.get(0)).getY())))
        			v.add(new coordinateXY(((coordinateXY)v.get(0)).getX()-1,((coordinateXY)v.get(0)).getY()));
        	}
        	if(((coordinateXY)v.get(0)).getX()<imag.getWidth()-1 && ((coordinateXY)v.get(0)).getX()>0 && ((coordinateXY)v.get(0)).getY()<imag.getHeight()-1 && ((coordinateXY)v.get(0)).getY()>0 && (new Color(imag.getRGB(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1)).equals(co))) {
        		if((((coordinateXY)v.get(0)).getY() + 1) % 2 == 0  && (((coordinateXY)v.get(0)).getX()) % 2 == 0)
        			g2_.drawLine(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1, ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1);
        		g2.drawLine(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1, ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()+1);
        		if(!v.contains(new coordinateXY(((coordinateXY)v.get(0)).getX(),((coordinateXY)v.get(0)).getY()+1)))
        			v.add(new coordinateXY(((coordinateXY)v.get(0)).getX(),((coordinateXY)v.get(0)).getY()+1));
        	}
        	if(((coordinateXY)v.get(0)).getX()<imag.getWidth()-1 && ((coordinateXY)v.get(0)).getX()>0 && ((coordinateXY)v.get(0)).getY()<imag.getHeight()-1 && ((coordinateXY)v.get(0)).getY()>0 && (new Color(imag.getRGB(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1)).equals(co))) {
        		if((((coordinateXY)v.get(0)).getY() - 1) % 2 == 0  && (((coordinateXY)v.get(0)).getX()) % 2 == 0)
        			g2_.drawLine(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1, ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1);
        		g2.drawLine(((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1, ((coordinateXY)v.get(0)).getX(), ((coordinateXY)v.get(0)).getY()-1);
        		if(!v.contains(new coordinateXY(((coordinateXY)v.get(0)).getX()+1,((coordinateXY)v.get(0)).getY()-1)))
        			v.add(new coordinateXY(((coordinateXY)v.get(0)).getX(),((coordinateXY)v.get(0)).getY()-1));
        	}
        	v.remove(0);
		}
		}
	}
}
