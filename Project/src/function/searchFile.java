package function;

import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import type.str2;

public class searchFile {
	private static Boolean flag = false;
	private static String ret = "";
	/**
	 * ������� ���������� ������
	 * @return ���� �� �������� � ������� ��������� ����
	 */
	public static String getResult()
	{
		return ret;
	}
	
	/**
	 * ������� ����������� ����� �����
	 * @param path ���� � ��������
	 * @param find ��� �����
	 */
	public static void newPoisk(String path, String find)
	{
		flag = false;
		func(path, find);
	}
	
	/**
	 * ����� ������ ����� � ��������
	 * @param path ���� � ��������
	 * @param find ��� �����
	 * @return 
	 */
	public static void func(String path, String find) {
        File f = new File(path);
        String[] list = f.list();//������ ������ � ������� �����
        ret = "";
        if (list == null) {//���� ����� �������� �����
        	   return;
        	}
        for (String file : list) {//�������� �� ����������
            if (find.equals(file)) {
                flag=true;
                ret = path;
                return;
            }
            if (!path.endsWith("\\")) {
                path += "\\";
            }
            File tempfile = new File(path + file);
            System.out.println(path + file);
            if (!file.equals(".") && !file.equals("..")) {
                if (tempfile.isDirectory()) {//����� ���������, ���� ��� �����
                    func(path + file, find);//�� ����������� ����� ���� �������
                    if(flag) return;
                }
            }
        }
	return;
        
    }

	
	public static String fname="";
	
	/**
	 * ����� ������ ����� � ��������
	 * @param path ���� � ��������
	 * @param find ��� �����
	 * @return 
	 */
	public static void func2(String path, str2 find, String st) {
        File f = new File(path);
        String[] list = f.list();//������ ������ � ������� �����
        ret = "";
        if (list == null) {//���� ����� �������� �����
        	   return;
        	}
        for (String file : list) {//�������� �� ����������
        	if(file.indexOf(".")!=-1 && file.indexOf("-")!=-1 && path.indexOf(st)!=-1)
        	{
        		String X1 = file.substring(0,file.indexOf("-"));
        		String X2 = file.substring(file.indexOf(".")+1,file.lastIndexOf("-"));
        		String Y1 = file.substring(file.indexOf("-")+1,file.indexOf("."));
        		String Y2 = file.substring(file.lastIndexOf("-")+1,file.length());
            	if (Integer.parseInt(X1)<=Integer.parseInt(find.get_s1()) && Integer.parseInt(Y1)>=Integer.parseInt(find.get_s1()) && Integer.parseInt(X2)<=Integer.parseInt(find.get_s2()) && Integer.parseInt(Y2)>=Integer.parseInt(find.get_s2())) {
            		fname = file;
            		System.out.println(path +"\\"+ fname);
            		flag=true;
            		ret = path;
            		return;
            	}
        	}
            if (!path.endsWith("\\")) {
                path += "\\";
            }
            File tempfile = new File(path + file);
            System.out.println(path + file);
            if (!file.equals(".") && !file.equals("..")) {
                if (tempfile.isDirectory()) {//����� ���������, ���� ��� �����
                    func2(path + file, find, st);//�� ����������� ����� ���� �������
                    if(flag) return;
                }
            }
        }
	return;
        
    }
	
	public static String newPoisk(String path, str2 find) {
		flag = false;
		func2(path, find, "");
		return path;
	}

	public static String newPoisk(String path, str2 find, String st) {
		flag = false;
		func2(path, find, st);
		return path;
	}

}
