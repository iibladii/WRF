package component;

import javax.swing.JLabel;

public class dataBox {//Парсим index файл и сохраняем значения
	 private static String startW = "";
	 private static String startH = "";
	 private static String endW = "";
	 private static String endH = "";
	 private static String lon = "";
	 private static String lat = "";
	 private static String dx__ = "";
	 private static String dy__ = "";
	 private static String tx__ = "";
	 private static String ty__ = "";
	 private static String category_min_ = "";
	 private static String category_max_ = "";
	 public static int category_min = 1;
	 public static int category_max = 21;
	 public static Double lon_ = 0.0;
	 public static Double lat_ = 0.0;
	 public static int tx_ = 0;
	 public static int ty_ = 0;
	 public static double dx = 0.0;
	 public static double dy = 0.0;
	 public static JLabel lblLonLat;
	 public static int start_x = 0;
	 public static int start_y = 0;
	 //String startW, String startH, String endW, String endH
	 public static void newLabel(JLabel lblLonLat_) {
		 lblLonLat = lblLonLat_;
	 }

	 public static void setLocate(String str)
	 {
		 startW = "";
		 startH = "";
		 endW = "";
		 endH = "";
		 lon = "";
		 lat = "";
		 tx__ = "";
		 ty__ = "";
		 lon_ = 0.0;
		 lat_ = 0.0;
		 dx = 0.0;
		 dy = 0.0;
		 dx__ = "";
		 dy__ = "";
		 tx_ = 0;
		 ty_ = 0;
		 category_min_ = "";
		 category_max_ = "";
		 
		 int d = str.length();
		 int lat_pos = str.indexOf("lat");
		 if(lat_pos!=-1)
			 lat_pos = str.indexOf('=',lat_pos);
		 int lon_pos = str.indexOf("lon");
		 if(lon_pos!=-1)
			 lon_pos = str.indexOf('=',lon_pos);
		 
		 int tx = str.indexOf("tile_x");
		 tx = str.indexOf('=',tx);
		 int ty = str.indexOf("tile_y");
		 ty = str.indexOf('=',ty);
		 
		 int dx_ = str.indexOf("dx");
		 dx_ = str.indexOf('=',dx_);
		 int dy_ = str.indexOf("dy");
		 dy_ = str.indexOf('=',dy_);
		 
		 int category_min_pos = str.indexOf("category_min");
		 if(category_min_pos!=-1)
			 category_min_pos = str.indexOf('=',category_min_pos);
		 
		 int category_max_pos = str.indexOf("category_max");
		 if(category_max_pos!=-1)
			 category_max_pos = str.indexOf('=',category_max_pos);
		 
		 int fl = 0;
		 if(category_min_pos!=-1)
			 for(int i = category_min_pos; i<str.length(); i++)
			 {
			 
				 if(((str.charAt(i)) == '.') || ((str.charAt(i)) == '-') || ((str.charAt(i)) == '1') || (str.charAt(i)) == '2' || (str.charAt(i)) == '3' || (str.charAt(i)) == '4' || (str.charAt(i)) == '5'
					 || (str.charAt(i)) == '6' || (str.charAt(i)) == '7' || (str.charAt(i)) == '8' || (str.charAt(i)) == '9' || (str.charAt(i)) == '0')
				 {
					 fl = 1;
					 category_min_ += str.charAt(i);
				 }
				 else
					 if(fl == 1)
						 break;
			 }
		 	 else
		 	 {
		 		category_min_ = "1";
		 	 }
			 
			 
			 
		 fl = 0;
		 if(category_max_pos!=-1)
			 for(int i = category_max_pos; i<str.length(); i++)
			 {
				 if(((str.charAt(i)) == '.') || ((str.charAt(i)) == '-') || ((str.charAt(i)) == '1') || (str.charAt(i)) == '2' || (str.charAt(i)) == '3' || (str.charAt(i)) == '4' || (str.charAt(i)) == '5'
					 || (str.charAt(i)) == '6' || (str.charAt(i)) == '7' || (str.charAt(i)) == '8' || (str.charAt(i)) == '9' || (str.charAt(i)) == '0')
				 {
					 fl = 1;
					 category_max_ += str.charAt(i);
				 }
				 else
					 if(fl == 1)
						 break;
		 	}
		 	else
		 	{
		 		category_max_ = "25";
		 	}
		 
		 
		//////////////////////////////////////////////////////////////
		fl = 0;
		for(int i = dx_; i<str.length(); i++)
		{
			if(((str.charAt(i)) == '.') || ((str.charAt(i)) == '1') || (str.charAt(i)) == '2' || (str.charAt(i)) == '3' || (str.charAt(i)) == '4' || (str.charAt(i)) == '5'
				|| (str.charAt(i)) == '6' || (str.charAt(i)) == '7' || (str.charAt(i)) == '8' || (str.charAt(i)) == '9' || (str.charAt(i)) == '0')
			{
				fl = 1;
				dx__ += str.charAt(i);
			}
			else
				if(fl == 1)
					break;
		}
		
		fl = 0;
		for(int i = dy_; i<str.length(); i++)
		{
			if(((str.charAt(i)) == '.') || ((str.charAt(i)) == '1') || (str.charAt(i)) == '2' || (str.charAt(i)) == '3' || (str.charAt(i)) == '4' || (str.charAt(i)) == '5'
				|| (str.charAt(i)) == '6' || (str.charAt(i)) == '7' || (str.charAt(i)) == '8' || (str.charAt(i)) == '9' || (str.charAt(i)) == '0')
			{
				fl = 1;
				dy__ += str.charAt(i);
			}
			else
				if(fl == 1)
					break;
		}
		///////////////////////////////////////////////////////////////
		 
		 
		 fl = 0;
		 for(int i = lat_pos; i<str.length(); i++)
		 {
			 
			 if(((str.charAt(i)) == '.') || ((str.charAt(i)) == '-') || ((str.charAt(i)) == '1') || (str.charAt(i)) == '2' || (str.charAt(i)) == '3' || (str.charAt(i)) == '4' || (str.charAt(i)) == '5'
					 || (str.charAt(i)) == '6' || (str.charAt(i)) == '7' || (str.charAt(i)) == '8' || (str.charAt(i)) == '9' || (str.charAt(i)) == '0')
			 {
				 fl = 1;
				 lat += str.charAt(i);
			 }
			 else
				 if(fl == 1)
					 break;
		 }
		 
		 fl = 0;
		 for(int i = lon_pos; i<str.length(); i++)
		 {
			 
			 if(((str.charAt(i)) == '.') || ((str.charAt(i)) == '-') || ((str.charAt(i)) == '1') || (str.charAt(i)) == '2' || (str.charAt(i)) == '3' || (str.charAt(i)) == '4' || (str.charAt(i)) == '5'
					 || (str.charAt(i)) == '6' || (str.charAt(i)) == '7' || (str.charAt(i)) == '8' || (str.charAt(i)) == '9' || (str.charAt(i)) == '0')
			 {
				 fl = 1;
				 lon += str.charAt(i);
			 }
			 else
				 if(fl == 1)
					 break;
		 }
		 
		 fl = 0;
		 for(int i = tx; i<str.length(); i++)
		 {
			 
			 if(((str.charAt(i)) == '.') || ((str.charAt(i)) == '1') || (str.charAt(i)) == '2' || (str.charAt(i)) == '3' || (str.charAt(i)) == '4' || (str.charAt(i)) == '5'
					 || (str.charAt(i)) == '6' || (str.charAt(i)) == '7' || (str.charAt(i)) == '8' || (str.charAt(i)) == '9' || (str.charAt(i)) == '0')
			 {
				 fl = 1;
				 tx__ += str.charAt(i);
			 }
			 else
				 if(fl == 1)
					 break;
		 }
		 
		 fl = 0;
		 for(int i = ty; i<str.length(); i++)
		 {
			 
			 if(((str.charAt(i)) == '.') || ((str.charAt(i)) == '1') || (str.charAt(i)) == '2' || (str.charAt(i)) == '3' || (str.charAt(i)) == '4' || (str.charAt(i)) == '5'
					 || (str.charAt(i)) == '6' || (str.charAt(i)) == '7' || (str.charAt(i)) == '8' || (str.charAt(i)) == '9' || (str.charAt(i)) == '0')
			 {
				 fl = 1;
				 ty__ += str.charAt(i);
			 }
			 else
				 if(fl == 1)
					 break;
		 }
		 
		 lon_ = Double.parseDouble(lon);
		 lat_ = Double.parseDouble(lat);
		 tx_ = Integer.parseInt(tx__);
		 ty_ = Integer.parseInt(ty__);
		 dx = Double.parseDouble(dx__);
		 dy = Double.parseDouble(dy__);
		 category_min = Integer.parseInt(category_min_);
		 category_max = Integer.parseInt(category_max_);
	 }
}