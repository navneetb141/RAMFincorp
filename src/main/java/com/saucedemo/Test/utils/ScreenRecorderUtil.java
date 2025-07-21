package com.saucedemo.Test.utils;

import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenRecorderUtil {
    private static final String VIDEO_DIRECTORY = "./ScreenRecords";
    private static String currentScenarioName;
    private static String currentMp4Path;
    private static Process ffmpegProcess;

    public static void startRecording(String scenarioName) throws Exception {
        System.out.println("Starting recording for scenario: " + scenarioName);

        File directory = new File(VIDEO_DIRECTORY);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new RuntimeException("Failed to create video directory: " + VIDEO_DIRECTORY);
        }

        currentScenarioName = scenarioName.replaceAll("[^a-zA-Z0-9_-]", "_").trim();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        currentMp4Path = new File(directory, currentScenarioName + "_" + timestamp + ".mp4").getAbsolutePath();

        System.out.println("MP4 Path: " + currentMp4Path);

        String ffmpegPath = "C:\\RMAutomation_Selenium\\ffmpeg\\bin\\ffmpeg.exe";

        boolean isHeadless = GraphicsEnvironment.isHeadless();
        String[] command;

        if (isHeadless) {
            System.out.println("Running in headless mode, using dummy display.");
            command = new String[]{
                    ffmpegPath,
                    "-y",
                    "-f", "gdigrab",
                    "-framerate", "30",
                    "-video_size", "1920x1080",
                    "-offset_x", "0", "-offset_y", "0",
                    "-i", "desktop",
                    "-pix_fmt", "yuv420p",
                    "-c:v", "libx264",
                    "-preset", "ultrafast",
                    currentMp4Path
            };
        } else {
            System.out.println("Running in GUI mode, capturing normal display.");
            command = new String[]{
                    ffmpegPath,
                    "-y",
                    "-f", "gdigrab",
                    "-framerate", "30",
                    "-video_size", "1920x1080",
                    "-i", "desktop",
                    "-pix_fmt", "yuv420p",
                    "-c:v", "libx264",
                    "-preset", "ultrafast",
                    currentMp4Path
            };
        }

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        ffmpegProcess = pb.start();

        // Drain FFmpeg logs quietly (no binary garbage in logs)
        new Thread(() -> {
            try (InputStream is = ffmpegProcess.getInputStream()) {
                while (is.read() != -1) {
                    // Discard output silently
                }
            } catch (IOException e) {
                System.err.println("Error draining FFmpeg logs: " + e.getMessage());
            }
        }).start();

        Thread.sleep(3000);
        System.out.println("Recording started: " + currentMp4Path);
    }

    public static void stopRecording() {
        try {
            if (ffmpegProcess != null && ffmpegProcess.isAlive()) {
                OutputStream os = ffmpegProcess.getOutputStream();
                if (os != null) {
                    os.write("q\n".getBytes());
                    os.flush();
                }
                ffmpegProcess.waitFor();
                System.out.println(" FFmpeg process stopped.");
            }
        } catch (IOException e) {
            System.err.println("Error while stopping FFmpeg: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("FFmpeg stop interrupted.");
        }
    }

    public static void attachRecordingToAllure(Scenario scenario) {
        try {
            File videoFile = new File(currentMp4Path);
            if (videoFile.exists() && scenario != null) {
                try (InputStream is = new FileInputStream(videoFile)) {
                    Allure.addAttachment("Test Video", "video/mp4", is, ".mp4");
                    System.out.println("MP4 file attached to Allure report: " + currentMp4Path);
                }
            } else {
                System.err.println("MP4 file not found " + currentMp4Path);
            }
        } catch (Throwable t) {
            System.err.println("Skipped Allure video attachment (probably due to test already finished): " + t.getMessage());
        }
    }
}
