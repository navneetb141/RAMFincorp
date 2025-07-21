package com.saucedemo.Test.hooks;

import com.saucedemo.Test.BaseClass;
import com.saucedemo.Test.utils.CaptureScreenShot;
import com.saucedemo.Test.utils.ScreenRecorderUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class MainHook extends BaseClass {


    @Before
    public void setUp(Scenario scenario) {
        if (!scenario.getSourceTagNames().contains("@WithExtension")) {
            // ScenarioOutputCapture.startCapture(); //Start capturing logs
            setUp(false); // Initialize WebDriver without extension

            try {
                String sanitizedScenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9_-]", "_").trim();
                if (sanitizedScenarioName.isEmpty()) {
                    throw new IllegalArgumentException("Scenario name is invalid after sanitization.");
                }
                com.saucedemo.Test.utils.ScreenRecorderUtil.startRecording(sanitizedScenarioName);
                System.out.println("Recording started for scenario: " + sanitizedScenarioName);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to start recording for scenario: " + scenario.getName(), e);
            }
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        String scenarioName = scenario.getName();

        try {
          //  ScenarioOutputCapture.stopAndAttachToAllure(scenarioName); //Attach logs to Allure

            System.out.println("Stopping recording for scenario: " + scenarioName);
            ScreenRecorderUtil.stopRecording();
            ScreenRecorderUtil.attachRecordingToAllure(scenario);
        } catch (IllegalStateException e) {
            System.err.println("Recording was not initialized. Skipping stop.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred during afterScenario processing.");
        } finally {
            // Capture and attach screenshots
            if (scenario.isFailed()) {
                System.out.println("Scenario failed: " + scenarioName);
                CaptureScreenShot.captureScreenshot(BaseClass.driver, scenarioName, "FAILED");
                CaptureScreenShot.failedScreenshotAttacheToAllure(BaseClass.driver, scenarioName);
            } else {
                System.out.println("Scenario passed: " + scenarioName);
                CaptureScreenShot.captureScreenshot(BaseClass.driver, scenarioName, "PASSED");
                CaptureScreenShot.passedScreenshotAttacheToAllure(BaseClass.driver, scenarioName);
            }
            List<File> stepScreens = CaptureScreenShot.getStepScreenshots();
            for (File file : stepScreens) {
                if (file != null && file.exists()) {
                    try {
                        Allure.addAttachment("Step Screenshot: " + file.getName(), "image/png", new FileInputStream(file), ".png");
                    } catch (IOException e) {
                        System.err.println("Failed to attach step screenshot: " + e.getMessage());
                    }
                }
            }
            CaptureScreenShot.clearStepScreenshots();


            // Close the browser and terminate session
            if (BaseClass.driver != null) {
                try {
                    BaseClass.driver.quit();
                    BaseClass.driver = null; // Reset driver for the next scenario
                    System.out.println("Browser session terminated.");
                } catch (Exception e) {
                    System.err.println("Error closing browser: " + e.getMessage());
                }
            }
        }
    }
}
