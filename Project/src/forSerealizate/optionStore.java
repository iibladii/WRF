package forSerealizate;

/**
 * openFileName - ��� �����
 * openFilePath - ����
 * @author megroup9gmail.com
 *
 */
public class optionStore {
	private static String openFileName = "";
	private static String openFilePath = "";
	
	/**
	 * �������� ����
	 * @param path ����
	 * @param name ��� �����
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
