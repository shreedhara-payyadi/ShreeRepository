package target.system.programming.main;

import java.io.File;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Shreedhara P
 *
 */
public class SystemsProgramming_1 {
	public static File path;

	public static void main(String[] args) {

		System.out.println("Enter the Directory Path:");

		// Parsing mount point as an argument
		Scanner inputFromTheConsole = new Scanner(System.in);
		String direcPath = inputFromTheConsole.nextLine();

		// terminate the program if mount point is NULL or EMPTY otherwise set
		// it as PATH
		if (direcPath != null && !direcPath.isEmpty())
			path = new File(direcPath);
		else
			terminateProgramWithTheMessage("Path should not be empty");

		// if path is ROOT then terminate with the message
		if (path.getParent() != null)
			generateJSONofDiskUsage(path, direcPath);
		else
			terminateProgramWithTheMessage("Path is wrong or Root cannot be a path");

	}

	/**
	 * 
	 * @param directory
	 * @param inputPath
	 */
	private static void generateJSONofDiskUsage(File directory, String inputPath) {
		JSONArray arrayJson = new JSONArray();

		// List all directory files
		File[] fList = directory.listFiles();

		// Check if list is not null and also folder size, if so then terminate
		// the program
		if (fList != null && fList.length > 0) {

			// loop over the list of files
			for (File file : fList) {
				try {

					// if the file is a directory, then call a method to iterate
					// directory files
					if (file.isDirectory()) {
						FilesInsideTheDirectory(file, arrayJson, inputPath);
					} else if (file.isFile()) {

						// Create a JSON Object and add file path as a key value
						// as size in byte
						JSONObject Objectjson = new JSONObject();
						Objectjson.put(getFilePath(file, inputPath), file.length());
						// Appending Object into an arrayJSON
						arrayJson.put(Objectjson);
					}
				} catch (Exception e) {
					terminateProgramWithTheMessage(
							"Exception While Fetching data from__ " + file.getName() + " __ to JSON");
				}
			}
		} else {
			terminateProgramWithTheMessage("No files are present at this path");
		}

		// TO meet the requirement Adding arrayJSON as value to the key "files "
		// in a JSON Object
		JSONObject finalJson = new JSONObject();
		finalJson.put("files:", arrayJson);

		// Finally printing result as a JSONObject to print in pretty format
		// used toString(4)
		System.out.println(" JSON RESULT:-\n----------------------------------------------\n" + finalJson.toString(4));

	}

	/**
	 * 
	 * @param file
	 * @param arrayJson
	 * @param inputPath
	 *            Method will loop for nested directory, and add file details to
	 *            the JSON
	 */
	private static void FilesInsideTheDirectory(File file, JSONArray arrayJson, String inputPath) {

		// List all files as same as previous and loop the fies
		File[] dirList = file.listFiles();
		for (File dirFile : dirList) {
			try {
				if (dirFile.isDirectory())
					FilesInsideTheDirectory(dirFile, arrayJson, inputPath);
				else {
					JSONObject json = new JSONObject();
					json.put(getFilePath(dirFile, inputPath), dirFile.length());
					arrayJson.put(json);
				}
			} catch (Exception e) {
				terminateProgramWithTheMessage(
						"Exception While Fetching data from__ " + file.getName() + " __ to JSON");
			}
		}

	}

	/**
	 * 
	 * @param dirFile
	 * @param inputPath
	 * @return a path, starts from Directory which we passed as an argument at
	 *         the begining and replace "\\" to "/"
	 */
	private static String getFilePath(File dirFile, String inputPath) {
		return dirFile.getPath().substring(dirFile.getPath().indexOf(inputPath.substring(inputPath.lastIndexOf("\\"))))
				.replace("\\", "/");

	}

	// Function which will terminate the program after printing the message
	private static void terminateProgramWithTheMessage(String string) {
		System.out.println(string);
		System.exit(0);

	}
}
