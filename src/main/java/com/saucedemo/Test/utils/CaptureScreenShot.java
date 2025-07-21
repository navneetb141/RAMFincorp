package com.saucedemo.Test.utils;

import java.io.File;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;

public class CaptureScreenShot {

	private static ThreadLocal<List<File>> stepScreenshots = ThreadLocal.withInitial(ArrayList::new);

	public static File captureScreenshot(WebDriver driver, String scenarioName, String status) {
		try {
			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
			String fileName = scenarioName + "_" + status + "_" + timestamp + ".png";
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File destination = new File("./Screenshots/" + fileName);
			FileUtils.copyFile(screenshot, destination);
			System.out.println("Screenshot saved at: " + destination.getAbsolutePath());

			// ✅ Track STEP screenshots
			if ("STEP".equalsIgnoreCase(status)) {
				stepScreenshots.get().add(destination);
			}

			return destination;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to capture screenshot for: " + scenarioName);
			return null;
		}
	}

	public static void passedScreenshotAttacheToAllure(WebDriver driver, String scenarioName) {
		try {
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			Allure.addAttachment("Passed Screenshot - " + scenarioName, new ByteArrayInputStream(screenshot));
			System.out.println("Passed screenshot attached to allure for: " + scenarioName);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to attach passed screenshot for: " + scenarioName);
		}
	}

	public static void failedScreenshotAttacheToAllure(WebDriver driver, String scenarioName) {
		try {
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			Allure.addAttachment("Failed Screenshot - " + scenarioName, new ByteArrayInputStream(screenshot));
			System.out.println("Failed screenshot attached to allure for: " + scenarioName);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to attach failed screenshot for: " + scenarioName);
		}
	}

	public static List<File> getStepScreenshots() {
		return stepScreenshots.get();
	}

	public static void clearStepScreenshots() {
		stepScreenshots.remove();
	}
}
