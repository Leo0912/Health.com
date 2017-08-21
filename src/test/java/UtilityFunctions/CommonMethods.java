package UtilityFunctions;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ObjectRepository.ElementLocator;

public class CommonMethods 

{
	private WebDriver driver;
	
	public static boolean isElementPresent1(WebElement e) 
	{
	    boolean flag = true;
	    try {
	        e.isDisplayed();
	        flag = true;
	    }
	    catch (Exception ex) {
	        flag = false;
	    }
	    return flag;
	}
	public static boolean isElementPresent(WebDriver driver, By xPath, String linktext) 
	{
	    boolean flag = true;
	    try {
	    	//xPath.isDisplayed();
	    	//Thread.sleep(2000);
	    	if (linktext.contains("birchbox") || linktext.contains("target")){
	    	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    	}
	    	driver.findElement(xPath).isDisplayed();
	    	
	        flag = true;
	    }
	    catch (Exception ex) {
	        return false;
	    }
	    return flag;
	}
	
	public static boolean isWebElementPresent(WebDriver driver, By elementPath)
	{
		boolean isPresent=false;
		try
		{
			//By modalPopup= By.xpath("//div[contains(@id,'modal')]");
			//By modalPopup= By.xpath("//button[contains(@class,'ui-dialog')]");
			boolean modalPresnt= driver.findElement(elementPath).isDisplayed();
			if(modalPresnt)
				isPresent=true;
			else
				isPresent=false;
				

		}
		catch(Exception  ex)
		{
			return isPresent=false;
		}
		return isPresent;
	} 

	public static boolean isSlidePresent(WebDriver driver, By xpathofSlide) 
	{
				
			boolean isSlidePresent=false;
			try
			{
				isSlidePresent= isSlidePresent=driver.findElement(xpathofSlide).isDisplayed();
			}
			catch(Exception ex)
			{
				return false;
			}
			return isSlidePresent;

	} 
	
	public static Map<String, String> addLinksInSlideToAList(WebDriver driver, By dotcomLinks_Path)
	{
		Map<String, String> linkTextAndHrefsMap= new LinkedHashMap<String, String>();
		try
		{
			
			//
			By noOfpagesInSlide_path= By.xpath("//div[contains(@class,'view-one-page')]//span[starts-with(text(),' 1 of')]");
			String textWithPages= driver.findElement(noOfpagesInSlide_path).getText().toString();
			String[] splitText= textWithPages.split("of");
			int noOfPages= Integer.valueOf(splitText[1].toString().trim());
			for(int i=1;i<=noOfPages; i++)
			{
				List<WebElement> myElements = driver.findElements(dotcomLinks_Path);

				// print the total number of elements
				System.out.println("Total selected rows are " + myElements.size());

				// Now using Iterator we will iterate all elements
				Iterator<WebElement> iter = myElements.iterator();

				// this will check whether list has some element or not
				while (iter.hasNext()) {

					// Iterate one by one
					WebElement item = iter.next();
					int indexoflink= myElements.indexOf(item);
					// get the link text
					
					String childLinkText =  item.getAttribute("innerText");
					System.out.println("Child Link Text is " + childLinkText);
					
					// get the href
					String childLink = item.getAttribute("href");

					// print the text
					System.out.println("Child Link is " + childLink);
					if (childLink != "")
						//slideLinksList.add(childLink);
						linkTextAndHrefsMap.put(indexoflink+"_"+childLinkText, childLink);
				//
		
				
				}
			}
		}
				
		catch(Exception ex)
		{
			System.out.println(ex);
		}
			
		return linkTextAndHrefsMap;
		

		
	}
	
