package ObjectRepository;

import org.openqa.selenium.By;

public class ElementLocator 
{
	
	//public static By dotComLinks_Path = By.partialLinkText(".com");
	public static By dotComLinks_Path = By.xpath("//div[(contains(@class, 'item-deck-content') or contains(@class, 'article content body') or  contains(@class, 'caption')) and not(contains(@class, 'hidden'))]//a[contains(text(),'.com')]");
	public static By quantiyBtn_common_Path = By.xpath("//select/preceding::label[text()='QTY' or text()='qty' or text()='Qty' or text()='Quantity' or text()='Qty:']");
	public static By modalPopup1= By.xpath("//div[contains(@id,'modal')]");
	public static By modalPopup2= By.xpath("//button[contains(@class,'ui-dialog')]");
	public static By closeBtnPath_modalPopup1 = By.xpath("//a[@id='closeButton' or @class='ltkmodal-close']");
	public static By modalPopup3= By.xpath("(//div[contains(@id,'bx-creative')])[1] ");
	public static By closeBtnPath_modalPopup3 = By.xpath("(//div[contains(@id,'bx-creative')])[1]");  
	 			 
	public static String linkWithMultiProducts_1= "http://www.reebok.com/us/white-classic";
	public static By linkWithMultiProducts_1_path= By.xpath("//div[@class='product-tile']//a[contains(@class,'product-link')]");
	public static String linkWithMultiProducts_2="http://www.zappos.com/bernie-mev-lulia?PID=7883147&AID=11554337&utm_source=Time+Inc.&splash=none&utm_medium=affiliate&cjevent=214f0c4a78db11e780cb848f69840f81_265103985663963284%3AhoBDLhpEHym9";
	public static By linkWithMultiProducts_2_path= By.xpath("//div[@id='searchResults']//a");
	public static String linkWithMultiProducts_3="http://shop.nordstrom.com/s/munro-jordi-leather-loafer-women/4361620?cm_mmc=google-_-productads-_-33067285669_-_-94858396&rkg_id=h-08e06a9a8a432919c3421647e4840e2e_t-1498070067&adpos=1o1&creative=145503081107&device=c&network=g&gclid=CIy-zPHJz9QCFc5XDQodkikDfQ&siteId=93xLBvPhAeE-lBBHw6ei._VK5LTvAKZQCw";
	public static By linkWithMultiProducts_3_path= By.xpath("//ul[contains(@class,'results')]//li");

}