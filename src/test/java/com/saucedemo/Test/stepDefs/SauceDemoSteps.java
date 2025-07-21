package com.saucedemo.Test.stepDefs;

import com.saucedemo.Test.pages.SauceDemoPage;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class SauceDemoSteps {

    SauceDemoPage saucePage = new SauceDemoPage();

    @Given("User launches the SauceDemo website")
    public void user_launches_site() {
        saucePage.launchSite();
    }

    @When("User log in to the application")
    public void user_logs_in() {
        saucePage.login();
    }

    @Then("User should be navigated to the inventory page")
    public void validate_inventory_page() {
        Assert.assertTrue(saucePage.isInventoryPage());
    }

    @When("User adds Sauce Labs Backpack and Sauce Labs Bolt T-Shirt to the cart")
    public void add_products() {
        saucePage.addProductToCart();
    }

    @When("User navigates to the cart")
    public void navigate_to_cart() {
        saucePage.goToCart();
    }

    @Then("Both products should be present in the cart with quantity 1")
    public void validate_cart_products() {
        Assert.assertTrue(saucePage.verifyCartContents());
    }

    @When("User proceeds to checkout with first name {string}, last name {string}, and zip {string}")
    public void proceed_to_checkout(String first, String last, String zip) {
        saucePage.checkout(first, last, zip);
    }

    @Then("User should see item total, tax, and total as sum of item total and tax")
    public void validate_totals() {
        Assert.assertTrue(saucePage.verifyTotalCalculation());
    }

    @When("User clicks Finish")
    public void click_finish() {
        saucePage.finishCheckout();
    }

    @Then("Confirmation message {string} should be displayed")
    public void verify_confirmation(String message) {
        Assert.assertEquals(saucePage.getConfirmationMessage(), message);
    }

    @When("User logs out from the product page")
    public void logout() {
        saucePage.logout();
    }

    @Then("User should be navigated back to the login page")
    public void back_to_login() {
        Assert.assertTrue(saucePage.isLoginPage());
    }
}
