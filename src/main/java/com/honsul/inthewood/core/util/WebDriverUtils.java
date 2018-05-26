package com.honsul.inthewood.core.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtils {
  static {
    //System.setProperty("webdriver.chrome.driver", "E:/ProjectHome/tools/webdriver/chromedriver.exe");
    //System.setProperty("phantomjs.binary.path", "E:/ProjectHome/tools/webdriver/phantomjs.exe");
    System.setProperty("phantomjs.binary.path", "/home/ec2-user/tools/webdriver/phantomjs-2.1.1-linux-x86_64/bin/phantomjs");
  }
  
  public static WebDriver createDriver() {
    return new PhantomJSDriver();
  }
  
  public static Document getDocument(String url) {
    //WebDriver driver = new PhantomJSDriver();
    WebDriver driver = new ChromeDriver();
    
    driver.get(url);
    
    Document doc = Jsoup.parse(driver.getPageSource());

    driver.quit();
    
    return doc;
  }
  
  public static Document getDocument(String url, DriverCallback callback) {
    //WebDriver driver = new PhantomJSDriver();
    WebDriver driver = new ChromeDriver();
    
    driver.get(url);
    
    callback.callback(driver);
    
    Document doc = Jsoup.parse(driver.getPageSource());

    driver.quit();
    
    return doc;
  }
  
  public static List<Document> getDocuments(String... urls) {
    WebDriver driver = new PhantomJSDriver();
    
    List<Document> docs = new ArrayList<>();
    for(String u : urls) {
      driver.get(u);
      docs.add(Jsoup.parse(driver.getPageSource()));
    }
    
    driver.quit();
    return docs;
  }
  
  public static void waitForJQuery(WebDriver driver) {
    new WebDriverWait(driver, 5).until(
        new ExpectedCondition<Boolean>() {
          public Boolean apply(WebDriver d) {
            JavascriptExecutor js = (JavascriptExecutor) d;
            return (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
          }
        }
    );
  }
  
  public static interface DriverCallback {
    public void callback(WebDriver driver);
  }
}
