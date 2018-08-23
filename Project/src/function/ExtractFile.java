package function;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Распаковка jar архива
 * @author megroup9gmail.com
 *
 */
public class ExtractFile {
/**
 * Распаковывает файлы из jar архива
 * @param in файл в jar архиве
 * @param out путь до распакованого файла
 */
public void extract(String in, String out) {
	InputStream initialStream = getClass().getResourceAsStream(in);
	byte[] buffer = null;
	try {
		buffer = new byte[initialStream.available()];
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
		initialStream.read(buffer);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}  
    OutputStream outStream = null;
	try {
		outStream = new FileOutputStream(new File(out));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
		outStream.write(buffer,0, buffer.length);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
		outStream.close();
	} catch (IOException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
}
}
