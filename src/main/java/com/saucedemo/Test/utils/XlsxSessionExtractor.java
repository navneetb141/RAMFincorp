package com.saucedemo.Test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;

public class XlsxSessionExtractor {

    private static final String DOWNLOAD_DIR = System.getProperty("user.home") + "/Downloads";
    private static List<List<String>> sheetData;
    private static List<String> headers;
    private static List<List<String>> latestSessionRows;

    /**
     * Waits for a new download in the user's Downloads folder.
     */
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
                    System.out.println("New file detected: " + newFile.get().getName());
                    return newFile.get();
                }
            }
            try {
                Thread.sleep(1000); // wait before retry
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        throw new RuntimeException("Timed out waiting for downloaded XLSX file.");
    }

    /**
     * Reads the specified sheet into memory and extracts headers/data.
     */
    public static void loadSheetData(File xlsxFile, String sheetName) {
        sheetData = new ArrayList<>();
        headers = null;

        try (FileInputStream file = new FileInputStream(xlsxFile);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null)
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found.");

            // Unhide all columns for completeness
            for (int col = 0; col < sheet.getRow(0).getLastCellNum(); col++) {
                sheet.setColumnHidden(col, false);
            }

            boolean headerFound = false;
            DataFormatter formatter = new DataFormatter();

            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    rowData.add(cell != null ? formatter.formatCellValue(cell).trim() : "[EMPTY]");
                }

                if (!headerFound && rowData.stream().anyMatch(h -> !h.equals("[EMPTY]"))) {
                    headers = rowData;
                    headerFound = true;
                }

                if (headerFound) {
                    sheetData.add(rowData);
                }
            }
            if (headers == null)
                throw new IllegalStateException("Header row not found.");
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel sheet: " + e.getMessage());
        }
    }

    /**
     * Extracts latest session rows by date/time column names and their formats.
     */
    public static List<List<String>> extractLatestSessionRows(
            String filePrefix,
            String extension,
            long downloadStartTime,
            String sheetName,
            String dateCol,
            String timeCol,
            String[] valueCols, // e.g. {"Action","Attribute","AttributeValue"}
            String dateFormat,  // e.g. "M/d/yy"
            String timeFormat   // e.g. "h:mm:ss a"
    ) {
        File file = waitForNewDownload(filePrefix, extension, downloadStartTime, 30);
        loadSheetData(file, sheetName);

        int dateIdx = headers.indexOf(dateCol);
        int timeIdx = headers.indexOf(timeCol);

        int[] valueIndices = Arrays.stream(valueCols).mapToInt(h -> headers.indexOf(h)).toArray();
        if (dateIdx == -1 || timeIdx == -1 || Arrays.stream(valueIndices).anyMatch(idx -> idx == -1)) {
            throw new IllegalStateException("Required columns not found in the sheet.");
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeFormat);

        List<LocalDate> validDates = sheetData.stream().skip(1)
                .map(row -> {
                    try {
                        return LocalDate.parse(row.get(dateIdx), dateFormatter);
                    } catch (Exception e) {
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());

        if (validDates.isEmpty())
            throw new IllegalStateException("No valid session start dates found.");

        LocalDate latestDate = Collections.max(validDates);

        List<List<String>> rowsWithLatestDate = sheetData.stream().skip(1)
                .filter(row -> {
                    try {
                        return LocalDate.parse(row.get(dateIdx), dateFormatter).equals(latestDate);
                    } catch (Exception e) {
                        return false;
                    }
                }).collect(Collectors.toList());

        List<LocalTime> validTimes = rowsWithLatestDate.stream()
                .map(row -> {
                    try {
                        return LocalTime.parse(row.get(timeIdx), timeFormatter);
                    } catch (Exception e) {
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());

        if (validTimes.isEmpty())
            throw new IllegalStateException("No valid session start times found for date: " + latestDate);

        LocalTime latestTime = Collections.max(validTimes);
        String latestDateStr = latestDate.format(dateFormatter);
        String latestTimeStr = latestTime.format(timeFormatter);

        // Debug
        System.out.println("Stored latest date/time:");
        System.out.println("  latest date : " + latestDateStr);
        System.out.println("  latest time : " + latestTimeStr);

        latestSessionRows = sheetData.stream().skip(1)
                .filter(row -> row.size() > Math.max(dateIdx, timeIdx))
                .filter(row -> {
                    try {
                        boolean dateMatch = row.get(dateIdx).equals(latestDateStr);
                        boolean timeMatch = false;
                        if (dateMatch) {
                            LocalTime rowTime = LocalTime.parse(row.get(timeIdx), timeFormatter);
                            LocalTime latestTimeParsed = LocalTime.parse(latestTimeStr, timeFormatter);
                            timeMatch = (rowTime.getHour() == latestTimeParsed.getHour()) &&
                                    (rowTime.getMinute() == latestTimeParsed.getMinute());
                        }
                        return dateMatch && timeMatch;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .map(row -> Arrays.stream(valueIndices)
                        .mapToObj(idx -> row.get(idx).trim()).collect(Collectors.toList()))
                .collect(Collectors.toList());

        System.out.println("Picked rows for latest session (Date + Hour:Minute match):");
        for (List<String> row : latestSessionRows) {
            System.out.println(row);
        }
        System.out.println("Total matching rows found: " + latestSessionRows.size());
        return latestSessionRows;
    }

    // Utility getters if needed
    public static List<List<String>> getSheetData() { return sheetData; }
    public static List<String> getHeaders() { return headers; }
    public static List<List<String>> getLatestSessionRows() { return latestSessionRows; }
}
