package function;

public class verifyLine {
	/**
	 * �������� ������ index ����� �� ������������
	 * @param str ������
	 * @return true - ������ �������� ���������
	 */
	public static Boolean isVerify(String str)
	{
		String s[] = { "type", "category_min", "category_max", "projection", "dx", "dy", "known_x", "known_y", "known_lat", "known_lon", "wordsize", "tile_x", "tile_y", "tile_z", "units", "description", "mminlu", "iswater", "islake", "isice", "isurban"};
		Boolean out = true;
		//�������� ���� �� �������
		String str1 = str;
		if(str.indexOf(' ') != -1 && str.indexOf("description") < 0)
			return false;
		//�������� ���� �� ������� �� ����� �����
		if(str.indexOf('=') != -1 && str.indexOf(' ') != -1 && str.indexOf('=') > str.indexOf(' '))
			return false;
		//�������� ��� �� ��������� ��������� ��� ������� �������
		Boolean fl = false;
		for(int y = 0; y < s.length; y++)
		{
			if(str.indexOf(s[y])!=-1)
				fl = true;
		}
		if(!fl)
			return false;
		//��������� ���������� ���������
		if(str.indexOf("dx") != -1 || str.indexOf("dy") != -1)
		{
			String vStr = "";
			Double category = 0.0;
			for(int j = str.indexOf('=') + 1; j < str.length(); j++)
				if( str.charAt(j)!='\r')
					vStr += str.charAt(j);
			if(vStr.equals(""))
				return false;
			try {//���� ���� ������ � ������� �����
				category = Double.parseDouble(vStr);
			}
			catch(NumberFormatException e) {
				return false;
			}
			finally {
				if(category < 0)
					return false;
			}
		}
		else
			if(str.indexOf("category_min") != -1)
			{
				String vStr = "";
				int category = 0;
				for(int j = str.indexOf('=') + 1; j < str.length(); j++)
					if( str.charAt(j)!='\r')
						vStr += str.charAt(j);
				if(vStr.equals(""))
					return false;
				try {//���� ���� ������ � ������� �����
					category = Integer.parseInt(vStr);
				}
				catch(NumberFormatException e) {
					return false;
				}
				finally {
					if(category < 1 || category > 33)
					return false;
				}
				
			}
			else
				if(str.indexOf("category_max") != -1)
				{
					String vStr = "";
					int category = 0;
					for(int j = str.indexOf('=') + 1; j < str.length(); j++)
						if( str.charAt(j)!='\r')
							vStr += str.charAt(j);
					if(vStr.equals(""))
						return false;
					try {//���� ���� ������ � ������� �����
						category = Integer.parseInt(vStr);
					}
					catch(NumberFormatException e) {
						return false;
					}
					finally {
						if(category < 2 || category > 33)
							return false;
					}
				}
				else
					if(str.indexOf("known_lat") != -1 || str.indexOf("known_lon") != -1)
					{
						String vStr = "";
						Double category = 0.0;
						for(int j = str.indexOf('=') + 1; j < str.length(); j++)
							if( str.charAt(j)!='\r')
								vStr += str.charAt(j);
						if(vStr.equals(""))
							return false;
						try {//���� ���� ������ � ������� �����
							category = Double.parseDouble(vStr);
						}
						catch(NumberFormatException e) {
							return false;
						}
						finally {
							if(category < -180 || category > 180)
								return false;
						}
						
					}
					else
						if(str.indexOf("tile_x") != -1 || str.indexOf("tile_y") != -1 || str.indexOf("tile_z") != -1)
						{
							String vStr = "";
							int category = 0;
							for(int j = str.indexOf('=') + 1; j < str.length(); j++)
								if( str.charAt(j)!='\r')
									vStr += str.charAt(j);
							if(vStr.equals(""))
								return false;
							try {//���� ���� ������ � ������� �����
								category = Integer.parseInt(vStr);
							}
							catch(NumberFormatException e) {
								return false;
							}
							finally {
								if(category < 1)
									return false;
							}
							
						}
						else
							if(str.indexOf("iswater") != -1 || str.indexOf("islake") != -1 || str.indexOf("isice") != -1 || str.indexOf("isurban") != -1)
							{
								String vStr = "";
								int category = 0;
								for(int j = str.indexOf('=') + 1; j < str.length(); j++)
									if( str.charAt(j)!='\r')
										vStr += str.charAt(j);
								if(vStr.equals(""))
									return false;
								try {//���� ���� ������ � ������� �����
									category = Integer.parseInt(vStr);
								}
								catch(NumberFormatException e) {
									return false;
								}
								finally {
									if(category < 0 || category > 33)
										return false;
								}
								
							}
							else
								if(str.indexOf("known_x") != -1 || str.indexOf("known_y") != -1)
								{
									String vStr = "";
									Double category = 0.0;
									for(int j = str.indexOf('=') + 1; j < str.length(); j++)
										if( str.charAt(j)!='\r')
											vStr += str.charAt(j);
									if(vStr.equals(""))
										return false;
									try {//���� ���� ������ � ������� �����
										category = Double.parseDouble(vStr);
									}
									catch(NumberFormatException e) {
										return false;
									}
									finally {
										if(category < 0.0)
											return false;
									}
								}
								else
									if(str.indexOf("wordsize") != -1)
									{
										String vStr = "";
										int category = 0;
										for(int j = str.indexOf('=') + 1; j < str.length(); j++)
											if( str.charAt(j)!='\r')
												vStr += str.charAt(j);
										if(vStr.equals(""))
											return false;
										try {//���� ���� ������ � ������� �����
											category = Integer.parseInt(vStr);
										}
										catch(NumberFormatException e) {
											return false;
										}
										finally {
											if(category < 1)
												return false;
										}
									}
		return true;
	}
}