	public static Map<String,String> addLinksInNormalPageToAList(WebDriver driver, By dotcomLinks_Path)
	{
		Map<String, String> linkTextAndHrefsMap= new LinkedHashMap<String, String>();
		try
		{
			
			
				List<WebElement> myElements = driver.findElements(dotcomLinks_Path);

				// print the total number of elements
				//System.out.println("Total selected rows are " + myElements.size());

				// Now using Iterator we will iterate all elements
				Iterator<WebElement> iter = myElements.iterator();

				// this will check whether list has some element or not
				while (iter.hasNext()) {

					// Iterate one by one
					WebElement item = iter.next();
					int indexoflink= myElements.indexOf(item);
					// get the link text
					
					String childLinkText =  item.getAttribute("innerText");
					//System.out.println("Child Link Text is " + childLinkText);
					
					// get the href
					String childLink = item.getAttribute("href");

					// print the text
					//System.out.println("Child Link is " + childLink);
					if (childLink != "")
						//slideLinksList.add(childLink);
						linkTextAndHrefsMap.put(indexoflink+"_"+childLinkText, childLink);
				
		
				
				}
			
		}
				
		catch(Exception ex)
		{
			System.out.println(ex);
		}
			
		return linkTextAndHrefsMap;
	}
	
	public static Map<String, String> getLinksPerSlide1(WebDriver driver) 
	{
		int tot_slides;
		int ads;
		Map<String, String> linkTextAndHrefsMap= new LinkedHashMap<String, String>();
		ArrayList<String> sites = new ArrayList<String>();
		String[] slidetext =driver.findElement(By.xpath("//div[contains(@class,'view-one-page')]//span[starts-with(text(),' 1 of')]")).getText().split(" ");
		int noOfpages = Integer.parseInt(slidetext[2]);
		ads = Math.round(noOfpages/3);
		tot_slides = noOfpages + ads + 1;
		System.out.println(tot_slides);
		
	//	2. Loop each slide say (25 times) and store the hrefs in arraylist(2.  check if its a Ad page, if yes-> continue)
		for(int i=1;i<=tot_slides;i++)
		{ 
			
			System.out.println(i);
			//Thread.sleep(5000);
	         if (i==1)
	         {
	       	  driver.findElement(By.xpath("//div[contains(@class,'view-one-page')]//span[starts-with(text(),' "+i+" of')]/following-sibling::a")).click();
	         }
	         
	         else if(driver.findElements(By.xpath("//a[@class='skip-ad']")).size() != 0)
	         {
	       	  driver.findElement(By.xpath("//a[@class='skip-ad']")).click();
	       	  i = i-1;
	         }
	         else{
	       	  System.out.println("hiii");
	       	  driver.findElement(By.xpath("//div[contains(@class,'view-one-page')]//span[starts-with(text(),' "+i+" of')]/following-sibling::a")).click();
	       	  WebElement s = driver.findElement(By.xpath("//div[contains(@class,'view-one-page')]//span[starts-with(text(),' 2 of')]//ancestor::div[@class='row-gallery']//div[contains(@class,'row-gallery')]/p/a"));
	       	  System.out.println(s.getAttribute("href"));
	       	  String hrfs = s.getAttribute("href");
	       	  String linkText= s.getText();
	       	  linkTextAndHrefsMap.put(linkText,hrfs);
	         }
	   }    
		
		return linkTextAndHrefsMap;
		
	}
	
	public static Map<String, String> getLinksPerSlide(WebDriver driver) throws InterruptedException
	{
		int tot_slides;
		int ads;
		Map<String, String> linkTextAndHrefsMap= new LinkedHashMap<String, String>();
		ArrayList<String> sites = new ArrayList<String>();
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
	       
	         }
	         
