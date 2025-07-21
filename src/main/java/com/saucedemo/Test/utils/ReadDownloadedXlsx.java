package com.saucedemo.Test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.saucedemo.Test.BaseClass;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.*;


public class ReadDownloadedXlsx extends BaseClass {

	    private static final String DOWNLOAD_DIR = System.getProperty("user.home") + "/Downloads";

	    private static String latestFilePath;
	    private static List<List<String>> sheetData;
	    private static List<String> headers;
	    private static String latestSessionTime;

	 // method to wait for the new download
	    public static File waitForNewDownload(String filePrefix, String extension, long startTime, int timeoutSeconds) {
	        File downloadDir = new File(DOWNLOAD_DIR);
	        long endTime = System.currentTimeMillis() + timeoutSeconds * 1000;

	        while (System.currentTimeMillis() < endTime) {
	            File[] matchingFiles = downloadDir.listFiles((dir, name) ->
	                name.startsWith(filePrefix) && name.endsWith(extension));

	            if (matchingFiles != null) {
	                Optional<File> newFile = Arrays.stream(matchingFiles)
	                    .filter(f -> f.lastModified() > startTime)
	                    .max(Comparator.comparingLong(File::lastModified));

	                if (newFile.isPresent()) {
	                    System.out.println(" New file detected: " + newFile.get().getName());
	                    return newFile.get();
	                }
	            }

	            try {
	                Thread.sleep(1000); // wait before retry
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	            }
	        }

	        throw new RuntimeException(" Timed out waiting for downloaded XLSX file.");
	    }

	    // method: uses startTime and waits for the new file
	    public static void openLatestXlsxFile(String filePrefix, String fileExtension, long downloadStartTime) {
			File downloadedFile = waitForNewDownload(filePrefix, fileExtension, downloadStartTime, 30);
	        latestFilePath = downloadedFile.getAbsolutePath();

	        System.out.println(" Opening file: " + downloadedFile.getName());
	        System.out.println(" File path: " + latestFilePath);
	    }



	 // Step 2: Access the target sheet and load it into memory
	    public static void accessTheSheet(String sheetName) {
	        sheetData = new ArrayList<>();

	        try (FileInputStream file = new FileInputStream(latestFilePath);
	             Workbook workbook = WorkbookFactory.create(file)) {

	            Sheet sheet = workbook.getSheet(sheetName);
	            if (sheet == null) {
	                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found.");
	            }
	            
	         // ➤ Unhide all columns
	            
	            for (int col = 0; col < sheet.getRow(0).getLastCellNum(); col++) {
	                sheet.setColumnHidden(col, false);
	            }
	            
	            
	            boolean headerFound = false;
	            DataFormatter formatter = new DataFormatter(); // Ensures proper formatting

	            for (Row row : sheet) {
	                List<String> rowData = new ArrayList<>();
	                for (int j = 0; j < row.getLastCellNum(); j++) {
	                    Cell cell = row.getCell(j);
	                    rowData.add(cell != null ? formatter.formatCellValue(cell).trim() : "[EMPTY]");
	                }

	                // Find and set the header row (contains "Session Start Time")
	                if (!headerFound && (
	                        rowData.contains("Session Start Time") || 
	                        rowData.contains("Session Start Date") || 
	                        rowData.contains("Attribute") || 
	                        rowData.contains("ResourceSessionTypeName") || 
	                        rowData.contains("AttributeValue"))) {
	                    headers = rowData;
	                    headerFound = true;
	                }

	                // Skip rows until headers are found
	                if (headerFound) {
	                    sheetData.add(rowData);
	                }
	            }

	            if (!headerFound) {
	                throw new IllegalStateException("Header row with 'Session Start Time' not found.");
	            }

	        } catch (IOException e) {
	            throw new RuntimeException("Error reading Excel sheet: " + e.getMessage());
	        }
        }


	    public static List<List<String>> getSheetData() {
	        return sheetData;
	    }

	    public static List<String> getHeaders() {
	        return headers;
	    }

	    public static String getLatestSessionTime() {
	        return latestSessionTime;
	    }

	    
	}
