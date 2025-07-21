package com.saucedemo.Test.utils;
import io.qameta.allure.Allure;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ScenarioOutputCapture {
    private static ThreadLocal<ByteArrayOutputStream> buffer = new ThreadLocal<>();
    private static ThreadLocal<PrintStream> originalOut = new ThreadLocal<>();
    private static ThreadLocal<PrintStream> originalErr = new ThreadLocal<>();

    public static void startCapture() {
        originalOut.set(System.out);
        originalErr.set(System.err);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        buffer.set(baos);

        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        System.setErr(ps);  // Redirect error stream too
    }

    public static void stopAndAttachToAllure(String scenarioName) {
        try {
            System.out.flush();
            System.err.flush();
            System.setOut(originalOut.get());
            System.setErr(originalErr.get());

            String output = buffer.get().toString();
            Allure.addAttachment("Console Output: " + scenarioName, "text/plain", output);

        } catch (Exception e) {
            System.err.println("Failed to attach output to Allure: " + e.getMessage());
        } finally {
            buffer.remove();
            originalOut.remove();
            originalErr.remove();
        }
    }
}
