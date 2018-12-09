package target.system.programming.main_addOn;

import java.io.File;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Shreedhara p
 *
 */
public class SystemsProgramming_2 {
	public static File path;

	public static void main(String[] args) {
		
		System.out.println("Enter the Directory Path:");
		Scanner inputFromTheConsole = new Scanner(System.in);
		String direcPath = inputFromTheConsole.nextLine();

		if (direcPath != null && !direcPath.isEmpty()) 
			path = new File(direcPath);
		 else 
			terminateProgramWithTheMessage("Path should not be empty");

		if(path.getParent()!=null)
		generateJSONofDiskUsage(path, direcPath);
		else
			terminateProgramWithTheMessage(" Path is wrong or root cannot be a path");

	}
	
	private static void terminateProgramWithTheMessage(String string) {
		System.out.println(string);
		System.exit(0);
		
	}

	private static void generateJSONofDiskUsage(File directory, String inputPath) {
		JSONObject Objectjson = new JSONObject();
		JSONArray arrayJson = new JSONArray();

		File[] fList = directory.listFiles();
		if (fList != null && fList.length > 0) {

			for (File file : fList) {
				try {
					if (file.isDirectory()) {
						FilesInsideTheDirectory(file, Objectjson, arrayJson, inputPath);
					} else if (file.isFile()) {
						Objectjson.put(getFilePath(file, inputPath), file.length());
					}
				} catch (Exception e) {
					terminateProgramWithTheMessage("Exception While Fetching data from__ " + file.getName()+" __ to JSON");
				}
			}
		} else {
			terminateProgramWithTheMessage("No files are present at this path");
		}
		arrayJson.put(Objectjson);
		JSONObject finalJson = new JSONObject();
		finalJson.put("files:", arrayJson);

		System.out.println(":::JSON RESULT:::\n---------------------------\n" + finalJson.toString(4));

	}

	private static void FilesInsideTheDirectory(File file, JSONObject json, JSONArray arrayJson, String inputPath) {

		File[] dirList = file.listFiles();
		for (File dirFile : dirList) {
			try {
				if (dirFile.isDirectory())
					FilesInsideTheDirectory(dirFile, json, arrayJson, inputPath);
				else

					json.put(getFilePath(dirFile, inputPath), dirFile.length());
			} catch (Exception e) {
				terminateProgramWithTheMessage("Exception While Fetching data from__ " + file.getName()+" __ to JSON");
			}
		}

	}

	private static String getFilePath(File dirFile, String inputPath) {
		return dirFile.getPath().substring(dirFile.getPath().indexOf(inputPath.substring(inputPath.lastIndexOf("\\"))))
				.replace("\\", "/");

	}
}
