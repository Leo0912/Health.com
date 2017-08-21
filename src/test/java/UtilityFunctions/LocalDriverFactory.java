package UtilityFunctions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

//import com.Salesforce.Tests.BaseTest;
 
class LocalDriverFactory {
    static WebDriver createInstance(String browserName) {
        WebDriver driver = null;
        String DRIVER_LOC_Path ="";
        if (browserName.toLowerCase().contains("firefox")) {
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            return driver;
        }
        if (browserName.toLowerCase().contains("ie")) 
        {
        	
        	 
        	
        	DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "IE");
			capabilities.setCapability(InternetExplorerDriver.
					  INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			
			//capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			//InternetExplorerDriver ieOptions = new InternetExplorerDriver();
			
			capabilities.setCapability("javascriptEnabled", true);
			capabilities.setCapability("applicationCacheEnabled", true);
			capabilities.setCapability("nativeEvents", false);    
			capabilities.setCapability("unexpectedAlertBehaviour", "accept");
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			capabilities.setCapability("disable-popup-blocking", true);
			capabilities.setCapability("enablePersistentHover", true);
			capabilities.setCapability("ignoreZoomSetting", true);
			//capabilities.setCapability("ie.ensureCleanSession", false);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, false);
			//capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			
            //ieOptions.AddAdditionalCapability("javascriptEnabled", true);
            //ieOptions.AddAdditionalCapability("applicationCacheEnabled", true);
            //ieOptions.EnableNativeEvents = true;
            //ieOptions.IgnoreZoomLevel = true;
            //ieOptions.RequireWindowFocus = true;
			
			//delete if req
			/*if(BaseTest.REQUIREPROXY)
			{
       	 		//String PROXY  = "94.177.242.132" + ":" + "80";
				String PROXY  = BaseTest.PROXYIPADDRESS+":" + BaseTest.PROXYPORTNUMBER;
       	 		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
       	 		proxy.setHttpProxy(PROXY);
       	 		proxy.setFtpProxy(PROXY);
       	 		proxy.setSslProxy(PROXY);
       	 		capabilities.setCapability(CapabilityType.PROXY, proxy);
			}*/
       	//end of delete
			
			DRIVER_LOC_Path = System.getProperty("user.dir")+"\\lib"+"\\"+"IEDriverServer.exe";
			//System.setProperty("webdriver.ie.driver",DRIVER_LOC_Path);
			driver = new InternetExplorerDriver(capabilities);
            //driver = new InternetExplorerDriver();
            return driver;
        }
        if (browserName.toLowerCase().contains("chrome")) {
        	DRIVER_LOC_Path = System.getProperty("user.dir")+"\\lib"+"\\"+"chromedriver.exe";
			//System.setProperty("webdriver.chrome.driver",DRIVER_LOC_Path);
			driver = new ChromeDriver();
			driver.manage().window().maximize();
            //driver = new ChromeDriver();
            return driver;
        }
        return driver;
    }
}
