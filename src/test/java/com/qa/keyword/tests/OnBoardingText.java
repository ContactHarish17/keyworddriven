package com.qa.keyword.tests;

import com.qa.keyword.base.Base;
import com.qa.keyword.engine.ExecuteEngine;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.qameta.allure.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class OnBoardingText {

    public ExecuteEngine executeEngine = new ExecuteEngine();
    Base base = new Base();



//    @BeforeTest
//    public void setUP()
//        {
//            base.startExtentreport();
//        }

    @Test(priority = 1)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test Case Description : verifying the new partner on boarding")
    @Story("Story Name: OnBoarding New Partner")
    public void OnBoadringPartnerTest()
    {
        //base.startTest("OnBoarding", "Test to check the new partner On boaring");
        executeEngine.startExecution("Login");

    }

//    @Test(priority = 2)
//    @Severity(SeverityLevel.BLOCKER)
//    @Description("Test Case Description : doing a saudi check for new partner")
//    @Story("Story Name: Saudi Check")
//
//    public void SaudiCheckTest()
//    {
//        //base.startTest("OnBoarding", "Test to check the new partner On boaring");
//        logger.info("Inside the on boarding test");
//        executeEngine.startExecution("SaudiCheck");
//
//    }

//    @Test(priority = 3)
//    @Severity(SeverityLevel.BLOCKER)
//    @Description("Test Case Description : adding contacts after partner on boarding")
//    @Story("Story Name: Adding New contacts")
//    public void AddingContactTest()
//    {
//        //base.startTest("AddingContact", "Test to add new contacts");
//        executeEngine.startExecution("AddingContact");
//
//    }

    @Test(priority = 4)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test Case Description : converting the added contact as prospect")
    @Story("Story Name: Adding Contact as Prospect")
    public void ConvertingContactASProspectTest()
    {
       // base.startTest("ConvertingasProspect", "Test to convert contact as Prospect");
        executeEngine.startExecution("EditingAContact");
    }


//    @AfterMethod
//    public void publishResult(ITestResult result)
//    {
//        base.getTestResult(result);
//    }
//
//    @AfterTest
//    public void tearDown()
//    {
//        base.endExtentreport();
//    }
}
