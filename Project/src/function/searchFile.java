package function;

import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import type.str2;

public class searchFile {
	private static Boolean flag = false;
	private static String ret = "";
	/**
	 * Получим результаты поиска
	 * @return путь до каталога в котором находится файл
	 */
	public static String getResult()
	{
		return ret;
	}
	
	/**
	 * Функция запускающая новый поиск
	 * @param path путь к каталогу
	 * @param find имя файла
	 */
	public static void newPoisk(String path, String find)
	{
		flag = false;
		func(path, find);
	}
	
	/**
	 * Метод поиска файла в каталоге
	 * @param path путь к каталогу
	 * @param find имя файла
	 * @return 
	 */
	public static void func(String path, String find) {
        File f = new File(path);
        String[] list = f.list();//список файлов в текущей папке
        ret = "";
        if (list == null) {//Если пусто закончим поиск
        	   return;
        	}
        for (String file : list) {//проверка на совпадение
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
                if (tempfile.isDirectory()) {//иначе проверяем, если это папка
                    func(path + file, find);//то рекурсивный вызов этой функции
                    if(flag) return;
                }
            }
        }
	return;
        
    }

	
	public static String fname="";
	
	/**
	 * Метод поиска файла в каталоге
	 * @param path путь к каталогу
	 * @param find имя файла
	 * @return 
	 */
	public static void func2(String path, str2 find, String st) {
        File f = new File(path);
        String[] list = f.list();//список файлов в текущей папке
        ret = "";
        if (list == null) {//Если пусто закончим поиск
        	   return;
        	}
        for (String file : list) {//проверка на совпадение
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
                if (tempfile.isDirectory()) {//иначе проверяем, если это папка
                    func2(path + file, find, st);//то рекурсивный вызов этой функции
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
