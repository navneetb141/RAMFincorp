package com.saucedemo.Test.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ResourceOnAndOff {

    // Generic method to set dropdown value with retry logic
    private static void setDropdownValue(WebDriver driver, String dropdownXPath, String desiredValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        int attempts = 0;
        boolean success = false;

        while (attempts < 2 && !success) {
            try {
                attempts++;

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dropdownXPath)));

                WebElement dropdown = driver.findElement(By.xpath(dropdownXPath));
                ScrollDownToElement.scrollToElement(driver, dropdown);
                HighlightElement.highlightElement(driver, dropdown);

                // Re-locate just before Select
                dropdown = driver.findElement(By.xpath(dropdownXPath));
                Select select = new Select(dropdown);
                String currentValue = select.getFirstSelectedOption().getText().trim();

                if (!currentValue.equalsIgnoreCase(desiredValue)) {
                    select.selectByVisibleText(desiredValue);
                    System.out.println("Dropdown set to: " + desiredValue);
                } else {
                    System.out.println("Dropdown is already: " + desiredValue);
                }

                WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Save']")
                ));
                ScrollDownToElement.scrollToElement(driver, saveBtn);
                HighlightElement.highlightElement(driver, saveBtn);
                saveBtn.click();

                success = true;

            } catch (org.openqa.selenium.StaleElementReferenceException staleEx) {
                System.err.println("StaleElementReferenceException on dropdown. Retrying... Attempt: " + attempts);
            } catch (Exception e) {
                System.err.println("Failed to set dropdown to " + desiredValue + " for XPath: " + dropdownXPath);
                e.printStackTrace();
                break;
            }
        }

        if (!success) {
            System.err.println("Could not set dropdown to " + desiredValue + " after retries.");
        }
    }

    // Public method to set dropdown to "On"
    public static void setDropdownOn(WebDriver driver, String dropdownXPath) {
        setDropdownValue(driver, dropdownXPath, "On");
    }

    // Public method to set dropdown to "Off"
    public static void setDropdownOff(WebDriver driver, String dropdownXPath) {
        setDropdownValue(driver, dropdownXPath, "Off");
    }
}
