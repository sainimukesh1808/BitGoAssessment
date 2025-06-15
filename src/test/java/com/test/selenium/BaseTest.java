package com.test.selenium;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.code.selenium.ConfigManager;
import com.code.selenium.utils.Constants;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	private static final Logger logger = LogManager.getLogger(BaseTest.class);
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod
    public void setUp() {
    	try {
    		String browser = ConfigManager.getProperty("browser");
    		logger.info("Launching browser: " + browser);
    		
    		if (browser.equalsIgnoreCase("chrome")) {
    			WebDriverManager.chromedriver().setup();
	            ChromeOptions options = new ChromeOptions();
	            options.addArguments("--start-maximized"); // Open in maximized mode
	            options.addArguments("--incognito"); // Run in incognito mode
	            options.setPageLoadStrategy(PageLoadStrategy.NORMAL); // Ensures full page load
//	            PageLoadStrategy.NORMAL --Waits until the entire page loads (including scripts and styles).
//	            PageLoadStrategy.EAGER -- Waits only until the DOM is fully loaded but ignores images and styles.
//	            PageLoadStrategy.NONE -- Selenium does not wait for the page to load.
//	            							✅ Useful if you want to handle waits manually using WebDriverWait.
	//            options.addArguments("--disable-popup-blocking"); // Disable popups
	//            options.addArguments("--disable-notifications"); // Block notifications
	//            options.addArguments("--headless"); // Run tests in headless mode
	//            options.addArguments("--remote-allow-origins=*"); // Fix cross-origin issues
	            
	            
	            driver.set(new ChromeDriver(options));
    		} else if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--start-maximized");
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL); // Ensures full page load
                driver.set(new FirefoxDriver(options));
            } else {
                throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            logger.info(browser + " initialized successfully");
            
            // Set base URL dynamically
            getDriver().get(Constants.BASE_URL);
            
            logger.info("Navigating to: " + Constants.BASE_URL);
            
    	} catch (Exception e) {
    		logger.error("Exception occurred during setup", e);
            tearDown();
            throw e;
        }
        
    }

    public void takeScreenshot(String testName) {
        File screenshotDir = new File("screenshots");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();  // Ensure folder is created
        }
        
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        File destFile = new File(screenshotDir, testName + ".png");
        try {
            FileHandler.copy(srcFile, destFile);
            logger.info("Screenshot saved at: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to save screenshot", e);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
        	logger.info("Closing WebDriver session");
            getDriver().quit();
            driver.remove();
        }
    }
}
