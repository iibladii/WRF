package function;

public class getStartCoordinate {

/**
 * Получим смещение по x из имени файла
 * @param str
 * @return
 */
public static int getStartX(String str){
	String str1 = "";
	int fl = 0;
	if(str.indexOf(".")>0 && str.indexOf("-")>0)
	{
	for(int i = 0; i<str.length(); i++)
	 {
		 
		 if(((str.charAt(i)) == '1') || (str.charAt(i)) == '2' || (str.charAt(i)) == '3' || (str.charAt(i)) == '4' || (str.charAt(i)) == '5'
				 || (str.charAt(i)) == '6' || (str.charAt(i)) == '7' || (str.charAt(i)) == '8' || (str.charAt(i)) == '9' || (str.charAt(i)) == '0')
		 {
			 fl = 1;
			 str1 += str.charAt(i);
		 }
		 else
			 if(fl == 1)
				 break;
	 }
	return Integer.parseInt(str1);
	}
	else
		return 0;
}

/**
 * Получим смещение по y из имени файла
 * @param str
 * @return
 */
public static int getStartY(String str){
	String str1 = "";
	int fl = 0;
	
	int category_min_pos = str.indexOf(".");
	
	if(str.indexOf(".")>0 && str.indexOf("-")>0)
	{
	for(int i = category_min_pos; i<str.length(); i++)
	 {
		 
		 if(((str.charAt(i)) == '1') || (str.charAt(i)) == '2' || (str.charAt(i)) == '3' || (str.charAt(i)) == '4' || (str.charAt(i)) == '5'
				 || (str.charAt(i)) == '6' || (str.charAt(i)) == '7' || (str.charAt(i)) == '8' || (str.charAt(i)) == '9' || (str.charAt(i)) == '0')
		 {
			 fl = 1;
			 str1 += str.charAt(i);
		 }
		 else
			 if(fl == 1)
				 break;
	 }
	return Integer.parseInt(str1);
	}
	else
		return 0;
}

}
