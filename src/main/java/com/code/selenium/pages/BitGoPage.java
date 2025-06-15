package com.code.selenium.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BitGoPage extends Page{
//	 ✅ BaseTest initializes WebDriver → Runs browser session for each test.
//	 ✅ LoginTest calls new LoginPage(getDriver()) → Passes the existing WebDriver to LoginPage.
//	 ✅ LoginPage does NOT create a new WebDriver → Uses the passed WebDriver to interact with elements.
	private static final Logger logger = LogManager.getLogger(BitGoPage.class);
	
    @FindBy(xpath = "//div[@class='transactions']/h3")
    private WebElement transactions;
    
    @FindBy(xpath = "//div[@class='transaction-box']")
    private List<WebElement> transactionBoxes;

    
    public BitGoPage(WebDriver driver) {
        super(driver);
        logger.info("LoginPage initialized");
    }
    
    public String getTransactionsText(WebDriver driver) {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='transactions']/h3")));
    	return transactions.getText();
    }
    
    public List<WebElement> getAllTransactions( WebDriver driver) {
    	return driver.findElements((By) transactionBoxes);
    }
    
    public List<WebElement> getInputCount( WebDriver driver) {
    	return driver.findElements(By.xpath("//div[@class='transaction-box']//div[@class='ins-and-outs']//div[@class='vins']"));
    }
    
    public Map<String, WebElement> getMatchingTransactionDetails(){
    	Map<String, WebElement> matching = new LinkedHashMap<>();
    	for (WebElement tx : transactionBoxes) {
    		try {
                List<WebElement> inputs = tx.findElements(By.cssSelector(".vins .vin"));
                List<WebElement> outputs = tx.findElements(By.cssSelector(".vouts .vout"));

                int outputCount = 0;
                for (WebElement output : outputs) {
                    String html = output.getAttribute("innerHTML");
                    if (html.contains("<br>")) {
                        outputCount += html.split("<br>").length;
                    } else {
                        outputCount += 1;
                    }
                }

                if (inputs.size() == 1 && outputCount == 2) {
                    // Find the <a> inside this transaction
                    WebElement anchor = tx.findElement(By.cssSelector("a[href]"));
                    String txText = anchor.getText().trim();

                    matching.put(txText, tx);  // Store ID + transaction WebElement
                }

            } catch (Exception e) {
                System.out.println("Error processing transaction: " + e.getMessage());
            }
        }

        return matching;
    }
    
}
