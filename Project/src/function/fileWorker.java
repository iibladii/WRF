package function;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * ����� ���������� �� �������� � ���������� �����������
 */
public class fileWorker {
	public String ReadFile(String FileName)
	{
		String str="";
		try(FileReader reader = new FileReader(FileName))
        {
           // ������ �����������
            int c;
            while((c=reader.read())!=-1){
            	
                str+=(char)c;
                
            } 
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        }   
		return str;
	}
	
	public void WriteFile(String FileName, String text)
	{
		try(FileWriter writer = new FileWriter(FileName, false))
        {
            writer.write(text);
            writer.flush();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        } 
	}
}
