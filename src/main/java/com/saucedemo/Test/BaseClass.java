package com.saucedemo.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.saucedemo.Test.utils.ReadConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    public static WebDriver driver;
    
    public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();

    public static synchronized WebDriver getDriver() {
        return tdriver.get();
    }
     static com.saucedemo.Test.utils.ReadConfig readconfig = new ReadConfig();
    public static String browser = readconfig.openBrowser();
    public static String qaURL = readconfig.getApplicationUrl();
    public static String qaUsername = readconfig.getUserName();
    public static String qaPassword = readconfig.getPassword();
    public static void setUp(boolean b) {

        try {

            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().clearDriverCache().setup();

                    ChromeOptions chromeOptions = new ChromeOptions();

                    // Visual scaling
                    chromeOptions.addArguments("--force-device-scale-factor=1.2");

                    // Use a fresh, temporary user profile (prevents saved credentials)
                    chromeOptions.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir") + "/tempChromeProfile_" + System.currentTimeMillis());

                    // Suppress automation controlled info bar
                    //chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                   // chromeOptions.setExperimentalOption("useAutomationExtension", false);

                    // Disable all password services and leak detection
                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.password_manager_leak_detection", false);
                    chromeOptions.setExperimentalOption("prefs", prefs);

                    driver = new ChromeDriver(chromeOptions);
                    tdriver.set(driver);
                    break;



                case "firefox":
                    WebDriverManager.firefoxdriver().clearDriverCache().setup();
                    driver = new FirefoxDriver();
                    tdriver.set(driver);
                    break;

                case "edge":
                    WebDriverManager.edgedriver().clearDriverCache().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--force-device-scale-factor=1.2");
                    driver = new EdgeDriver(edgeOptions);
                    tdriver.set(driver);
                    break;

                case "safari":
                    driver = new SafariDriver();
                    tdriver.set(driver);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

            PageFactory.initElements(driver, new BaseClass());

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

        
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage());
        }
    }

}
