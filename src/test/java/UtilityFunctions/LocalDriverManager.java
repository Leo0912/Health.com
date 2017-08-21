package UtilityFunctions;

import org.openqa.selenium.WebDriver;

public class LocalDriverManager {
    public static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
 
    public static WebDriver getDriver() {
        return webDriver.get();
    }
 
    static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }
}