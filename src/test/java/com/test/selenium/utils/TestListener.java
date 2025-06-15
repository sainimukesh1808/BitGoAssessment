package com.test.selenium.utils;

//TestListener.javaimport org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.test.selenium.BaseTest;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.IExecutionListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestListener implements IExecutionListener, ISuiteListener, ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test Started: " + result.getName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: " + result.getName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: " + result.getName());
        if (result.getInstance() instanceof BaseTest) {
            BaseTest baseTest = (BaseTest) result.getInstance();
            baseTest.takeScreenshot(result.getName());
        } else {
            logger.warn("Screenshot skipped: Test class does not extend BaseTest");
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test Skipped: " + result.getName());
    }
    
    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Suite Execution Started: " + context.getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite Execution Finished: " + context.getName());
    }
    
    @Override
    public void onStart(ISuite suite) {
        logger.info("Test Suite Started: " + suite.getName());
    }
    
    @Override
    public void onFinish(ISuite suite) {
        logger.info("Test Suite Finished: " + suite.getName());
    }
    
    @Override
    public void onExecutionStart() {
        logger.info("Test Execution Started");
    }
    
    @Override
    public void onExecutionFinish() {
        logger.info("Test Execution Finished");
    }
}