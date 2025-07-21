package com.saucedemo.Test.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ReadActivityMetaData {

    private static List<List<String>> latestSessionRows = new ArrayList<>();

    public static List<List<String>> extractLatestSessionValues(String sheetName, String extension, long downloadStartTime) {
        // Step 1: Load the file and sheet
        ReadDownloadedXlsx.openLatestXlsxFile(sheetName, extension, downloadStartTime);
        ReadDownloadedXlsx.accessTheSheet(sheetName);

        List<List<String>> sheetData = ReadDownloadedXlsx.getSheetData();
        List<String> headers = ReadDownloadedXlsx.getHeaders();

        int dateIdx = headers.indexOf("Session Start Date");
        int timeIdx = headers.indexOf("Session Start Time");
        int actionIdx = headers.indexOf("Action");
        int attributeIdx = headers.indexOf("Attribute");
        int attributeValueIdx = headers.indexOf("AttributeValue");

        if (dateIdx == -1 || timeIdx == -1 || actionIdx == -1 || attributeIdx == -1 || attributeValueIdx == -1) {
            throw new IllegalStateException("Required columns not found: 'Session Start Date', 'Session Start Time', 'Action', 'Attribute', 'AttributeValue'");
        }

        // Step 2: Find latest date and time
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm:ss a");

        List<LocalDate> validDates = sheetData.stream().skip(1)
            .map(row -> {
                try {
                    return LocalDate.parse(row.get(dateIdx), dateFormatter);
                } catch (Exception e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        if (validDates.isEmpty()) {
            throw new IllegalStateException("No valid session start dates found.");
        }

        LocalDate latestDate = Collections.max(validDates);

        List<List<String>> rowsWithLatestDate = sheetData.stream().skip(1)
            .filter(row -> {
                try {
                    return LocalDate.parse(row.get(dateIdx), dateFormatter).equals(latestDate);
                } catch (Exception e) {
                    return false;
                }
            })
            .collect(Collectors.toList());

        List<LocalTime> validTimes = rowsWithLatestDate.stream()
            .map(row -> {
                try {
                    return LocalTime.parse(row.get(timeIdx), timeFormatter);
                } catch (Exception e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        if (validTimes.isEmpty()) {
            throw new IllegalStateException("No valid session start times found for date: " + latestDate);
        }

        LocalTime latestTime = Collections.max(validTimes);
        String latestDateStr = latestDate.format(dateFormatter);
        String latestTimeStr = latestTime.format(timeFormatter);

        System.setProperty("latest.session.date", latestDateStr);
        System.setProperty("latest.session.time", latestTimeStr);

        System.out.println("Stored latest date/time:");
        System.out.println("  latest date : " + latestDateStr);
        System.out.println("  latest time : " + latestTimeStr);

        // Step 3: Collect matching rows where Date matches and Hour + Minute matches
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
            .map(row -> Arrays.asList(
                row.get(actionIdx).trim(),
                row.get(attributeIdx).trim(),
                row.get(attributeValueIdx).trim()
            ))
            .collect(Collectors.toList());

        // Step 4: Debug log the picked rows
        System.out.println("✅ Picked rows for latest session (Date + Hour:Minute match):");
        for (List<String> row : latestSessionRows) {
            System.out.println("Action: " + row.get(0) + " | Attribute: " + row.get(1) + " | AttributeValue: " + row.get(2));
        }
        System.out.println("✅ Total matching rows found: " + latestSessionRows.size());
        return sheetData;
    }

    // Getter to return the picked session rows
    public static List<List<String>> getLatestSessionRows() {
        if (latestSessionRows == null || latestSessionRows.isEmpty()) {
            throw new IllegalStateException("Latest session rows not extracted yet. Please call extractLatestSessionValues() first.");
        }
        return latestSessionRows;
    }
}
