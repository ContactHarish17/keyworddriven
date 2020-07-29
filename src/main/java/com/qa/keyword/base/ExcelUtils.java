package com.qa.keyword.base;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;


public class ExcelUtils {


    public static String excelpath = "D:\\artdesktopkeyworddriven\\Excel\\TestData.xlsx";
    public static  XSSFWorkbook workbook;
    public static XSSFSheet sheet;
    public static XSSFCell cell;

    public static void main(String args[])
    {
        getRowCount();
        getCellDataStringValue();
        getNumericCellData();
        getNumericData();
       }


    public static void getRowCount()
    {
        try {
            workbook = new XSSFWorkbook(excelpath);
            sheet = workbook.getSheet("data");
            int rowscount = sheet.getPhysicalNumberOfRows();
            System.out.println("the number of rows " + rowscount);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

   public static void getCellDataStringValue()
   {
       try {
           workbook = new XSSFWorkbook(excelpath);
           sheet = workbook.getSheet("data");
           String celldata = sheet.getRow(1).getCell(0).getStringCellValue();
           System.out.println("the cell values " + celldata);
       } catch (IOException e) {
           e.printStackTrace();
       }

   }


   public static int getNumericCellData()
   {
       try {
           workbook = new XSSFWorkbook(excelpath);
       } catch (IOException e) {
           e.printStackTrace();
       }
       sheet = workbook.getSheet("data");
       int celldata = (int) sheet.getRow(1).getCell(3).getNumericCellValue();
       System.out.println("the integer cell values " + celldata);
       return celldata;
   }


    public static String getCellDataIntValue(int rownum, int cellnum)
    {
        try {
            workbook = new XSSFWorkbook(excelpath);
            sheet = workbook.getSheet("data");
            cell =  sheet.getRow(rownum).getCell(cellnum);
            if(XSSFCell.CELL_TYPE_NUMERIC == cell.getCellType())
            {
            return String.valueOf(cell.getNumericCellValue());
            }else
                {
                return cell.getStringCellValue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void getNumericData()
    {
        int values = getNumericCellData();
        int cellvalue= new Double(values).intValue();
        System.out.println("the value is " + cellvalue);

    }

}
