package com.qa.keyword.engine;

import com.qa.keyword.base.Base;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.testng.*;

public class ExecuteEngine {

    public WebDriver webDriver;
    public Properties properties;
    public static Workbook workbook;
    public static Sheet sheet;
    public Base base;
    public WebElement element;
    WebDriverWait wait = null;
    Actions actions;

    public static String browsername=null;
    public static String platformName=null;
    public static String browserVersion=null;

    Logger logger = LogManager.getLogger(ExecuteEngine.class);


    public final String scenarioPath = "D:\\artdesktopkeyworddriven\\src\\main\\java\\com\\qa\\keyword\\scenarios\\ARTTestData.xlsx";


    public void startExecution(String Sheetname)
    {
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(scenarioPath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
           workbook = WorkbookFactory.create(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        System.out.println("the sheetname is " + Sheetname);
        sheet = workbook.getSheet(Sheetname);
        int k=0;
        for(int i=0;i<sheet.getLastRowNum();i++)
        {
            try {
                String locatorColType = sheet.getRow(i + 1).getCell(k + 1).toString();
                String locatorColValue = sheet.getRow(i + 1).getCell(k + 2).toString();
                String action = sheet.getRow(i + 1).getCell(k + 3).toString();

                String value = null;
                Cell cell = sheet.getRow(i + 1).getCell(k + 4);
                if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                    value = String.valueOf(sheet.getRow(i + 1).getCell(k + 4));
                } else
                    {
                     value = cell.getStringCellValue();
                } if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                    value = String.valueOf(sheet.getRow(i + 1).getCell(k + 4));
                    System.out.println("the value String is " + value);
                }if(Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                    int values = new Double(value).intValue();
                    value = String.valueOf(values).replaceAll("[-+.,%EBDAF]", "");
                    System.out.println("After conversation value inreger is " + value);
                }

                switch (action) {
                    case "open browser":
                        base = new Base();
                        properties = base.init_properties();
                        if (value.isEmpty() || value.equals("NA")) {
                            webDriver = base.init_driver(properties.getProperty("browser"));
                            wait = new WebDriverWait(webDriver,60);
                            actions = new Actions(webDriver);
                            Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();
                            browsername = capabilities.getBrowserName();
                            platformName = capabilities.getPlatform().toString();
                            browserVersion = capabilities.getVersion();
                        } else {
                            webDriver = base.init_driver(value);
                            wait = new WebDriverWait(webDriver,60);
                            actions = new Actions(webDriver);
                            Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();
                            browsername = capabilities.getBrowserName();
                            platformName = capabilities.getPlatform().toString();
                            browserVersion = capabilities.getVersion();
                        }
                        break;

                    case "enter URL":
                        if (value.isEmpty() || value.equals("NA")) {
                            webDriver.get(properties.getProperty("url"));
                        } else {
                            webDriver.navigate().to(value);
                        }
                        break;

                    case "close browser":
                        webDriver.quit();
                        break;
                    case "wait":
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "pressback":
                        webDriver.navigate().back();
                        break;

                    default:
                        break;

                }

                switch (locatorColType) {
                    case "id":
                        element = webDriver.findElement(By.id(locatorColValue));
                        if (action.equalsIgnoreCase("sendkeys")) {
                            element.clear();
                            element.sendKeys(value);
                        } else if (action.equalsIgnoreCase("click")) {
                            element.click();
                        } else if (action.equalsIgnoreCase("getText")) {
                            String elementText = element.getText();
                            System.out.println("the element text is " + elementText);
                            try {
                                Assert.assertEquals(value, elementText);
                            }catch (AssertionError error)
                            {
                                error.printStackTrace();
                            }

                        } else if (action.equalsIgnoreCase("isDisplayed")) {
                            element.isDisplayed();
                        }else if(action.equalsIgnoreCase("select")) {
                            Select options = new Select(element);
                            options.selectByVisibleText(value);
                        } else if(action.equalsIgnoreCase("waitforElement")){
                            wait.until(ExpectedConditions.visibilityOfAllElements(element));

                        } else if(action.equalsIgnoreCase("menuoption"))
                         {
                            actions.moveToElement(element).perform();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else if(action.equalsIgnoreCase("submenuoption"))
                        {
                            actions.moveToElement(element);
                            actions.click().build().perform();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }


                locatorColType = null;
                break;
}



                switch (locatorColType)
                {
                    case "xpath":
                        element = webDriver.findElement(By.xpath(locatorColValue));
                        if (action.equalsIgnoreCase("sendkeys")) {
                            element.clear();
                            element.sendKeys(value);
                        } else if (action.equalsIgnoreCase("click")) {
                            element.click();
                        } else if (action.equalsIgnoreCase("getText")) {
                            String elementText = element.getText();
                            System.out.println("the element text is " + elementText);
                            try {
                                Assert.assertEquals(value, elementText);
                            }catch (AssertionError error)
                            {
                                error.printStackTrace();
                            }
                        } else if (action.equalsIgnoreCase("isDisplayed")) {
                            element.isDisplayed();
                        }else if(action.equalsIgnoreCase("select")) {
                            Select options = new Select(element);
                            options.selectByVisibleText(value);
                        } else if(action.equalsIgnoreCase("waitforElement")){
                            wait.until(ExpectedConditions.visibilityOfAllElements(element));
                        }else if(action.equalsIgnoreCase("menuoption")) {
                            actions.moveToElement(element).perform();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else if(action.equalsIgnoreCase("submenuoption")) {
                            actions.moveToElement(element);
                            actions.click().build().perform();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }

                        locatorColType = null;
                        break;

                }

                switch (locatorColType) {
                    case "className":
                        element = webDriver.findElement(By.className(locatorColValue));
                        if (action.equalsIgnoreCase("sendkeys")) {
                            element.clear();
                            element.sendKeys(value);
                        } else if (action.equalsIgnoreCase("click")) {
                            element.click();
                        } else if (action.equalsIgnoreCase("getText")) {
                            String elementText = element.getText();
                            System.out.println("the element text is " + elementText);
                            try {
                                Assert.assertEquals(value, elementText);
                            }catch (AssertionError error)
                            {
                                error.printStackTrace();
                            }
                        } else if (action.equalsIgnoreCase("isDisplayed")) {
                            element.isDisplayed();
                        }else if(action.equalsIgnoreCase("select")) {
                            Select options = new Select(element);
                            options.selectByVisibleText(value);
                        } else if(action.equalsIgnoreCase("waitforElement")){
                            wait.until(ExpectedConditions.visibilityOfAllElements(element));
                        }
                        locatorColType = null;
                        break;

                }

                switch (locatorColType) {
                    case "cssSelector":
                        element = webDriver.findElement(By.cssSelector(locatorColValue));
                        if (action.equalsIgnoreCase("sendkeys")) {
                            element.clear();
                            element.sendKeys(value);
                        } else if (action.equalsIgnoreCase("click")) {
                            element.click();
                        } else if (action.equalsIgnoreCase("getText"))
                        {
                            String elementText = element.getText();
                            System.out.println("the element text is " + elementText);
                            try {
                                Assert.assertEquals(value, elementText);
                            }catch (AssertionError error)
                            {
                                error.printStackTrace();
                            }

                        } else if (action.equalsIgnoreCase("isDisplayed")) {
                            element.isDisplayed();
                        }else if(action.equalsIgnoreCase("select")) {
                            Select options = new Select(element);
                            options.selectByVisibleText(value);
                        } else if(action.equalsIgnoreCase("waitforElement")){
                            wait.until(ExpectedConditions.visibilityOfAllElements(element));
                        }
                        locatorColType = null;
                        break;

                }
                switch (locatorColType) {
                    case "name":
                        element = webDriver.findElement(By.name(locatorColValue));
                        if (action.equalsIgnoreCase("sendkeys")) {
                            element.clear();
                            element.sendKeys(value);
                        } else if (action.equalsIgnoreCase("click")) {
                            element.click();
                        } else if (action.equalsIgnoreCase("getText")) {
                            String elementText = element.getText();
                            System.out.println("the element text is " + elementText);
                            try {
                                Assert.assertEquals(value, elementText);
                            }catch (AssertionError error)
                            {
                                error.printStackTrace();
                            }

                        } else if (action.equalsIgnoreCase("isDisplayed")) {
                            element.isDisplayed();
                        }else if(action.equalsIgnoreCase("select")) {
                            Select options = new Select(element);
                            options.selectByVisibleText(value);
                        }
                        locatorColType = null;
                        break;

                }

                switch (locatorColType) {
                    case "linkText":
                        element = webDriver.findElement(By.linkText(locatorColValue));
                        if (action.equalsIgnoreCase("sendkeys")) {
                            element.clear();
                            element.sendKeys(value);
                        } else if (action.equalsIgnoreCase("click")) {
                            element.click();
                        } else if (action.equalsIgnoreCase("getText")) {
                            String elelemtText = element.getText();
                            System.out.println("the element text is " + elelemtText);

                        } else if (action.equalsIgnoreCase("isDisplayed")) {
                            element.isDisplayed();
                        }else if(action.equalsIgnoreCase("select")) {
                            Select options = new Select(element);
                            options.selectByVisibleText(value);
                        }
                        locatorColType = null;
                        break;

                }
                switch (locatorColType) {
                    case "partialLinkText":
                        element = webDriver.findElement(By.partialLinkText(locatorColValue));
                        if (action.equalsIgnoreCase("sendkeys")) {
                            element.clear();
                            element.sendKeys(value);
                        } else if (action.equalsIgnoreCase("click")) {
                            element.click();
                        } else if (action.equalsIgnoreCase("getText")) {
                            String elelemtText = element.getText();
                            System.out.println("the element text is " + elelemtText);
                        } else if (action.equalsIgnoreCase("isDisplayed")) {
                            element.isDisplayed();
                        } else if(action.equalsIgnoreCase("select")) {
                            Select options = new Select(element);
                            options.selectByVisibleText(value);
                        }

                        locatorColType = null;
                        break;
                }

            }catch (NullPointerException e) {
           // e.printStackTrace();
            }
        }

    }

    /**
     * Function to mouse over a menu
     *
     * @param menuselement
     * @param tosubmenuelement
     * @throws InterruptedException
     */

    public void mouseHover(WebElement menuselement, WebElement tosubmenuelement) throws InterruptedException {

        Actions actions = new Actions(webDriver);
        actions.moveToElement(menuselement).perform();
        Thread.sleep(3000);
        actions.moveToElement(tosubmenuelement);
        actions.click().build().perform();
        Thread.sleep(3000);
    }
}
