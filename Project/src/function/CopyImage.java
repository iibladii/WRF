package function;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * Класс копирования картинки (присваивание не копирует!!!)
 */
public class CopyImage {
	
	/**
	 * Производит клонирование в новую переменную
	 * @param bi копируемое изображение
	 * @return скопированное изображение
	 */
	public static BufferedImage deepCopy(BufferedImage bi) {
	    ColorModel cm = bi.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}