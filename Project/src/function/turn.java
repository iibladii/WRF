package function;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/*
 * ��������:
 * 1. �������� ����������� �� buf1 � buf2
 * 2. ����������� �������� �� buf2 � buf1 � ������ ��������
 */
public class turn {
	BufferedImage buf2;//����� �����������
	
	public static BufferedImage deepCopy(BufferedImage bi) {
	    ColorModel cm = bi.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	public void isTurn(BufferedImage buf1, int grad) {
		//��������
		buf2 = deepCopy(buf1);
		
		//������������
		/*
		Graphics g = buf1.getGraphics();
        Graphics2D g2 = (Graphics2D)g;
		for(int x = 0; x < buf1.getWidth(); x++)
			for(int y = 0; y < buf1.getHeight(); y++)
			{
				Color c = new Color(buf2.getRGB(x, y));//<-- color
				g2.setColor(c);
				g2.drawLine(x, y, x, y);
			}
		*/
	}
	
}
