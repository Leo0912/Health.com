package TestDataReadingAndWriting;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteDataIntoExcel 
{
	public void writeToExcel(String filePath,String fileName,String sheetName,String[] dataToWrite) throws IOException{

        //Create an object of File class to open xlsx file

        File file =    new File(filePath+"\\"+fileName);

        //Create an object of FileInputStream class to read excel file

        FileInputStream inputStream = new FileInputStream(file);

        Workbook myWorkbook = null;

        //Find the file extension by splitting  file name in substring and getting only extension name

        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        //Check condition if the file is xlsx file

        if(fileExtensionName.equals(".xlsx")){

        //If it is xlsx file then create object of XSSFWorkbook class

        myWorkbook = new XSSFWorkbook(inputStream);

        }

        //Check condition if the file is xls file

        else if(fileExtensionName.equals(".xls")){

            //If it is xls file then create object of XSSFWorkbook class

            myWorkbook = new HSSFWorkbook(inputStream);

        }

        

    //Read excel sheet by sheet name    

    Sheet sheet = myWorkbook.getSheet(sheetName);

    //Get the current count of rows in excel file

    int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

    //Get the first row from the sheet

    Row row = sheet.getRow(0);

    //Create a new row and append it at last of sheet

    Row newRow = sheet.createRow(rowCount+1);

    //Create a loop over the cell of newly created Row

    for(int j = 0; j < row.getLastCellNum(); j++){

        //Fill data in row

        Cell cell = newRow.createCell(j);

        cell.setCellValue(dataToWrite[j]);

    }

    //Close input stream

    inputStream.close();

    //Create an object of FileOutputStream class to create write data in excel file

    FileOutputStream outputStream = new FileOutputStream(file);

    //write data in the excel file

    myWorkbook.write(outputStream);

    //close output stream

    outputStream.close();

	}
	
	public static void writeToExcel(String filePath,String fileName,String sheetName,int rowNumber, int colNumber,String dataToWrite) throws IOException{

        //Create an object of File class to open xlsx file

        File file =    new File(filePath+"\\"+fileName);

        //Create an object of FileInputStream class to read excel file

        FileInputStream inputStream = new FileInputStream(file);

        Workbook myWorkbook = null;

        //Find the file extension by splitting  file name in substring and getting only extension name

        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        //Check condition if the file is xlsx file

        if(fileExtensionName.equals(".xlsx")){

        //If it is xlsx file then create object of XSSFWorkbook class

        myWorkbook = new XSSFWorkbook(inputStream);

        }

        //Check condition if the file is xls file

        else if(fileExtensionName.equals(".xls")){

            //If it is xls file then create object of XSSFWorkbook class

            myWorkbook = new HSSFWorkbook(inputStream);

        }

        
       /* HSSFCellStyle my_style = (HSSFCellStyle) myWorkbook.createCellStyle();
         Create HSSFFont object from the workbook 
        HSSFFont my_font=(HSSFFont) myWorkbook.createFont();
         set the weight of the font 
        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
         attach the font to the style created earlier 
        my_style.setFont(my_font);*/
        

    //Read excel sheet by sheet name    

    Sheet sheet = myWorkbook.getSheet(sheetName);

    //Get the current count of rows in excel file

    int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
    
    //Set Font
    
    //

    //Get the first row from the sheet

    //Row row = sheet.getRow(0);
    Row row = sheet.getRow(rowNumber);

    //Create a new row and append it at last of sheet

    //Row newRow = sheet.createRow(rowCount+1);

    //Create a loop over the cell of newly created Row

  

        //Fill data in row
    	 Cell resultcell= row.createCell(colNumber);
    	 
        //Cell cell = newRow.createCell(j);

    	 resultcell.setCellValue(dataToWrite);
    	 //resultcell.setCellStyle(my_style);

    

    //Close input stream

    inputStream.close();

    //Create an object of FileOutputStream class to create write data in excel file

    FileOutputStream outputStream = new FileOutputStream(file);

    //write data in the excel file

    myWorkbook.write(outputStream);

    //close output stream

    outputStream.close();

	}

	public static void writeToExcel_addNotes(String filePath,String fileName,String sheetName,int rowNumber, int colNumber,int cellDate, StringBuffer dataToWrite, String executionDate ) throws IOException{

        //Create an object of File class to open xlsx file
		
        File file =    new File(filePath+"\\"+fileName);

        //Create an object of FileInputStream class to read excel file

        FileInputStream inputStream = new FileInputStream(file);

        Workbook myWorkbook = null;

        //Find the file extension by splitting  file name in substring and getting only extension name

        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        //Check condition if the file is xlsx file

        if(fileExtensionName.equals(".xlsx")){

        //If it is xlsx file then create object of XSSFWorkbook class

        myWorkbook = new XSSFWorkbook(inputStream);

        }

        //Check condition if the file is xls file

        else if(fileExtensionName.equals(".xls")){

            //If it is xls file then create object of XSSFWorkbook class

            myWorkbook = new HSSFWorkbook(inputStream);

        }

        

    //Read excel sheet by sheet name    

    Sheet sheet = myWorkbook.getSheet(sheetName);

    //Get the current count of rows in excel file

    int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

    //Get the first row from the sheet

    //Row row = sheet.getRow(0);
    Row row = sheet.getRow(rowNumber);

    //Create a new row and append it at last of sheet

    //Row newRow = sheet.createRow(rowCount+1);

    //Create a loop over the cell of newly created Row

  

        //Fill data in row
    	 Cell resultcell= row.createCell(colNumber);
    	 
    	 resultcell.setCellValue(dataToWrite.toString());

    	 Cell datecell= row.createCell(cellDate);
    	 
    	 datecell.setCellValue(executionDate);
    	 
        //Cell cell = newRow.createCell(j);
    	 /*for(int i=0;i<=dataToWrite.length();i++){

    	 //resultcell.setCellValue(dataToWrite.set(i, dataToWrite.get(i).toString()));
    		// Cell cell = row.createCell(i);
    		 resultcell.setCellValue(dataToWrite);

    	 }*/

    //Close input stream

    inputStream.close();

    //Create an object of FileOutputStream class to create write data in excel file

    FileOutputStream outputStream = new FileOutputStream(file);

    //write data in the excel file

    myWorkbook.write(outputStream);

    //close output stream

    outputStream.close();

	}

	
}

