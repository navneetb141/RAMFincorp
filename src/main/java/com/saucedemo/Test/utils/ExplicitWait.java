package com.saucedemo.Test.utils;

import java.time.Duration;

import com.saucedemo.Test.BaseClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class ExplicitWait extends BaseClass {


	// Wait for element to be visible
	public static void waitForVisibility(WebElement element, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	// Wait for element to be clickable
	public static void waitForClickable(WebElement element, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	// Wait for element to be invisible
	public static void waitForInvisibility(WebElement element, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	// Wait for text to be present in element
	public static void waitForText(WebElement element, String text, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}

	// Wait for attribute value to match
	public static void waitForAttribute(WebElement element, String attribute, String value, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
		wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
	}

	public static void waitForPageToLoad(WebDriver driver, int timeoutSeconds) {
		new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds)).until(
				webDriver -> ((JavascriptExecutor) webDriver)
						.executeScript("return document.readyState").equals("complete"));
	}

	public static void waitForJQueryToFinish(WebDriver driver, int timeoutSeconds) {
		new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds)).until(
				webDriver -> {
					try {
						return (Boolean) ((JavascriptExecutor) webDriver)
								.executeScript("return !!window.jQuery && jQuery.active === 0");
					} catch (Exception e) {
						return true; // jQuery not present
					}
				});
	}

	public static void waitForAngularToFinish(WebDriver driver, int timeoutSeconds) {
		new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds)).until(
				webDriver -> {
					try {
						String script =
								"return (window.angular !== undefined) && " +
										"(angular.element(document).injector() !== undefined) && " +
										"(angular.element(document.body).injector().get('$http').pendingRequests.length === 0)";
						return Boolean.TRUE.equals(((JavascriptExecutor) webDriver).executeScript(script));
					} catch (Exception e) {
						return true; // Angular not present
					}
				});
	}
	
		
	
}
