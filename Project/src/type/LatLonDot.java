package type;

import java.io.BufferedReader;
import java.io.FileReader;

import function.fileWorker;

public class LatLonDot {
private int dx1;//Сдвиг в текущем файле
private int dx2;//Сдвиг в последующем файле
private int dy1;//Сдвиг в текущем файле
private int dy2;//Сдвиг в последующем файле
private int x;//Точка в текущем файле
private int y;//Точка в текущем файле
private double nx12;
private double ny12;
private double nx21;
private double ny21;
private double nx22;
private double ny22;

public void setLatLonDot(int dx1, int dx2, int dy1, int dy2, int x, int y) {
	this.dx1=dx1;
	this.dx2=dx2;
	this.dy1=dy1;
	this.dy2=dy2;
	this.x=x;
	this.y=y;
}

public double getnx12() {
	return nx12;
}

public double getny12() {
	return ny12;
}

public double getnx21() {
	return nx21;
}

public double getny21() {
	return ny21;
}

public double getnx22() {
	return nx22;
}

public double getny22() {
	return ny22;
}
/**
 * Получим точки относительно которых вычисляются границы нового ландшафта
 * @param Flags
 * @param path_
 * @return
 * @throws Exception
 */
public void GetDot(String[] Flags, String path_) throws Exception  
{
	// Counts the number of flags        
			int i = 0;        
			//System.out.println(Flags.length);
			for (int j = 1; j < Flags.length; j++)        
			{            
				if ((!Flags[j].equalsIgnoreCase("ASC")) && (!Flags[j].equalsIgnoreCase("GRD")) && (!Flags[j].equalsIgnoreCase("PNG")) && (!Flags[j].equalsIgnoreCase("TXT")))
					continue;            
				i++;        
			}             
			// Fills an array with the number of flags.          
			String[] FileTypes = new String[i];        
			i = 0;        
			for (int j = 1; j < Flags.length; j++)        
			{            
				if ((!Flags[j].equalsIgnoreCase("ASC")) && (!Flags[j].equalsIgnoreCase("GRD")) && (!Flags[j].equalsIgnoreCase("PNG")) && (!Flags[j].equalsIgnoreCase("TXT")))                
					continue;            
				FileTypes[i] = Flags[j];            
				i++;        
			}         
			// Opens the file 'index' for reading.  This file is REQUIRED, as it        
			// contains important information about the binary data.          
			BufferedReader Br = new BufferedReader(new FileReader(path_));
			
			//Загрузим index file
			fileWorker fw = new fileWorker();
			//index.setText(fw.ReadFile(path_+"index"));
			//Сохраним lon lat в data class
			//dataBox.setLocate(index.getText());
			
			String Line = Br.readLine();         
			int tile_x = 0;  
			// The width of the file        
			int tile_y = 0;  
			// The height of the file        
			double dx = 0;   
			// The resolution (in decimal degrees)        
			int tile_bdr = 0;  
			// The border around the edge of the file (ignored)        
			int wordsize = 1;  
			// The size of each piece of data (in bytes)        
			double scale_factor = 0.0;  
			// The scale factor         
			// Loops through the index file and sets the previous variables.          
			while (Line != null && Line.indexOf("=")!=-1)        
			{            
				String Var = Line.substring(0, Line.indexOf("=")).trim().toLowerCase();            
				String Value = Line.substring(Line.indexOf("=") + 1).trim().toLowerCase();            
				if (Var.equals("tile_x"))                
					tile_x = Integer.parseInt(Value);            
				if (Var.equals("tile_y"))                
					tile_y = Integer.parseInt(Value);            
				if (Var.equals("dx"))                
					dx = Double.parseDouble(Value);            
				if (Var.equals("tile_bdr"))                
					tile_bdr = Integer.parseInt(Value);            
				if (Var.equals("wordsize"))                
					wordsize = Integer.parseInt(Value);            
				if (Var.equals("scale_factor"))                
					scale_factor = Double.parseDouble(Value);            
				Line = Br.readLine();        
			}         
			// Tests for the '--locate' flag and prints out file that matches the         
			// input latitude and longitude.  This method calculates the filename        
			// based on the latitude and longitude.  
	String File = "";        
	if (Flags[0].equalsIgnoreCase("--locate"))        
	{            
		System.out.print("The following file matches your location: ");            
		scale_factor = dx;            
		int size = (int)(tile_x * scale_factor + 0.5);            
		//int Lat = (int)(Double.parseDouble(Flags[1]) / 10.0) * 10;            
		//int Lon = (int)(Double.parseDouble(Flags[2]) / 10.0) * 10;
		/////////////////////////////////////////////////////////////////////////////
		int Lat = (int)Double.parseDouble(Flags[1]);            
		int Lon = (int)Double.parseDouble(Flags[2]);  
		if (Double.parseDouble(Flags[1]) < 0.0)                
			Lat -= 10;            
		if (Double.parseDouble(Flags[2]) < 0.0)                
			Lon -= 10;            
		int X = (Lon + 180) * tile_x / size;            
		int Y = (Lat + 90) * tile_y / size;  
		
		this.x = X;
		this.y = Y;
		/////////////////////////////////////////////////////////////////////////////
		Lat = (int)(Double.parseDouble(Flags[1]) / 10.0) * 10;            
		Lon = (int)(Double.parseDouble(Flags[2]) / 10.0) * 10;
		if (Double.parseDouble(Flags[1]) < 0.0)                
			Lat -= 10;            
		if (Double.parseDouble(Flags[2]) < 0.0)                
			Lon -= 10;
		
		int X1 = (Lon + 180) * tile_x / size;            
		int Y1 = (Lat + 90) * tile_y / size;  
		this.dx1 = X - X1;
		this.dy1 = Y - Y1;
		this.dx2 = tile_x - this.dx1;
		this.dy2 = tile_y - this.dy2;
		if(dy1>0) {
			this.nx12 = X1;
			this.ny12 = Y1 + size;
		}
		else {
			this.nx12 = -1;
			this.ny12 = -1;
		}
		if(dy1>0 && dx1>0) {
			this.nx22 = X1 + size;
			this.ny22 = Y1 + size;
		}
		else {
			this.nx22 = -1;
			this.ny22 = -1;
		}
		if(dx1>0) {
			this.nx21 = X1 + size;
			this.ny21 = Y1;
		}
		else {
			this.nx21 = -1;
			this.ny21 = -1;
		}
		/////////////////////////////////////////////////////////////////////////////
	}
}

}
