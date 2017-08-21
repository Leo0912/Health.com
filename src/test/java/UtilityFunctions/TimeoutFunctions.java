package UtilityFunctions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TimeoutFunctions 
{

	// function to wait for 60 secs for element
	public static void explicitWait(WebElement ele, WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			System.out.println("Element "+ ele +" found!!");
		} catch (Exception e) {
			System.out.println("Element not found!!");
			e.printStackTrace();
		}
	}

	// Function to wait as per given Time for timeout
	public void explicitWait(WebElement ele, WebDriver driver, int timeoutinSec) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutinSec);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			System.out.println("Element "+ ele +" found!!");
		} catch (Exception e) {
			System.out.println("Element not found!!");
			e.printStackTrace();
		}
	}

	// Function to take Screenshot
	public static void takeScreenShot(WebDriver driver) {
		try {

			// Get Called function Name
			String functionName = new Exception().getStackTrace()[1].getMethodName();
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("MMddyyyyhhmmss");
			// Save ScreenShot
			FileUtils.copyFile(scrFile, new File("./ErrorSS/fn_" + functionName + "_SS_" + ft.format(dNow) + ".png"));
			System.out.println("fn_" + functionName + "_SS_" + ft.format(dNow) + ".png Saved Successfully!!");
			
		} catch (IOException e) {
			System.out.println("Unable to take Screenshot");
			e.printStackTrace();
		}
	}
	
	//Implicit wait with wait time input in Sec
	public static void implicitWait(WebDriver driver, int timeunitInSec){
		driver.manage().timeouts().implicitlyWait(timeunitInSec, TimeUnit.SECONDS);
		
	}

	//Sleep function
	public void sleep(int timeunitInSec){
		try {
			Thread.sleep(timeunitInSec*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
