package com.qa.keyword.base;

import com.qa.keyword.engine.ExecuteEngine;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;


public class Base {

    public WebDriver webDriver;
    public Properties properties;
    File chromepath = new File("D:\\artdesktopkeyworddriven\\src\\test\\resources\\drivers\\chromedriver.exe");
    File configpropertyPath =  new File("D:\\artdesktopkeyworddriven\\src\\main\\java\\com\\qa\\keyword\\config\\config.properties");

    public ExtentReports extentReports;
    public ExtentTest extentTest;

    public WebDriver init_driver(String browserName)
    {
        if(browserName.equalsIgnoreCase("chrome"))
        {
            System.setProperty("webdriver.chrome.driver", "D:\\artdesktopkeyworddriven\\src\\test\\resources\\drivers\\chromedriver.exe");
            if (properties.getProperty("headless").equals("yes")) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                webDriver = new ChromeDriver(chromeOptions);

            }else
            {
                webDriver = new ChromeDriver();
                webDriver.manage().window().maximize();
                webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

            }
        }

        return webDriver;
    }

    public  Properties init_properties()
    {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(configpropertyPath);

            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return properties;
    }



    public void startExtentreport()
    {
        extentReports = new ExtentReports(System.getProperty("user.dir")+"/Reports/ExtentReport.html", true);
        extentReports.addSystemInfo("Host Name", "Winwos mac");
        extentReports.addSystemInfo("Username","Harish");
        extentReports.addSystemInfo("browsername" , ExecuteEngine.browsername);
        extentReports.addSystemInfo("platformName" , ExecuteEngine.platformName);
        extentReports.addSystemInfo("browserVersion" , ExecuteEngine.browserVersion);

    }

    public void endExtentreport()
    {
        extentReports.close();
        extentReports.flush();
    }

    public String getScreenShotOnFailure(String screenshotname)
    {
        String datename = new SimpleDateFormat("yyyyDDMMhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot)webDriver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir")+"/FailedScreenShot"+ screenshotname + datename + ",png";
        File finaldestination = new File(destination);
        try {
            FileUtils.copyFile(source, finaldestination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination;
    }



    public void getTestResult(ITestResult iTestResult)
    {
        if(iTestResult.getStatus() == ITestResult.FAILURE)
        {
            extentTest.log(LogStatus.FAIL, "Test Failed "+ iTestResult.getName());
            extentTest.log(LogStatus.FAIL, "Test Case Failed " + iTestResult.getThrowable());

            String screenshotName = getScreenShotOnFailure(iTestResult.getName());
            extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotName));
        } else if(iTestResult.getStatus() == ITestResult.SKIP)
        {
            extentTest.log(LogStatus.SKIP, "Test case got skipped " + iTestResult.getName());
        } else if(iTestResult.getStatus() == ITestResult.SUCCESS)
        {
            extentTest.log(LogStatus.PASS , "Test case passed " + iTestResult.getName());
        }
            extentReports.endTest(extentTest);
    }

    public void startTest(String testName, String testDescription)
    {
        extentReports.startTest(testName,testDescription);
    }
}




