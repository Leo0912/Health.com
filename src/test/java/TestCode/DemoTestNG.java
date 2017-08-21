package TestCode;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ObjectRepository.ElementLocator;
//import TestDataReadingAndWriting.ReadDataFromExcel;
import UtilityFunctions.CommonMethods;

public class DemoTestNG 
{
	
	public static void main(String[] args) 
	{
		String path= getXpathFormultiresultURL("underarmour");
		/* String driverPath = "C:\\Users\\guttik\\Downloads\\chromedriver_win32\\";
	//WebDriver driver1 = new FirefoxDriver();
		       String appUrl ="http://www.health.com/beauty/natural-beauty-products";
	
		       driver1.manage().window().maximize();
		       driver1.get(appUrl);
		       
		       System.setProperty("webdriver.chrome.driver", driverPath
						+ "chromedriver.exe");
				WebDriver driver1 = new ChromeDriver();
				driver1.manage().window().maximize();
				 driver1.get(appUrl);
		      try 
		      {
				getLinksPerSlide(driver1);
		      } 
		      catch (InterruptedException e) 
		      {
				// TODO Auto-generated catch block
				e.printStackTrace();
		      }	
	*/
		/*
		WebDriver driver1 = new FirefoxDriver();
	       String appUrl ="http://www.zappos.com/bernie-mev-lulia?PID=7883147&AID=11554337&utm_source=Time+Inc.&splash=none&utm_medium=affiliate&cjevent=214f0c4a78db11e780cb848f69840f81_265103985663963284%3AhoBDLhpEHym9";

	       driver1.manage().window().maximize();
	       driver1.get(appUrl);*/
	       //twoLinksCheck(driver1,appUrl);
	       //
	       //CommonMethods.modalWindowCheckAndClose(driver1);
	       //boolean addtoCartBtnPresent = isElementPresent(driver1, By.xpath("//input[(@id='receiveing-addToCartbtn')]"));

	       //
	       
	      /* By multielements= By.xpath("//button[@id='add-to-cart']");
	       WebElement elem= driver1.findElement(multielements);
	       String text= elem.getAttribute("value").toString();
	       System.out.println(text);*/
	       //driver1.quit();
	}
	
	public static void getLinksPerSlide(WebDriver driver) throws InterruptedException
	{
		int tot_slides;
		int ads;
		ArrayList<String> sites = new ArrayList<String>();
		/*ArrayList<String> sites = new ArrayList<String>();
		String[] slidetext =driver.findElement(By.xpath("//div[contains(@class,'view-one-page')]//span[starts-with(text(),' 1 of')]")).getText().split(" ");
		int noOfpages = Integer.parseInt(slidetext[2]);*/
		
		By noOfpagesInSlide_path= By.xpath("//div[contains(@class,'view-one-page')]//span[starts-with(text(),' 1 of')]");
		String textWithPages= driver.findElement(noOfpagesInSlide_path).getText().toString();
		String[] splitText= textWithPages.split("of");
		int noOfPages= Integer.valueOf(splitText[1].toString().trim());
		
		ads = Math.round(noOfPages/3);
		tot_slides = noOfPages + ads + 1;
		System.out.println(tot_slides);
		
	//	2. Loop each slide say (25 times) and store the hrefs in arraylist(2.  check if its a Ad page, if yes-> continue)
		for(int i=1;i<=tot_slides;i++)
			{ 
			
			System.out.println(i);
			Thread.sleep(5000);
	         if (i==1 || i==tot_slides)
	         {
	        	 if(i==1)
	       	  driver.findElement(By.xpath("//div[contains(@class,'view-one-page')]//span[starts-with(text(),' "+i+" of')]/following-sibling::a")).click();
	       	 /* if(driver.findElements(By.xpath("//div[@data-slide-number='"+i+"']//a[contains(text(),'.com')]")).size() != 0)
	       	  	{
	       		  
	       	  	}*/
	         }
	         
	         else if(driver.findElements(By.xpath("//a[@class='skip-ad']")).size() != 0)
	         {
	       	  driver.findElement(By.xpath("//a[@class='skip-ad']")).click();
	       	  i = i-1;
	         }
	         else
	         {
	       	  System.out.println("hiii");
	       	/*  driver.findElement(By.xpath("//div[contains(@class,'view-one-page')]//span[starts-with(text(),' "+i+" of')]/following-sibling::a")).click();
	       	  WebElement s = driver.findElement(By.xpath("//div[contains(@class,'view-one-page')]//span[starts-with(text(),' 2 of')]//ancestor::div[@class='row-gallery']//div[contains(@class,'row-gallery')]/p/a"));
	       	  System.out.println(s.getAttribute("href"));
	       	  String hrfs = s.getAttribute("href");*/
	       	  String xpath = null;
	       	  By pricePath= By.xpath("(//div[contains(@class,'row-gallery-text')])[1]//span[contains(text(),'$')]");
	       	  By linkPathInSlide= By.xpath("(//div[contains(@class,'row-gallery-text')])[1]//a[contains(text(),'.com')]");
	       	  boolean isPricePathPresent= CommonMethods.isElementPresent(driver, pricePath,xpath);
	       	  if(isPricePathPresent)
	       	  {
	       		  WebElement linkElement= driver.findElement(linkPathInSlide);
	       		  String hrefElement= linkElement.getAttribute("href");
	       		String linkText= linkElement.getText();
	       	  }
	       	  //sites.add(hrfs);
	         }
	   }    
		
		//return sites;
		
	}

	public static void linksWithMultiProductsCheck(WebDriver driver, String linkTocheck)
	{
		
		if(linkTocheck.equals(ElementLocator.linkWithMultiProducts_1) || linkTocheck.equals(ElementLocator.linkWithMultiProducts_2))
		{
			//driver.get(linkTocheck);
			if(linkTocheck.equals(ElementLocator.linkWithMultiProducts_1))
			{
				List<WebElement> allProducts=driver.findElements(ElementLocator.linkWithMultiProducts_1_path);
				 allProducts.get(0).click();
				
			}
			else
			{
				List<WebElement> allProducts=driver.findElements(ElementLocator.linkWithMultiProducts_2_path);
				 allProducts.get(0).click();
				
			}
			
		}
	}
	
	public static String getXpathFormultiresultURL(String url)
	{
		String xpath=null;
		String sourcefilePath = System.getProperty("user.dir") + "\\TestData_InputExcelFile\\xpathAndlinksWithmultiResults";
		try
		{
			//xpath= ReadDataFromExcel.readCellValueByKey_FromExcel(sourcefilePath, "XpathsexcelNew.xlsx", "xpaths", url);
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return xpath;
	}
	              
}
