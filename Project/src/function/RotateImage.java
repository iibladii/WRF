package function;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/*
 * Функции поворота изображения
 */
public class RotateImage {
	//private static BufferedImage bi;//Промежуточная область для копирования
	public static BufferedImage Rotate(BufferedImage imag, BufferedImage bi) {
		Graphics g = imag.getGraphics();
        Graphics2D g2 = (Graphics2D)g;
        Graphics g_ = bi.getGraphics();
        Graphics2D g2_ = (Graphics2D)g_;
        
		for(int i=0; i<imag.getWidth(); i++)
			for(int j=0; j<imag.getHeight(); j++)
			{
				Color c = new Color(imag.getRGB(i, j));
                g2_.setColor(c);
                g2_.setStroke(new  BasicStroke(1.0f));
                g2_.drawLine(imag.getWidth() - j, i, imag.getWidth() - j,  i);
			}
		
		//imag= CopyImage.deepCopy(bi);
		return  bi;
	}

	//Поворот афинным преобразованием без поправки на размер
	public static BufferedImage rotateImag (BufferedImage imag, double n) {//n в радианах
        double rotationRequired = Math.toRadians (n);
        double locationX = imag.getWidth() / 2;
        double locationY = imag.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);         
        BufferedImage newImage =new BufferedImage(imag.getHeight()*2, imag.getHeight()*2, imag.getType());
        op.filter(imag, newImage);
           //this.img = newImage;
        return(newImage);
     }
	
	//Поворот с поправкой
	public static BufferedImage rotate(BufferedImage image, double n) {//n в радианах
	    double sin = Math.abs(Math.sin(n)), cos = Math.abs(Math.cos(n));
	    int w = image.getWidth(), h = image.getHeight();
	    int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
	    GraphicsConfiguration gc = getDefaultConfiguration();
	    BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
	    Graphics2D g = result.createGraphics();
	    g.translate((neww - w) / 2, (newh - h) / 2);
	    g.rotate(n, w / 2, h / 2);
	    g.drawRenderedImage(image, null);
	    g.dispose();
	    return result;
	}

	private static GraphicsConfiguration getDefaultConfiguration() {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    return gd.getDefaultConfiguration();
	}
	
}
