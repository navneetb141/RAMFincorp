package com.saucedemo.Test.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HighlightElement {
	 // Utility method to highlight an element
    public static void highlightElement(WebDriver driver, WebElement element) {
        if (driver == null || element == null) {
            throw new IllegalArgumentException("Driver or element is null.");
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Add a red border to the element
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }
}
