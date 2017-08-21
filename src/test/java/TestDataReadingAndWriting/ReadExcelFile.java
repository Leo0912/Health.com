package TestDataReadingAndWriting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile 
{

		public static Map<String, Map<String,Map<String, String>>> readExcel(String filePath,String fileName,String sheetName) throws IOException
		 {
			Map<String, Map<String,Map<String, String>>> excelMap=new LinkedHashMap<String,Map<String, Map<String, String>>>();
			File file =    new File(filePath+"\\"+fileName);
			 FileInputStream inputStream = new FileInputStream(file);
			    Workbook myWorkbook = null;
			try
			{
				
   

			    //Find the file extension by splitting file name in substring  and getting only extension name

			    String fileExtensionName = fileName.substring(fileName.indexOf("."));

			    //Check condition if the file is xlsx file

			    if(fileExtensionName.equals(".xlsx")){

			    //If it is xlsx file then create object of XSSFWorkbook class

			    	myWorkbook = new XSSFWorkbook(inputStream);
			    	//myWorkbook = new HSSFWorkbook(inputStream);

			    }

			    //Check condition if the file is xls file

			    else if(fileExtensionName.equals(".xls")){

			        //If it is xls file then create object of XSSFWorkbook class

			        //myWorkbook = new HSSFWorkbook(inputStream);

			    }

			    //Read sheet inside the workbook by its name

			    Sheet mySheet = myWorkbook.getSheet(sheetName);

			    //Find number of rows in excel file

			    int rowCount = mySheet.getLastRowNum()-mySheet.getFirstRowNum();

			    //Create a loop over all the rows of excel file to read it

			    for (int i = 1; i <= rowCount+1; i++) {

			        Row row = mySheet.getRow(i);

			        //Create a loop to print cell values in a row
			       
			        for (int j = 0; j < row.getLastCellNum()-1; j++) {

			        	 Map<String, Map<String,String>> mainUrlAndchildURLs=new LinkedHashMap<String, Map<String,String>>();
			        	 Map<String,String> childUrlList = new LinkedHashMap<String,String>();
			        	mainUrlAndchildURLs.put(row.getCell(j+1).getStringCellValue(), childUrlList );
			        	excelMap.put(i+ "_"+ row.getCell(j).getStringCellValue(), mainUrlAndchildURLs);
			        	
			        }
			        
			       
			        //System.out.println();

			    }
			    
			
			}
			    	catch(Exception ex)
			    	{
			    		System.out.println("Exception:" + ex.getMessage());
			    	}
			
			return excelMap;
			
		    

	}
		 
		 public static String readCellValueByKey_FromExcel(String filePath,String fileName,String sheetName, String keyToSearchfor) throws IOException
		 {
			 String requiredCellValue="";
			 File file =   new File(filePath+"\\"+fileName);
			 
			 try
			 {
				
				    //Create an object of FileInputStream class to read excel file

				    FileInputStream inputStream = new FileInputStream(file);

				    Workbook myWorkbook = null;

				    //Find the file extension by splitting file name in substring  and getting only extension name

				    String fileExtensionName = fileName.substring(fileName.indexOf("."));

				    //Check condition if the file is xlsx file

				    if(fileExtensionName.equals(".xlsx")){

				    //If it is xlsx file then create object of XSSFWorkbook class

				    myWorkbook = new XSSFWorkbook(inputStream);
				    	//myWorkbook = new HSSFWorkbook(inputStream);

				    }

				    //Check condition if the file is xls file

				    else if(fileExtensionName.equals(".xls")){

				        //If it is xls file then create object of XSSFWorkbook class


				    }

				    //Read sheet inside the workbook by its name

				    Sheet mySheet = myWorkbook.getSheet(sheetName);

				    //Find number of rows in excel file

				    int rowCount = mySheet.getLastRowNum()-mySheet.getFirstRowNum();
				    String cellKey="";
				    
				    DataFormatter formatter = new DataFormatter();
				    
				    //Create a loop over all the rows of excel file to read it

				    for (int i = 1; i < rowCount+1; i++) {

				        Row row = mySheet.getRow(i);

				        //Create a loop to print cell values in a row

				        for (int j = 0; j < row.getLastCellNum(); j++) {

				            //Print Excel data in console

				        	cellKey=formatter.formatCellValue(row.getCell(j));
				        	if(cellKey.equals(keyToSearchfor))
				        	{
				        		requiredCellValue=formatter.formatCellValue(row.getCell(j+1));
				        		break;
				        	}

				        }

				    }
				    
				   
			 }
			
			 
		    catch(Exception ex)
			 {
		    
		    	System.out.println("Exception:" + ex.getMessage());
		    	
			 }
			return requiredCellValue;
		 }
}

