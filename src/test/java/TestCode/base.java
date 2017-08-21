package TestCode;

import java.util.Date;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import TestDataReadingAndWriting.*;

public class base {

	Date StartTime = new Date();
	splitAndMergeExcel Splitfiles = new splitAndMergeExcel();

	@BeforeSuite
	public void BaseSetUp() {
		StartTime = new Date();
		System.out.println("<<<<<<<--------Base Set Up Run----------->>>>>>>>>" + StartTime);
		
		Splitfiles.ReportSplitter("./TestData_InputExcelFile/xpathAndlinksWithmultiResults/E-commerce_Health.xlsx", 4);
		// code to merge all output files goes here

	}

	@AfterSuite
	public void tearDown() throws Exception {
		Date EndTime = new Date();
		long diff = EndTime.getTime() - StartTime.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		System.out.println("Total time for Execution in HH:mm:ss:>>" + diffHours + ":"+diffMinutes+":"+diffSeconds);
		
		Splitfiles.mergeAllData();
		// code to merge all output files goes here

	}

}
