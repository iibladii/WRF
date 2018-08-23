package function;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/*
 * Класс отвечающий за перекраску
 */
public class SubPaint {

	public static BufferedImage deepCopy(BufferedImage bi) {
	    ColorModel cm = bi.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	/*
	 * Заменим один цвет на изображении на другой
	 * BufferedImage - изображение
	 * colorIn - Перекрашиваемый цвет
	 * colorOut - цвет в который надо перекрасить
	 * 
	 */
	public static void isRepaint(BufferedImage bi, Color colorIn, Color colorOut) {
		Graphics g = bi.getGraphics();
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(colorOut);
        for(int i=0; i<bi.getWidth(); i++)
        	for(int j=0; j<bi.getHeight(); j++)
        	{
        		if(colorIn.getRGB() ==  bi.getRGB(i, j))
        			g2.drawLine(i, j, i, j);
        	}	
	}

}
