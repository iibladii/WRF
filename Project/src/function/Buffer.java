package function;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
/**
 * ����� ���������� �� �������������� � ��������� �������
 */
public abstract class Buffer implements ClipboardOwner{
	
	public static BufferedImage deepCopy(BufferedImage bi) {
	    ColorModel cm = bi.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	/**
	 * �������� ������ � ��������� �����
	 * @param str ������������ ������
	 */
	public static void setStr(String str) {
	    StringSelection ss = new StringSelection(str);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}
	
	/**
	 * ��������� ������ �� ���������� ������
	 * @return ������ �� ���������� ������
	 */
	public String getClipboardContents() {
	    String result = "";
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    //odd: the Object param of getContents is not currently used
	    Transferable contents = clipboard.getContents(null);
	    boolean hasTransferableText =
	      (contents != null) &&
	      contents.isDataFlavorSupported(DataFlavor.stringFlavor)
	    ;
	    if (hasTransferableText) {
	      try {
	        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
	      }
	      catch (UnsupportedFlavorException | IOException ex){
	        System.out.println(ex);
	        ex.printStackTrace();
	      }
	    }
	    return result;
	  }
	
	/**
	 * �������� ����������� � ��������� �����
	 * @param bi ������������ �����������
	 */
	public static void setImage(BufferedImage bi) {
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new ImageTransferable(bi), null);
	}
	
	/**
	 * ��������� ����������� �� ���������� ������
	 * @return ������ �� ���������� ������
	 */
	public static BufferedImage getImage() {
		BufferedImage result = null;
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    //odd: the Object param of getContents is not currently used
	    Transferable contents = clipboard.getContents(null);
	    boolean hasTransferableText =
	      (contents != null) &&
	      contents.isDataFlavorSupported(DataFlavor.imageFlavor);
	    if (hasTransferableText) {
	      try {
	        result = (BufferedImage)contents.getTransferData(DataFlavor.imageFlavor);
	      }
	      catch (UnsupportedFlavorException | IOException ex){
	        System.out.println(ex);
	        ex.printStackTrace();
	      }
	    }
	    return result;
	  }
	
	/**
	 * ����� ���������� �� �������������� ������ � ������������� ����
	 *
	 */
	static final class ImageTransferable implements Transferable {
	    final BufferedImage image;

	    public ImageTransferable(final BufferedImage image) {
	        this.image = image;
	    }

	    public DataFlavor[] getTransferDataFlavors() {
	        return new DataFlavor[] {DataFlavor.imageFlavor};
	    }

	    public boolean isDataFlavorSupported(final DataFlavor flavor) {
	        return DataFlavor.imageFlavor.equals(flavor);
	    }

	    public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException, IOException {
	        if (isDataFlavorSupported(flavor)) {
	            return image;
	        }

	        throw new UnsupportedFlavorException(flavor);
	    }
	};
	
	
}
