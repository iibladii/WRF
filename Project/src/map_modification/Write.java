package map_modification;

//Program: Write.java
//Created by Danny Brown
//in Edmonton, Alberta, Canada

//This program takes output from the Read.java program and converts it back
//to the WRF binary format.


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Write
{
 public void writeData(String[] Flags, String IndexFileName, BufferedImage Images) throws Exception
 {
     // Opens the 'index' file for reading.  
     BufferedReader Br = new BufferedReader(new FileReader(IndexFileName));
     String Line = Br.readLine();

     int tile_x = 0;   // The width of the file
     int tile_y = 0;   // The height of the file
     int tile_bdr = 0; // The border width (ignored)
     int wordsize = 1; // The wordsize (in bytes)

     // Loops through the index file and sets the previous variables.  
     while (Line != null)
     {
         String Var = Line.substring(0, Line.indexOf("=")).trim().toLowerCase();
         String Value = Line.substring(Line.indexOf("=") + 1).trim().toLowerCase();
         if (Var.equals("tile_x"))
             tile_x = Integer.parseInt(Value);
         if (Var.equals("tile_y"))
             tile_y = Integer.parseInt(Value);
         if (Var.equals("tile_bdr"))
             tile_bdr = Integer.parseInt(Value);
         if (Var.equals("wordsize"))
             wordsize = Integer.parseInt(Value);
         Line = Br.readLine();
     }

     // An array to hold all the data.  
     double[][] Data = new double[tile_x][tile_y];

     System.out.print("Reading Data...");
//image work
         try
         {
        	 InputStream initialStream = getClass().getResourceAsStream("/resource/Colours.conf");
				byte[] buffer = new byte[initialStream.available()];
			    initialStream.read(buffer);
			    File targetFile = new File("Colours.conf");
			    OutputStream outStream = new FileOutputStream(targetFile);
			    outStream.write(buffer);
			    
             // Opens the 'colours.conf' file for reading.  
             Br = new BufferedReader(new FileReader("Colours.conf"));
             Line = Br.readLine();
             int num = 0; // This just counts the number of colours.  
             while (Line != null)
             {
                 num++;
                 Line = Br.readLine();
             }
             Color[] Colours = new Color[num];  // An array of colours.  
             Br = new BufferedReader(new FileReader("Colours.conf"));
             Line = Br.readLine(); Line = Br.readLine(); Line = Br.readLine(); Line = Br.readLine();
             num = 0; // This section actually loads the colour from the file.  
             while (Line != null)
             {
                 StringTokenizer St = new StringTokenizer(Line, "\t");
                 St.nextToken();
                 Colours[num] = new Color(Integer.parseInt(St.nextToken()), Integer.parseInt(St.nextToken()), Integer.parseInt(St.nextToken()), Integer.parseInt(St.nextToken()));
                 Line = Br.readLine();
                 num++;
             }

             //  This reads the image from the file.  
             BufferedImage Image = Images;//ImageIO.read(new File(Flags[0]));
             //  Loops through all pixels in the image
             for (int i = 0; i < tile_x; i++)
             {
                 for (int j = 0; j < tile_y; j++)
                 {
                     // Picks the colour of the pixel, and checks to see
                     // which land use type goes with this colour.  
                     Color Colour = new Color(Image.getRGB(i, j));
                     for (int m = 0; m < num; m ++)
                     {
                         if (Colours[m].getRed() != Colour.getRed() || Colours[m].getGreen() != Colour.getGreen() || Colours[m].getBlue() != Colour.getBlue())
                         {}
                         else
                         {
                             Data[tile_x - 1 - j][i] = m;
                             break;
                         }
                     }
                 }
             }

             System.out.println("Done!");
             System.out.println("Loaded PNG file to system.");
         }
         catch (Exception e2)
         {  // Prints an error if both of the previous methods fail.  
             System.out.println("***Error***");
             System.out.println("File format not recognised.");
             JOptionPane.showMessageDialog(null, "Формат файла не распознан. Проверьте параметры index файла.");
             //e.printStackTrace();
             e2.printStackTrace();
         }
     // This section writes the data to the wrf binary file.  
     System.out.print("Writing data to binary file...");
     FileOutputStream Fs = new FileOutputStream(Flags[1]);
     for (int i = 0; i < ((tile_x * wordsize + tile_bdr * wordsize * 2)*tile_bdr); i ++)
         Fs.write(99); // Writes dummy data to the top tile border
     // Proceeds line by line.  
     for (int i = 0; i < tile_x; i ++)
     {   // Writes dummy data to the first few border grid cells.  
         for (int j = 0; j < tile_bdr * wordsize; j ++)
             Fs.write(99);
         for (int j = 0; j < tile_y; j ++)
         {  // Tests for the wordsize and writes all the data accordingly.
             if (wordsize == 1)
                 Fs.write((int)Data[i][j]);
             else if (wordsize == 2)
             {
                 Fs.write(((int)Data[i][j] >> 8) & 0xFF);
                 Fs.write((int)Data[i][j] & 0xFF);
             }
         } // Writes more dummy data to the last few border grid cells.
         for (int j = 0; j < tile_bdr * wordsize; j ++)
             Fs.write(99);
     }
     for (int i = 0; i < ((tile_x * wordsize + tile_bdr * wordsize * 2)*tile_bdr); i ++)
         Fs.write(99);  // Finalises the bottom border with dummy data.  
     System.out.println("Done!");
     JOptionPane.showMessageDialog(null, "Сохранение прошло успешно!");
 }
}
