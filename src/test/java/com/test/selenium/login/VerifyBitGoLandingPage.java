package com.test.selenium.login;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.code.selenium.pages.BitGoPage;
import com.test.selenium.BaseTest;
import com.test.selenium.utils.TestListener;


@Listeners(TestListener.class)
public class VerifyBitGoLandingPage extends BaseTest{
	
	
	
	@Test()
    public void verifyTransactionalText() {
		String actualTransactionalText = "25 of 2875 Transactions";
		WebDriver driver = getDriver();
		BitGoPage bitGoPage = new BitGoPage(driver);
		
		String expectedTransactionalText = bitGoPage.getTransactionsText(driver);

//        LoginPage loginPage = new LoginPage(getDriver());
//        loginPage.login("student", "Password123");
        Assert.assertEquals(actualTransactionalText, expectedTransactionalText);
    }
	
	
	@Test()
    public void printTransactionsHaveOneInputAndTwoOutput() {
		WebDriver driver = getDriver();
		BitGoPage bitGoPage = new BitGoPage(driver);
		


		 Map<String, WebElement> matched = bitGoPage.getMatchingTransactionDetails();

	        System.out.println("Matching Transactions (1 input, 2 outputs): " + matched.size());

	        int count = 1;
	        for (Map.Entry<String, WebElement> entry : matched.entrySet()) {
	            System.out.println("Transaction #" + count++);
	            System.out.println("Transaction Text (from <a>): " + entry.getKey());
	            // Optional: print full transaction details
	            // System.out.println(entry.getValue().getText());
	        }
    }

}
