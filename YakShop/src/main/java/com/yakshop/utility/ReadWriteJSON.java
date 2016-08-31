package com.yakshop.utility;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author Sunny Mourya
 * 
 *         This class contains utility methods to read and write data to/from
 *         json files
 *
 */
public class ReadWriteJSON {

	/**
	 * @param inputFileName
	 * @param inputJson
	 * 
	 *            This method creates or updates json file
	 */
	public static void writeJSON(String inputFileName, JSONObject inputJson) {
		try (FileWriter file = new FileWriter(inputFileName)) {
			file.write(inputJson.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param inputFileName
	 * @return JSONObject
	 * 
	 *         This method reads values from a json file
	 */
	public static JSONObject readJSON(String inputFileName) {
		JSONObject jsonObject = null;
		JSONParser jsonParser = new JSONParser();
		try {
			Object obj = jsonParser.parse(new FileReader(inputFileName));
			jsonObject = (JSONObject) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