	         else if(driver.findElements(By.xpath("//a[@class='skip-ad']")).size() != 0)
	         {
	       	  driver.findElement(By.xpath("//a[@class='skip-ad']")).click();
	       	  i = i-1;
	         }
	         else
	         {
	       	  System.out.println("hiii");
	       	  By pricePath= By.xpath("(//div[contains(@class,'row-gallery-text')])[1]//span[contains(text(),'$')]");
	       	  By linkPathInSlide= By.xpath("(//div[contains(@class,'row-gallery-text')])[1]//a[contains(text(),'.com')]");
	       	  String txt=null;
	       	  boolean isPricePathPresent= CommonMethods.isElementPresent(driver, pricePath,txt);
	       	  if(isPricePathPresent)
	       	  {
	       		  WebElement linkElement= driver.findElement(linkPathInSlide);
	       		  String hrefElement= linkElement.getAttribute("href");
	       		  String linkText= linkElement.getText();
	       		  linkTextAndHrefsMap.put(i+"_"+linkText, hrefElement);
	       	  }
	         }
	   }    
		
		return linkTextAndHrefsMap;
		
	}

	public static void modalWindowCheckAndClose(WebDriver driver)
	{
		boolean isModalWindowPresent1 = CommonMethods.isWebElementPresent(driver, ElementLocator.modalPopup1);
		boolean isModalWindowPresent2 = CommonMethods.isWebElementPresent(driver, ElementLocator.modalPopup2);
		boolean isModalWindowPresent3 = CommonMethods.isWebElementPresent(driver, ElementLocator.modalPopup3);
		
		if (isModalWindowPresent1 || isModalWindowPresent2 || isModalWindowPresent3)
		{
			System.out.println("Modal window is dispalyed. Close window action is required");
			for (String winhandle : driver.getWindowHandles()) {
				driver.switchTo().window(winhandle);
			}
			
			if(isModalWindowPresent2)
			{
			
				boolean isClickBtnPresnt2= CommonMethods.isWebElementPresent(driver, ElementLocator.closeBtnPath_modalPopup1);
				if(isClickBtnPresnt2)
					driver.findElement( ElementLocator.closeBtnPath_modalPopup1).click();
				System.out.println("Looks like close button not found to close the popup");
				
					
			}
			
			else if(isModalWindowPresent1)
			{
			
				boolean isClickBtnPresnt1= CommonMethods.isWebElementPresent(driver, ElementLocator.closeBtnPath_modalPopup1);
				if(isClickBtnPresnt1)
					driver.findElement( ElementLocator.closeBtnPath_modalPopup1).click();
				else
					System.out.println("Looks like close button not found to close the popup");
			
			}
			
			else if(isModalWindowPresent3)
			{
			
				boolean isClickBtnPresnt1= CommonMethods.isWebElementPresent(driver, ElementLocator.closeBtnPath_modalPopup3);
				if(isClickBtnPresnt1)
					driver.findElement( ElementLocator.closeBtnPath_modalPopup3).click();
				else
					System.out.println("Looks like close button not found to close the popup");
			
			}
		}
	}
	
	public static void linksWithMultiProductsCheck(WebDriver driver, String linkTocheck, String xpath, String linktext)
	{
		try{
		List<WebElement> list_of_items1 = driver.findElements(By.xpath(""+xpath+"//a"));
		System.out.println(list_of_items1.size());
		if((linktext.contains("zappos") || linktext.contains("modcloth") ||  linktext.contains("coteshop") || linktext.contains("reebok") || linktext.contains("goop") || linktext.contains("pier1") || linktext.contains("grounds-and-hounds") || linktext.contains("lovewilddesign") || linktext.contains("lovewilddesign")) && list_of_items1.size()>0)
		{
			
			list_of_items1.get(0).click();
			
		}
		}
		catch(Exception e1){
			System.out.println("handle exception");
		}
		
		/*if(linkTocheck.equals(ElementLocator.linkWithMultiProducts_1) || linkTocheck.equals(ElementLocator.linkWithMultiProducts_2)
				||linkTocheck.equals(ElementLocator.linkWithMultiProducts_3))
		{
			if(linkTocheck.equals(ElementLocator.linkWithMultiProducts_1))
			{
				List<WebElement> allProducts=driver.findElements(ElementLocator.linkWithMultiProducts_1_path);
				 allProducts.get(0).click();
				
			}
			else if(linkTocheck.equals(ElementLocator.linkWithMultiProducts_2))
			{
				List<WebElement> allProducts=driver.findElements(ElementLocator.linkWithMultiProducts_2_path);
				 allProducts.get(0).click();
				
			}
			else if(linkTocheck.equals(ElementLocator.linkWithMultiProducts_3))
			{
				List<WebElement> allProducts=driver.findElements(ElementLocator.linkWithMultiProducts_3_path);
				 allProducts.get(0).click();
				
			}
			
		}*/
	}
	
}
