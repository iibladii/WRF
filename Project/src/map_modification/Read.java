package map_modification;

//Program: Read.java
// Created by Danny Brown
// in Edmonton, Alberta, Canada
// This program reads binary 30-second WRF land use and elevation files and
// converts them to useable formats.  Use Write.java to convert them back
// into the WRF binary format.  It will not work with the lower resolution
// data.  
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.lang.Object;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import component.dataBox;
import forSerealizate.optionStore;
import function.fileWorker;
public class Read{
	public BufferedImage readData(String[] Flags, String path_, JTextPane index) throws Exception    {
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
		BufferedReader Br = new BufferedReader(new FileReader(path_+"index"));
		
		//Загрузим index file
		fileWorker fw = new fileWorker();
		index.setText(fw.ReadFile(path_+"index"));
		//Сохраним lon lat в data class
		String sss = index.getText();
		dataBox.setLocate(index.getText());
		
		String Line = Br.readLine();         
		int tile_x = 0;  
		// The width of the file        
		int tile_y = 0;  
		// The height of the file        
		double dx = 0;   
		double dy = 0;  
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
			if (Var.equals("dy"))                
				dy = Double.parseDouble(Value);
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
			int size_x = (int)(tile_x * dx + 0.5);   
			int size_y = (int)(tile_x * dy + 0.5); 
			int Lat = (int)(Double.parseDouble(Flags[1]) / 10.0) * 10;            
			int Lon = (int)(Double.parseDouble(Flags[2]) / 10.0) * 10;            
			if (Double.parseDouble(Flags[1]) < 0.0)                
				Lat -= 10;            
			if (Double.parseDouble(Flags[2]) < 0.0)                
				Lon -= 10;            
			int X = (Lon + 180) * tile_x / size_x;            
			int Y = (Lat + 90) * tile_y / size_y;            
			String X1 = "";            
			String X2 = "";            
			String Y1 = "";            
			String Y2 = "";            
			if ((X >= 1000) && (X < 10000))                
				X1 = "0" + (X + 1);            
			else 
				if ((X >= 100) && (X < 1000))                
					X1 = "00" + (X + 1);            
				else 
					if ((X >= 10) && (X < 100))                
						X1 = "000" + (X + 1);            
					else 
						if ((X >= 0) && (X < 10))                
							X1 = "0000" + (X + 1);            
						else                
							X1 = "" + (X + 1);            
			if ((X + tile_x >= 1000) && (X + tile_x < 10000))                
				X2 = "0" + (X + tile_x);            
			else 
				if ((X + tile_x >= 100) && (X + tile_x < 1000))                
					X2 = "00" + (X + tile_x);            
				else 
					if ((X + tile_x >= 10) && (X + tile_x < 100))                
						X2 = "000" + (X + tile_x);            
					else 
						if ((X + tile_x >= 0) && (X + tile_x < 10))                
							X2 = "0000" + (X + tile_x);            
						else                
							X2 = "" + (X + size_x);            
			if ((Y >= 1000) && (Y < 10000))                
				Y1 = "0" + (Y + 1);            
			else 
				if ((Y >= 100) && (Y < 1000))                
					Y1 = "00" + (Y + 1);            
				else 
					if ((Y >= 10) && (Y < 100))                
						Y1 = "000" + (Y + 1);            
					else 
						if ((Y >= 0) && (Y < 10))                
							Y1 = "0000" + (Y + 1);            
						else                
							Y1 = "" + (Y + 1);            
			if ((Y + tile_y >= 1000) && (Y + tile_y < 10000))                
				Y2 = "0" + (Y + tile_y);            
			else 
				if ((Y + tile_y >= 100) && (Y + tile_y < 1000))                
					Y2 = "00" + (Y + tile_y);            
				else 
					if ((Y + tile_y >= 10) && (Y + tile_y < 100))                
						Y2 = "000" + (Y + tile_y);            
					else 
						if ((Y + tile_y >= 0) && (Y + tile_y < 10))                
							Y2 = "0000" + (Y + tile_y);            
						else                
							Y2 = "" + (Y + tile_y);            
			// The file to be output.              
			File = (String)X1 + "-" + X2 + "." + Y1 + "-" + Y2;            
			System.out.println(File);                        
			// If no other information is specified, the program exits.            
			// Otherwise it assumes that the next information specifies the            
			// parameters for the output of the file.              
			if (Flags[3] == null)            
			{                
				System.out.println();                
				System.exit(0);            
				}            
			else                
				System.out.println("Using this file for input...");        
			}        System.out.print("Reading Data...");        
			int Jump = 0;        
			FileInputStream Is;        
			if (!File.equals(""))        
			{            
				Is = new FileInputStream(File);
				optionStore.setFile_PathName(path_, File);
				Jump = 2;        
				}        
			else        
			{            
				Is = new FileInputStream(path_+Flags[0]);//<--error
				optionStore.setFile_PathName(path_, Flags[0]);
				Jump = 0;        
			}            
			// This section reads the binary file.          
			double max = -100000000; 
			// The mim value of the file - modified later        
			double min = 150000000;  
			// The max value of the file - modified later        
			// Here is an array that stores the data.          
			double[][] Data = new double[tile_y][tile_x];        
			// Skips the first number of lines, which are the border.          
			for (int k = 0; k < ((tile_x * wordsize + tile_bdr * wordsize * 2)*tile_bdr); k ++)            
				Is.read();        // This section reads the actual data, line by line.        
			for (int k = 0; k < tile_y; k ++)        
			{            
				// Skips the first few border grid cells             
				for (int j = 0; j < tile_bdr * wordsize; j ++)                
					Is.read();            
				// Puts the rest into the array.              
				for (int j = 0; j < tile_x; j ++)            
				{                
					// Tests for the wordsize.                  
					if (wordsize == 1)                     
						Data[k][j] = Is.read(); // Reads 1 byte.                
					else if (wordsize == 2)                
					{        
						Data[k][j] = Is.read() << 8 | Is.read(); // Reads 2 bytes.
						if (Data[k][j] > 15000) // Tests for negative elevations.                        
							Data[k][j] = Data[k][j] - 65536;                    
						if (Data[k][j] > max) // Sets the max and min values.                          
							max = Data[k][j];                    
						if (Data[k][j] < min)                        
							min = Data[k][j];       
					}
				}            
				// Skips the trailing border grid cells            
				for (int j = 0; j < tile_bdr * wordsize; j ++)                
					Is.read();        
				}        
			System.out.println("Done!");         
			// This section deals with the output of TXT, PNG, ASC, and GRD.        
			// Tests to see if PNG is specified in the output.        
			i = 0;        
			for (int j = 0; j < FileTypes.length; j++)        
			{            
				if (FileTypes[j].equalsIgnoreCase("PNG"))                
					i = 1;        
			}        
			if (FileTypes.length == 0)            
				i = 1;        
			if (i != 0)        
			{            
				// Begins the PNG subroutine.            
				System.out.print("Exporting to PNG file...");            
				// Opens the file 'Colours.conf' which maps the land use data            
				// to different colours.   
				InputStream initialStream = getClass().getResourceAsStream("/resource/Colours.conf");
				byte[] buffer = new byte[initialStream.available()];
			    initialStream.read(buffer);
			    File targetFile = new File("Colours.conf");
			    OutputStream outStream = new FileOutputStream(targetFile);
			    outStream.write(buffer);
				
				Br = new BufferedReader(new FileReader("Colours.conf"));  
				
				Line = Br.readLine();            
				int j = 0;            
				while (Line != null)            
				{                
					j ++;                
					Line = Br.readLine();            
				}            
				Color[] Colours = new Color[j];  
				// An array of colours.            
				if (wordsize == 1)            
				{    
					// If the wordsize is 1, then it reads the file.                  
					Br = new BufferedReader(new FileReader("Colours.conf"));
					//Br = new BufferedReader(new FileReader(getClass().getResource("/resources/Colours.conf").getPath()));
					Line = Br.readLine();                
					Line = Br.readLine();                
					Line = Br.readLine();                
					Line = Br.readLine();                
					j = 0;                
					while (Line != null)                
					{  
						// The file is delimited by tabs, and in RGB format.                      
						StringTokenizer St = new StringTokenizer(Line, "\t");                    
						St.nextToken();                    
						Colours[j] = new Color(Integer.parseInt(St.nextToken()), Integer.parseInt(St.nextToken()), Integer.parseInt(St.nextToken()), Integer.parseInt(St.nextToken()));                         
						Line = Br.readLine();                    
						j ++;                
					}            
				}            
				if (wordsize == 2)            
				{   
					// If the wordsize is 2, then it outputs some data to a file.                  
					PrintStream Ps = new PrintStream(new FileOutputStream(path_+Flags[(1 + Jump)] + ".ini"));                
					Ps.println("Name: " + Flags[1]);                
					Ps.println("Min: " + min);                
					Ps.println("Max: " + max);            
				}            
				// Create a new BufferedImage to store the data.            
				BufferedImage Image = new BufferedImage(tile_x, tile_y, 1);            
				int k = 0;            
				j = 0;         
				// Sets the data if the wordsize is 1.              
				if (wordsize == 1)            
				{                
					for (k = 0; k < tile_y; k ++)                
					{ 
						
						// References the Colours array for the different land use types                    
						for (j = 0; j < tile_x; j++) {
							Image.setRGB(j, tile_y - 1 - k, Colours[(int)Data[k][j]].getRGB());    
							
						}
						}            
				}
				// Sets the data if the wordsize is 2.              
				else 
					if (wordsize == 2)            
					{                
						for (k = 0; k < tile_y; k ++)                
						{  
							// Sets the colours to depend on the min and max values.                      
							for (j = 0; j < tile_x; j ++)                        
								Image.setRGB(j, tile_y - 1 - k, new Color((int)((Data[k][j] - min) / (max - min) * 255), (int)((Data[k][j] - min) / (max - min) * 255), (int)((Data[k][j] - min) / (max - min) * 255)).getRGB());                
						}            
					}        
				// При необходимости можно сохранить в виде файла           
				//ImageIO.write(Image, "PNG", new File(path_+Flags[(1 + Jump)] + ".png"));            
				System.out.println("Done!");
				return Image;        
				}
			return null;
	}
}