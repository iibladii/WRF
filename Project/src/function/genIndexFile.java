package function;

public class genIndexFile {
	public static String generate() {
		String str = 
				"type=categorical\n" + 
				"category_min=1\n" + 
				"category_max=21\n" + 
				"projection=regular_ll\n" + 
				"dx=\n" + 
				"dy=\n" + 
				"known_x=\n" + 
				"known_y=\n" + 
				"known_lat=\n" + 
				"known_lon=\n" + 
				"wordsize=\n" + 
				"tile_x=\n" + 
				"tile_y=\n" + 
				"tile_z=\n" + 
				"units=\"category\"\n" + 
				"description=\"Noah-modified 21-category IGBP-MODIS landuse\"\n" + 
				"mminlu=\"MODIFIED_IGBP_MODIS_NOAH\"";
		return str;
	}
}
