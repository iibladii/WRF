package function;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JTextPane;

import component.dataBox;
import type.str2;

public class searchFileName {
	public static str2 fName(String[] Flags, String path_) throws Exception  
	{
		str2 st = null;
		// Counts the number of flags        
				int i = 0;        
				//System.out.println(Flags.length);
				for (int j = 1; j < Flags.length; j++)        
				{            
					if ((!Flags[j].equalsIgnoreCase("ASC")) && (!Flags[j].equalsIgnoreCase("GRD")) && (!Flags[j].equalsIgnoreCase("PNG")) && (!Flags[j].equalsIgnoreCase("TXT")))
						continue;            
					i++;        
				}                           
				BufferedReader Br = new BufferedReader(new FileReader(path_));
				fileWorker fw = new fileWorker();
				
				String Line = Br.readLine();         
				int tile_x = 0;       
				int tile_y = 0;    
				double dx = 0;   
				double dy = 0;  
				int tile_bdr = 0;   
				int wordsize = 1;     
				double scale_factor = 0.0;       
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
		String File = "";        
		if (Flags[0].equalsIgnoreCase("--locate"))        
		{            
			System.out.print("The following file matches your location: ");       
			
			scale_factor = dx;            
			int size_x = (int)(tile_x * dx + 0.5);     
			int size_y = (int)(tile_y * dy + 0.5); 
			//int Lat = (int)(Double.parseDouble(Flags[1]) / 10.0) * 10;            
			//int Lon = (int)(Double.parseDouble(Flags[2]) / 10.0) * 10;
			double Lat = Double.parseDouble(Flags[1]);            
			double Lon = Double.parseDouble(Flags[2]);  
			if (Double.parseDouble(Flags[1]) < 0.0)                
				Lat -= 10;            
			if (Double.parseDouble(Flags[2]) < 0.0)                
				Lon -= 10;            
			int X = (int) ((Lon + 180) * tile_x / size_x);            
			int Y = (int) ((Lat + 90) * tile_y / size_y);            
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
			if ((X + tile_x >= 10000) && (X + tile_x < 100000))                
				X2 = "" + (X + tile_x);            
			else 
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
			if ((Y + tile_y >= 10000) && (Y + tile_y < 100000))                
				Y2 = "" + (Y + tile_y);            
			else 
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
			File = (String)X1 + "-" + X2 + "." + Y1 + "-" + Y2;
			st =  new str2(X1,Y1);
			System.out.println(File);                                 
			if (Flags[3] == null)            
			{                
				System.out.println();                
				System.exit(0);            
				}            
			else                
				System.out.println("Using this file for input...");        
			}
		return st;
	}
	
	public static String fName_(String[] Flags, String path_) throws Exception  
	{
		str2 st = null;
		// Counts the number of flags        
				int i = 0;        
				//System.out.println(Flags.length);
				for (int j = 1; j < Flags.length; j++)        
				{            
					if ((!Flags[j].equalsIgnoreCase("ASC")) && (!Flags[j].equalsIgnoreCase("GRD")) && (!Flags[j].equalsIgnoreCase("PNG")) && (!Flags[j].equalsIgnoreCase("TXT")))
						continue;            
					i++;        
				}                           
				BufferedReader Br = new BufferedReader(new FileReader(path_));
				fileWorker fw = new fileWorker();
				
				String Line = Br.readLine();         
				int tile_x = 0;       
				int tile_y = 0;    
				double dx = 0;   
				double dy = 0;  
				int tile_bdr = 0;   
				int wordsize = 1;     
				double scale_factor = 0.0;       
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
		String File = "";        
		if (Flags[0].equalsIgnoreCase("--locate"))        
		{            
			System.out.print("The following file matches your location: ");       
			
			scale_factor = dx;            
			int size_x = (int)(tile_x * dx + 0.5);     
			int size_y = (int)(tile_y * dy + 0.5); 
			//int Lat = (int)(Double.parseDouble(Flags[1]) / 10.0) * 10;            
			//int Lon = (int)(Double.parseDouble(Flags[2]) / 10.0) * 10;
			double Lat = Double.parseDouble(Flags[1]);            
			double Lon = Double.parseDouble(Flags[2]);  
			if (Double.parseDouble(Flags[1]) < 0.0)                
				Lat -= 10;            
			if (Double.parseDouble(Flags[2]) < 0.0)                
				Lon -= 10;            
			int X = (int) ((Lon + 180) * tile_x / size_x);            
			int Y = (int) ((Lat + 90) * tile_y / size_y);            
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
			if ((X + tile_x >= 10000) && (X + tile_x < 100000))                
				X2 = "" + (X + tile_x);            
			else 
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
			if ((Y + tile_y >= 10000) && (Y + tile_y < 100000))                
				Y2 = "" + (Y + tile_y);            
			else 
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
			File = (String)X1 + "-" + X2 + "." + Y1 + "-" + Y2;
			st =  new str2(X1,Y1);
			System.out.println(File);                                 
			if (Flags[3] == null)            
			{                
				System.out.println();                
				System.exit(0);            
				}            
			else                
				System.out.println("Using this file for input...");        
			}
		return File;
	}
}
