package target.system.programming.main_addOn;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Shreedhara p
 *
 */
public class SystemsProgramming_3 {
	public static File path;

	public static void main(String[] args) throws IOException {
		System.out.println("Enter the Directory Path:");
		Scanner inputFromTheConsole = new Scanner(System.in);
		String direcPath = inputFromTheConsole.nextLine();

		if (direcPath != null && !direcPath.isEmpty())
			path = new File(direcPath);
		else
			terminateProgramWithTheMessage("Path should not be empty");

		if (path.getParent() != null)
			generateJSONofDiskUsage(path, direcPath);
		else
			terminateProgramWithTheMessage("Path is wrong or root cannot be a path");

	}

	private static void terminateProgramWithTheMessage(String string) {
		System.out.println(string);
		System.exit(0);

	}

	private static void generateJSONofDiskUsage(File directory, String inputPath) {
		JSONObject json = new JSONObject();
		JSONArray arrayJson = new JSONArray();

		File[] fList = directory.listFiles();
		if (fList != null && fList.length > 0) {
			for (File file : fList) {
				try {
					if (file.isDirectory()) {
						FilesInsideTheDirectory(file, json);
					} else if (file.isFile()) {
						json.put(file.getName(), file.length());
					}
				} catch (Exception e) {
					terminateProgramWithTheMessage(
							"Exception While Fetching data from__ " + file.getName() + " __ to JSON");
				}
			}
		} else {
			terminateProgramWithTheMessage("No files are present at this path ");
		}

		arrayJson.put(json);
		JSONObject finalJson = new JSONObject();
		finalJson.put("files:", arrayJson);
		
		System.out.println(":::JSON RESULT:::\n------------------------------\n" + finalJson.toString(4));
	}

	private static void FilesInsideTheDirectory(File file, JSONObject json) {
		JSONObject dirJSon = new JSONObject();
		JSONArray dirArr = new JSONArray();

		File[] dirList = file.listFiles();
		for (File dirFile : dirList) {
			if (dirFile.isDirectory())
				FilesInsideTheDirectory(dirFile, dirJSon);
			else
				dirJSon.put(dirFile.getName(), dirFile.length());
		}
		dirArr.put(dirJSon);

		if (dirArr != null)
			json.put(file.getName(), dirArr);

	}
}
