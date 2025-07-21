package com.saucedemo.Test.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataReader {
	
	
	// Using getDataFromIndex(int index) we are passing the index number to fetch the data set from .json file
	
	public Object[][] getDataFromIndex(int index) throws IOException {

		JsonDataReader dr = new JsonDataReader();

		List<HashMap<String, String>> data = dr.getJsonData(
				System.getProperty("user.dir") + "//src\\test//" + "java//com//hdfc//testData//TestData.json");

		return new Object[][] { { data.get(index) } };

	}
	
	// This code will get the data from .json file and put it in hashmap object
	public List<HashMap<String, String>> getJsonData(String filePath) throws IOException {
		
		// read json data to string 
		String jsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		
		// String to hashmap -- add dependency "Jackson Databind"
		
	ObjectMapper mapper = new ObjectMapper();
	List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, 
			String>>>(){});
		
		return data;
		
		
	}

}
