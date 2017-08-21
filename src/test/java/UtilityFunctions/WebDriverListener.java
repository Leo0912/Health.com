package UtilityFunctions;

import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

//import com.Salesforce.Tests.BaseTest;
//import com.Salesforce.UtilityFunctions.ScreenShotListener;
 
public class WebDriverListener implements IInvokedMethodListener {
//com.Salesforce.UtilityFunctions.ScreenShotListener screenList= new com.Salesforce.UtilityFunctions.ScreenShotListener();
	
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            String browserName = method.getTestMethod().getXmlTest().getLocalParameters().get("browserName");
            WebDriver driver = LocalDriverFactory.createInstance(browserName);
            LocalDriverManager.setWebDriver(driver);
        }
    }
 
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            WebDriver driver = LocalDriverManager.getDriver();
            if(!testResult.isSuccess())
            {
            	//BaseTest.takeScreenShot(testResult);
            }
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
