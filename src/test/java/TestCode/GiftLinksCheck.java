package TestCode;

import static UtilityFunctions.CommonMethods.addLinksInNormalPageToAList;
import static UtilityFunctions.CommonMethods.getLinksPerSlide;
import static UtilityFunctions.CommonMethods.isElementPresent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ObjectRepository.ElementLocator;
import TestDataReadingAndWriting.ReadExcelFile;
import TestDataReadingAndWriting.WriteDataIntoExcel;
import UtilityFunctions.CommonMethods;
import UtilityFunctions.LocalDriverManager;

public class GiftLinksCheck extends base {
	private WebDriver driver;
	ElementLocator objRep = new ElementLocator();
	CommonMethods commonfunc = new CommonMethods();
	String destfilePath = System.getProperty("user.dir") + "\\TestData_InputExcelFile\\output";
	File destDirectory = new File(destfilePath);
	String fileName_urlsAndXpaths = "Xpathsexcel.xlsx";
	String sourcefilePath = System.getProperty("user.dir") + "\\TestData_InputExcelFile";
	String filePath = "./TestData_InputExcelFile";

	@Parameters({ "fileName", "sheetName" })
	@BeforeTest
	public void setUp(String fileName_input, String sheetName) {
		try {
			if (!destDirectory.exists()) {
				destDirectory.mkdir();
			}
			File sourcefile = new File(filePath + "/" + fileName_input);
			File destinationfile = new File(destfilePath + "\\" + fileName_input);
			if (destinationfile.exists()) {
				if (destinationfile.delete()) {
					// System.out.println("File deleted");
				}
			}
			FileUtils.copyFileToDirectory(sourcefile, destDirectory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			Reporter.log(e.getMessage());
		}
	}

	@Test
	@Parameters({ "fileName", "sheetName" })
	public void topPerformers(String fileName, String sheetName) {
		int mainCounter = 0;
		int index2 = 0;
		int countofRow = 0;

		try {
			// setUp(fileName,sheetName);
			System.out.println("Reading excel file with::Name--" + fileName + "--SheetName:" + sheetName + "--Path-"
					+ filePath + "BrowserID::" + LocalDriverManager.getDriver().hashCode());
			Map<String, Map<String, Map<String, String>>> map = ReadExcelFile.readExcel(filePath, fileName, sheetName);
			Set<Entry<String, Map<String, Map<String, String>>>> value = map.entrySet();
			for (Iterator<Entry<String, Map<String, Map<String, String>>>> iterator1 = value.iterator(); iterator1
					.hasNext();) {
				Entry<String, Map<String, Map<String, String>>> entry = iterator1.next();
				countofRow = countofRow + 1;
				int failCounter = 0;
				String key1 = entry.getKey();
				// System.out.println(key1);
				Map<String, Map<String, String>> value1 = entry.getValue();
				Set<Entry<String, Map<String, String>>> m1 = value1.entrySet();

				StringBuffer sbf = new StringBuffer();
				for (Iterator<Entry<String, Map<String, String>>> iterator2 = m1.iterator(); iterator2.hasNext();) {
					Entry<String, Map<String, String>> entry2 = (Entry<String, Map<String, String>>) iterator2.next();
					index2++;
					String key2 = entry2.getKey();
					// System.out.println(key2);
					invokeBrowser(key2);
					// System.out.println(key2+":::"+LocalDriverManager.getDriver().hashCode());
					String Parent_Window = LocalDriverManager.getDriver().getWindowHandle();
					boolean isItSlidePage = CommonMethods.isSlidePresent(LocalDriverManager.getDriver(),
							By.xpath("//div[contains(@class,'view-one-page')]//span[starts-with(text(),' 1 of')]"));
					Map<String, String> value2 = entry2.getValue();
					if (isItSlidePage) {
						// value2 = addLinksInSlideToAList(driver,
						// objRep.dotComLinks_Path);
						value2 = getLinksPerSlide(LocalDriverManager.getDriver());
					} else {
						value2 = addLinksInNormalPageToAList(LocalDriverManager.getDriver(),
								ElementLocator.dotComLinks_Path);
					}
					Set<Map.Entry<String, String>> m4 = value2.entrySet();
					int hrefssize = m4.size();
					System.out.println(index2 + ": Main URL:" + key2 + ":::No of sub links:" + hrefssize);
					int sublinkIndex = 1;
					for (Iterator<Entry<String, String>> iterator3 = m4.iterator(); iterator3.hasNext();) {
						((JavascriptExecutor) LocalDriverManager.getDriver()).executeScript("window.open()");
						ArrayList<String> tabs = new ArrayList<String>(
								LocalDriverManager.getDriver().getWindowHandles());
						LocalDriverManager.getDriver().switchTo().window(tabs.get(1)); // switches
																						// to
																						// //
						Entry<String, String> entry3 = (Entry<String, String>) iterator3.next();

						// Extract link text and get the xpath for it
						String[] splitLinkText = entry3.getKey().split("_");
						String linkText = splitLinkText[1].replaceAll("\\s", "");
						System.out.println("linktext is ----" + linkText + "---");
						System.out.println("index is ----" + sublinkIndex);
						String xpathFromExcel = ReadExcelFile.readCellValueByKey_FromExcel(sourcefilePath,
								fileName_urlsAndXpaths, "xpaths", linkText);

						try {
							invokeBrowser(entry3.getValue());
						} catch (TimeoutException e) {
							LocalDriverManager.getDriver().switchTo().window(tabs.get(1)).close();
							LocalDriverManager.getDriver().switchTo().window(Parent_Window);
							// System.out.println("<p>"+ "Page not opening or
							// not found. Page is:"+ entry3.getValue());
							Reporter.log("Page not opening or not found. Link Index is:" + sublinkIndex + " . Page is:"
									+ entry3.getValue());
							// comments.add("Page not opening or not found. Page
							// is:"+ entry3.getValue());
							sbf.append("ERROR:: Page not opening or not found. Link Index is:" + sublinkIndex
									+ " . Page is:" + entry3.getValue()).append(System.getProperty("line.separator"));
							failCounter = failCounter + 1;
							mainCounter = mainCounter + 1;
							continue;
						}

						CommonMethods.modalWindowCheckAndClose(LocalDriverManager.getDriver());
						CommonMethods.linksWithMultiProductsCheck(LocalDriverManager.getDriver(), entry3.getValue(),
								xpathFromExcel, linkText);
						boolean addtoCartBtnPresent = isElementPresent(LocalDriverManager.getDriver(),
								By.xpath(xpathFromExcel), linkText);
						if (!addtoCartBtnPresent) {
							System.out.println(
									xpathFromExcel + "FAIL::" + " Add to cart not shown for the link with Index: "
											+ sublinkIndex + " and href is:" + entry3.getValue());
							Reporter.log("FAIL::" + " Add to cart not shown for the link with Index: " + sublinkIndex
									+ " and href is:" + entry3.getValue());
							sbf.append("FAIL::" + " Add to cart not shown for the link with Index: " + sublinkIndex
									+ " and href is:" + entry3.getValue()).append(System.getProperty("line.separator"));
							LocalDriverManager.getDriver().switchTo().window(tabs.get(1)).close();
							LocalDriverManager.getDriver().switchTo().window(Parent_Window);
							failCounter = failCounter + 1;
							mainCounter = mainCounter + 1;

						} else {
							LocalDriverManager.getDriver().switchTo().window(tabs.get(1)).close();
							LocalDriverManager.getDriver().switchTo().window(Parent_Window);
						}

						sublinkIndex++;
					}

					LocalDateTime dt = LocalDateTime.now();
					DateTimeFormatter dtfor = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					String executionDateTime = dt.format(dtfor);

					if (failCounter > 0) {
						WriteDataIntoExcel.writeToExcel(destfilePath, fileName, "TopPerf", countofRow, 2, "FAIL");
						WriteDataIntoExcel.writeToExcel_addNotes(destfilePath, fileName, "TopPerf", countofRow, 3, 4,
								sbf, executionDateTime);
						System.out.println("No of Failures::" + failCounter + ". Source URL is: " + key2);
						Reporter.log("No of Failures:" + failCounter + ". Source URL is: " + key2);
					} else {

						WriteDataIntoExcel.writeToExcel(destfilePath, fileName, "TopPerf", countofRow, 2, "PASS");
						WriteDataIntoExcel.writeToExcel_addNotes(destfilePath, fileName, "TopPerf", countofRow, 3, 4,
								sbf, executionDateTime);
					}
					sbf.delete(0, sbf.length());
				}
			}

		}

		catch (Exception ex) {
			System.out.println("Exception from the main class");
			System.out.println(ex.getMessage());
			Assert.fail(ex.getMessage());
		}
		if (mainCounter > 0)
			Assert.fail("Test Failed");
	}

	private void invokeBrowser(String url) {
		LocalDriverManager.getDriver().get(url);
	}

}
