package com.honsul.inthewood.core.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtils {
  static {
    //System.setProperty("webdriver.chrome.driver", "E:/ProjectHome/tools/webdriver/chromedriver.exe");
    //System.setProperty("phantomjs.binary.path", "E:/ProjectHome/tools/webdriver/phantomjs.exe");
  }
  
  public static WebDriver createDriver() {
    return new PhantomJSDriver();
  }
  
  public static Document getDocument(String url) {
    WebDriver driver = createDriver();
    
    try {
      driver.get(url);
      
      return Jsoup.parse(driver.getPageSource());
    } finally {
      driver.quit();
    }
  }
  
  public static Document getDocument(String url, DriverCallback callback) {
    WebDriver driver = createDriver();
    try {
      driver.get(url);
      
      callback.callback(driver);
      
      return Jsoup.parse(driver.getPageSource());
    } finally {
      driver.quit();
    }
  }
  
  public static List<Document> getDocuments(String... urls) {
    WebDriver driver = new PhantomJSDriver();
    
    List<Document> docs = new ArrayList<>();
    try {
      for(String u : urls) {
        driver.get(u);
        docs.add(Jsoup.parse(driver.getPageSource()));
      }
    } finally {
      driver.quit();
    }
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
