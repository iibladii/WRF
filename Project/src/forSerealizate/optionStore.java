package forSerealizate;

/**
 * openFileName - имя файла
 * openFilePath - путь
 * @author megroup9gmail.com
 *
 */
public class optionStore {
	private static String openFileName = "";
	private static String openFilePath = "";
	
	/**
	 * Сохраним путь
	 * @param path путь
	 * @param name имя файла
	 */
	public static void setFile_PathName(String path, String name) {
		openFilePath = path;
		openFileName = name;
	}
	
	public static String getOpenFileName() {
		return openFileName;
	}
	
	public static String getOpenFilePath() {
		return openFilePath;
	}
}
