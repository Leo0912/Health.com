package TestDataReadingAndWriting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class splitAndMergeExcel {

	private String fileName;
	private int maxRows;
	private boolean firstExcelind = true;
	String destfilePath = "./TestData_InputExcelFile/SampleSplit/output";

	public void ReportSplitter(String fileName, final int maxRows) {

		// ZipSecureFile.setMinInflateRatio(0);

		this.fileName = fileName;
		this.maxRows = maxRows;

		try {
			/* Read in the original Excel file. */
			OPCPackage pkg = OPCPackage.open(new File(fileName));
			XSSFWorkbook workbook = new XSSFWorkbook(pkg);
			XSSFSheet sheet = workbook.getSheetAt(0);

			/* Only split if there are more rows than the desired amount. */
			if (sheet.getPhysicalNumberOfRows() >= maxRows) {
				List<XSSFWorkbook> wbs = splitWorkbook(workbook);
				writeWorkBooks(wbs);
			}
			pkg.close();
		} catch (EncryptedDocumentException | IOException | InvalidFormatException e) {
			e.printStackTrace();
		}
	}

	private List<XSSFWorkbook> splitWorkbook(XSSFWorkbook workbook) {

		List<XSSFWorkbook> workbooks = new ArrayList<XSSFWorkbook>();

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sh = wb.createSheet("TopPerf");

		XSSFRow newRow;
		XSSFCell newCell;

		int rowCount = 0;
		int colCount = 0;

		XSSFSheet sheet = workbook.getSheetAt(0);

		for (Row row : sheet) {
			newRow = sh.createRow(rowCount++);

			/* Time to create a new workbook? */
			if (rowCount == maxRows) {

				// Insert header to all Excel Sheets getting created
				sh.shiftRows(0, sh.getLastRowNum(), 1);
				Row Headerrow = sh.createRow(0);
				Headerrow.createCell(0).setCellValue("Sites");
				Headerrow.createCell(1).setCellValue("URL");

				// remove the header from Original Sheet in the first Excel
				// (Test_1.xlsx)
				if (firstExcelind == true) {
					sh.shiftRows(2, sh.getLastRowNum(), -1, false, false);
					firstExcelind = false;
				}

				// Add workbook to Workbooks list
				workbooks.add(wb);
				wb = new XSSFWorkbook();
				sh = wb.createSheet("TopPerf");
				rowCount = 0;
			}

			for (Cell cell : row) {

				newCell = newRow.createCell(colCount++);
				newCell = setValue(newCell, cell);

			}
			colCount = 0;
		}

		/* Only add the last workbook if it has content */
		if (wb.getSheetAt(0).getPhysicalNumberOfRows() > 0) {

			// Insert header to all Excel Sheets getting created
			sh.shiftRows(0, sh.getLastRowNum(), 1);
			Row Headerrow = sh.createRow(0);
			Headerrow.createCell(0).setCellValue("Sites");
			Headerrow.createCell(1).setCellValue("URL");
			workbooks.add(wb);
		}
		return workbooks;
	}

	/*
	 * Grabbing cell contents can be tricky. We first need to determine what
	 * type of cell it is.
	 */
	private XSSFCell setValue(XSSFCell newCell, Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			newCell.setCellValue(cell.getRichStringCellValue().getString());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				newCell.setCellValue(cell.getDateCellValue());
			} else {
				newCell.setCellValue(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			newCell.setCellValue(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			newCell.setCellFormula(cell.getCellFormula());
			break;
		default:
			// System.out.println("Could not determine cell type");
			newCell.setCellValue(cell.getRichStringCellValue().getString());
		}
		return newCell;
	}

	/* Write all the workbooks to disk. */
	private void writeWorkBooks(List<XSSFWorkbook> wbs) {
		FileOutputStream out;
		try {
			for (int i = 0; i < wbs.size(); i++) {
				String newFileName = "Test";
				out = new FileOutputStream(
						new File("./TestData_InputExcelFile/" + newFileName + "_" + (i + 1) + ".xlsx"));
				wbs.get(i).write(out);
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mergeAllData() throws Exception {

		File filePathObj = new File(destfilePath);
		File[] fileNameArr = filePathObj.listFiles();
		HashMap<String, String> dataMap = new HashMap<String, String>();
		HashMap<String, String> statusMap = new HashMap<String, String>();
		HashMap<String, String> commentMap = new HashMap<String, String>();
		HashMap<String, String> timeMap = new HashMap<String, String>();

		for (int i = 0; i < fileNameArr.length; i++) {

			if (fileNameArr[i] != null && fileNameArr[i].isFile()
					&& (fileNameArr[i].getName().contains(".xlsx") || !fileNameArr[i].getName().contains(".xlx"))
					&& !fileNameArr[i].getName().contains("Xpathsexcel")
					&& !fileNameArr[i].getName().contains("mergedSites")) {
				Pattern p = Pattern.compile("[^a-z0-9 _]", Pattern.CASE_INSENSITIVE);
				String fileNameStr = fileNameArr[i].getName();
				fileNameStr = fileNameStr.substring(0, fileNameStr.indexOf("."));
				Matcher m = p.matcher(fileNameStr);
				boolean bool = m.find();
				if (!bool) {
					FileInputStream fileInputStream = new FileInputStream(fileNameArr[i]);
					Workbook myWorkbook = new XSSFWorkbook(fileInputStream);
					Sheet mySheet = myWorkbook.getSheet("TopPerf");
					int rowCount = mySheet.getLastRowNum() - mySheet.getFirstRowNum();
					for (int j = 0; j < rowCount + 1; j++) {
						Row row = mySheet.getRow(j);
						Cell siteCell = row.getCell(0);
						Cell urlCell = row.getCell(1);
						Cell statusCell = row.getCell(2);
						Cell descCell = row.getCell(3);
						Cell timeCell = row.getCell(4);
						if (!siteCell.getStringCellValue().equals("Sites")
								&& !urlCell.getStringCellValue().equals("URL")) {
							dataMap.put(siteCell.getStringCellValue(), urlCell.getStringCellValue());
							statusMap.put(siteCell.getStringCellValue(), statusCell.getStringCellValue());
							commentMap.put(siteCell.getStringCellValue(), descCell.getStringCellValue());
							timeMap.put(siteCell.getStringCellValue(), timeCell.getStringCellValue());
						}

					}
				}

			}
		}
		Workbook mergedExcelWb = new XSSFWorkbook();
		Sheet sheet = mergedExcelWb.createSheet("TopPerf");
		Row headerRow = sheet.createRow(0);
		Cell siteHeaderCell = headerRow.createCell(0);
		Cell urlHeaderCell = headerRow.createCell(1);
		Cell commentHeaderCell = headerRow.createCell(2);
		Cell dateHeaderCell = headerRow.createCell(3);
		Cell timeHeaderCell = headerRow.createCell(4);
		siteHeaderCell.setCellValue("Sites");
		urlHeaderCell.setCellValue("URL");
		commentHeaderCell.setCellValue("Comment");
		dateHeaderCell.setCellValue("Date");
		timeHeaderCell.setCellValue("Time");

		Set keySet = dataMap.keySet();
		String url = null;
		String site = null;
		String status = null;
		String description = null;
		String timestamp = null;

		Iterator<String> keySetItr = keySet.iterator();
		int totalRowCount = 1;
		while (keySetItr.hasNext()) {
			site = keySetItr.next();
			url = dataMap.get(site);
			status = statusMap.get(site);
			description = commentMap.get(site);
			timestamp = timeMap.get(site);
			Row siteRow = sheet.createRow(totalRowCount);
			Cell siteNameCell = siteRow.createCell(0);
			Cell siteUrlCell = siteRow.createCell(1);
			Cell siteStatusCell = siteRow.createCell(2);
			Cell siteDescCell = siteRow.createCell(3);
			Cell siteTimeCell = siteRow.createCell(4);

			siteNameCell.setCellValue(site);
			siteUrlCell.setCellValue(url);
			siteStatusCell.setCellValue(status);
			siteDescCell.setCellValue(description);
			siteTimeCell.setCellValue(timestamp);
			totalRowCount++;
		}
		File checkMergedFile = new File(destfilePath + "\\mergedSites.xlsx");
		if (!checkMergedFile.isFile()) {
			FileOutputStream outputStream = new FileOutputStream(destfilePath + "\\mergedSites.xlsx");
			mergedExcelWb.write(outputStream);
			outputStream.close();
		} else {
			if (checkMergedFile.delete()) {
				FileOutputStream outputStream = new FileOutputStream(destfilePath + "\\mergedSites.xlsx");
				mergedExcelWb.write(outputStream);
				outputStream.close();
			} else {
				System.out.println("Could not delete older merged file. Please delete it manually and try again.");
			}
		}
	}

}
