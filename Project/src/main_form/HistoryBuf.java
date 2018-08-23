package main_form;

import java.awt.image.BufferedImage;
import java.util.Vector;

import function.CopyImage;

/**
 * �������
 *
 */
public class HistoryBuf {
	private static int max_cells = 10;
	private static int Current_cells = 0;
	private static Vector vec = new Vector();

/**
 * ������ �������� ���������
 * @param bi �����������
 */
public static void add(BufferedImage bi) {
	if(vec.size() < max_cells)
	{
		BufferedImage buf = CopyImage.deepCopy(bi);
		vec.addElement(buf);
		//Current_cells ++;
		Current_cells = vec.size()-1;
	}
	else
	{
		vec.remove(0);
		BufferedImage buf = CopyImage.deepCopy(bi);
		vec.addElement(buf);
	}
}

/**
 * �������� ����� �� 1 ������
 * @return �����������
 */
public static BufferedImage left() {
	if(Current_cells > 0)
		Current_cells --;
	return (BufferedImage) vec.get(Current_cells);
}

/**
 * �������� ������ �� 1 ������
 * @return �����������
 */
public static BufferedImage right() {
	if(Current_cells < vec.size()-1)
		Current_cells ++;	
	return (BufferedImage) vec.get(Current_cells);
}
}
