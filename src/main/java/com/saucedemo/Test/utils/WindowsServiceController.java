package com.saucedemo.Test.utils;

import java.io.*;

public class WindowsServiceController {

    public static void RmServiceRestart() {
        try {
            // Build absolute path to the .bat file
            File batFile = new File("Batch/Restart_ResearchMonitor_Service.bat");

            if (!batFile.exists()) {
                System.err.println(" Batch file not found at: " + batFile.getAbsolutePath());
                return;
            }

            String absolutePath = batFile.getAbsolutePath();

            // Use cmd to run the batch file
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", absolutePath);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Read and print the output from the batch file
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println(" Output from batch file:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println(" Batch file executed with exit code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
