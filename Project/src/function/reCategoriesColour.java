package function;

import java.util.Vector;

import javax.swing.JButton;

public class reCategoriesColour {
	/**
	 * Задание цветовой палитры
	 * @param masBt
	 * @param category
	 */
	public static void isCategories(JButton masBt[], int category) {
		Vector category1 = new Vector();
		category1.add("1 Evergreen Needleleaf Forest");
		category1.add("2 Evergreen Broadleaf Forest");
		category1.add("3 Deciduous Needleleaf Forest");
		category1.add("4 Deciduous Broadleaf Forest");
		category1.add("5 Mixed Forests");
		category1.add("6 Closed Shrublands");
		category1.add("7 Open Shrublands");
		category1.add("8 Woody Savannas");
		category1.add("9 Savannas");
		category1.add("10 Grasslands");
		category1.add("11 Permanent Wetlands");
		category1.add("12 Croplands");
		category1.add("13 Urban and Built-Up");
		category1.add("14 Cropland/Natural Vegetation Mosaic");
		category1.add("15 Snow and Ice");
		category1.add("16 Barren or Sparsely Vegetated");
		category1.add("17 Water");
		category1.add("18 Wooded Tundra");
		category1.add("19 Mixed Tundra");
		category1.add("20 Barren Tundra");
		
		Vector category2 = new Vector();
		category2.add("1 Urban and Built-up Land");
		category2.add("2 Dryland Cropland and Pasture");
		category2.add("3 Irrigated Cropland and Pasture");
		category2.add("4 Mixed Dryland/Irrigated Cropland and Pasture");
		category2.add("5 Cropland/Grassland Mosaic");
		category2.add("6 Cropland/Woodland Mosaic");
		category2.add("7 Grassland");
		category2.add("8 Shrubland");
		category2.add("9 Mixed Shrubland/Grassland");
		category2.add("10 Savanna");
		category2.add("11 Deciduous Broadleaf Forest");
		category2.add("12 Deciduous Needleleaf Forest");
		category2.add("13 Evergreen Broadleaf");
		category2.add("14 Evergreen Needleleaf");
		category2.add("15 Mixed Forest");
		category2.add("16 Water Bodies");
		category2.add("17 Herbaceous Wetland");
		category2.add("18 Wooden Wetland");
		category2.add("19 Barren or Sparsely Vegetated");
		category2.add("20 Herbaceous Tundra");
		category2.add("21 Wooded Tundra");
		category2.add("22 Mixed Tundra");
		category2.add("23 Bare Ground Tundra");
		category2.add("24 Snow or Ice");
		
		Vector category3 = new Vector();
		category3.add("0");
		category3.add("1");
		category3.add("2");
		category3.add("3");
		category3.add("4");
		category3.add("5");
		category3.add("6");
		category3.add("7");
		category3.add("8");
		category3.add("9");
		category3.add("10");
		category3.add("11");
		category3.add("12");
		category3.add("13");
		category3.add("14");
		category3.add("15");
		category3.add("16");
		category3.add("17");
		category3.add("18");
		category3.add("19");
		category3.add("20");
		category3.add("21");
		category3.add("22");
		category3.add("23");
		category3.add("24");
		category3.add("25");
		category3.add("26");
		category3.add("27");
		category3.add("28");
		category3.add("29");
		category3.add("30");
		category3.add("31");
		category3.add("32");
		category3.add("33");
		
		Vector category4 = new Vector();
		category4.add("1 Evergreen Broadleaf");
		category4.add("2 Dryland Cropland and Pasture");
		category4.add("3 Irrigated Cropland and Pasture");
		category4.add("4 Mixed Dryland/Irrigated Cropland and Pasture");
		category4.add("5 Cropland/Grassland Mosaic");
		category4.add("6 Cropland/Woodland Mosaic");
		category4.add("7 Grassland");
		category4.add("8 Shrubland");
		category4.add("9 Mixed Shrubland/Grassland");
		category4.add("10 Savanna");
		category4.add("11 Deciduous Broadleaf Forest");
		category4.add("12 Deciduous Needleleaf Forest");
		category4.add("13 Urban and Built-up Land");
		category4.add("14 Evergreen Needleleaf");
		category4.add("15 Ice");
		category4.add("16 Herbaceous Wetland");
		category4.add("17 Water");
		category4.add("18 Wooden Wetland");
		category4.add("19 Barren or Sparsely Vegetated");
		category4.add("20 Herbaceous Tundra");
		category4.add("21 Lake");
		
		for(int i = 0; i < masBt.length; i++)
		{
			masBt[i].setVisible(false);
		}
		
		if(category == 1)
			for(int i = 0; i < category1.size(); i++)
			{
				masBt[i].setToolTipText(category1.get(i).toString());
				masBt[i].setVisible(true);
			}
		else
			if(category == 2)
				for(int i = 0; i < category2.size(); i++)
				{
					masBt[i].setToolTipText(category2.get(i).toString());
					masBt[i].setVisible(true);
				}
			else
				if(category == 3)
					for(int i = 0; i < category3.size(); i++)
					{
						masBt[i].setToolTipText(category3.get(i).toString());
						masBt[i].setVisible(true);
					}
				else
					if(category == 4)
						for(int i = 0; i < category4.size(); i++)
						{
							masBt[i].setToolTipText(category4.get(i).toString());
							masBt[i].setVisible(true);
						}
	}
}
