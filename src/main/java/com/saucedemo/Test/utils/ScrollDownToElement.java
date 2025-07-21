package com.saucedemo.Test.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScrollDownToElement {
	// Scroll down until the element is visible
	public static void scrollToElement(WebDriver driver,WebElement element) {
	    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
	    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	    System.out.println("Scrolled to element: " + element);
	}
}
