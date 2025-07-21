package com.saucedemo.Test.pages;

import com.saucedemo.Test.BaseClass;
import com.saucedemo.Test.utils.HighlightElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SauceDemoPage extends com.saucedemo.Test.BaseClass {

	WebDriver driver;
	WebDriverWait wait;

	public SauceDemoPage() {
		this.driver = BaseClass.getDriver();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

		public void launchSite() {
			driver.get(qaURL);
		}

		public void login() {
			WebElement uName = driver.findElement(By.cssSelector("#user-name"));
			WebElement pass = driver.findElement(By.cssSelector("#password"));
			WebElement logibBtn = driver.findElement(By.cssSelector("#login-button"));

			HighlightElement.highlightElement(driver,uName);
			uName.sendKeys(qaUsername);
			HighlightElement.highlightElement(driver,pass);
			pass.sendKeys(qaPassword);
			HighlightElement.highlightElement(driver,logibBtn);
			logibBtn.click();
		}

		public boolean isInventoryPage() {
			return driver.getCurrentUrl().contains("inventory.html");
		}

		public void addProductToCart() {
			WebElement prod1 = driver.findElement(By.cssSelector("#add-to-cart-sauce-labs-backpack"));
			WebElement prod2=driver.findElement(By.cssSelector("#add-to-cart-sauce-labs-bolt-t-shirt"));
			HighlightElement.highlightElement(driver,prod1);
			prod1.click();
			HighlightElement.highlightElement(driver,prod2);
			prod2.click();
		}

		public void goToCart() {
			WebElement cart = driver.findElement(By.cssSelector(".shopping_cart_link"));
			HighlightElement.highlightElement(driver,cart);
			cart.click();
		}

		public boolean verifyCartContents() {
			boolean backpack = driver.getPageSource().contains("Sauce Labs Backpack");
			boolean tshirt = driver.getPageSource().contains("Sauce Labs Bolt T-Shirt");
			boolean qty1 = driver.findElements(By.className("cart_quantity"))
					.stream().allMatch(e -> e.getText().equals("1"));
			return backpack && tshirt && qty1;
		}

		public void checkout(String fname, String lname, String zip) {
			driver.findElement(By.cssSelector("#checkout")).click();
			driver.findElement(By.cssSelector("#first-name")).sendKeys(fname);
			driver.findElement(By.cssSelector("#last-name")).sendKeys(lname);
			driver.findElement(By.cssSelector("#postal-code")).sendKeys(zip);
			driver.findElement(By.cssSelector("#continue")).click();
		}

		public boolean verifyTotalCalculation() {
			String itemTotal = driver.findElement(By.cssSelector(".summary_subtotal_label")).getText().replace("Item total: $", "");
			String tax = driver.findElement(By.cssSelector(".summary_tax_label")).getText().replace("Tax: $", "");
			String total = driver.findElement(By.cssSelector(".summary_total_label")).getText().replace("Total: $", "");

			double itemVal = Double.parseDouble(itemTotal);
			double taxVal = Double.parseDouble(tax);
			double expectedTotal = itemVal + taxVal;

			return Math.abs(expectedTotal - Double.parseDouble(total)) < 0.01;
		}

		public void finishCheckout() {
			driver.findElement(By.cssSelector("#finish")).click();
		}

		public String getConfirmationMessage() {
			return driver.findElement(By.cssSelector(".complete-header")).getText().trim();
		}

		public void logout() {
			driver.findElement(By.cssSelector("#react-burger-menu-btn")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#logout_sidebar_link"))).click();
		}

	public boolean isLoginPage() {
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Checking Login Page - Current URL: " + currentUrl);
		return currentUrl.startsWith("https://www.saucedemo.com");
	}

}


