package com.honsul.inthewood.spider.collector.h003;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;

/**
 * 광치자연휴양림 예약현황 파서.
 */
@BookingParser(hotelId="H003")
public class H003BookingParser implements Parser<Booking>{
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private static final String ENTRY_POINT_URL = "https://www.kwangchi.or.kr:451/reservation.asp?location=002";
  
  private H003BookingParser thisMonth(WebDriver driver) {
    logger.debug("opening : {}", ENTRY_POINT_URL);
    driver.get(ENTRY_POINT_URL);
    
    waitForJQuery(driver);
    
    return this;
  }
  
  private H003BookingParser nextMonth(WebDriver driver) {
    logger.debug("moving to next month");
    driver.findElement(By.cssSelector("input.next")).click();
    
    waitForJQuery(driver);
    
    return this;
  }
  
  private List<Booking> extract(WebDriver driver) {
    List<Booking> bookingList = new ArrayList<>();
    
    List<WebElement> bookingIcons = driver.findElements(By.cssSelector("#contents form[action='/reservation.asp?location=002_02']"));
    for(WebElement elm : bookingIcons) {
      String[] attr = elm.findElement(By.cssSelector("input[name='rsv_info']")).getAttribute("value").split("#@");
      String bookingDt = attr[2];
      String roomNm = elm.findElement(By.tagName("button")).getText().replaceAll("\\*", "");
      Booking booking = new Booking();
      booking.setHotelId(SpiderContext.getHotelId());
      booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      booking.setRoomNo(roomNm);
      booking.setRoomNm(roomNm);
      bookingList.add(booking);
    }
    
    logger.debug("extracted Booking List count : {}", bookingList.size());
    return bookingList;
  }
  
  private void waitForJQuery(WebDriver driver) {
    new WebDriverWait(driver, 5).until(
        new ExpectedCondition<Boolean>() {
          public Boolean apply(WebDriver d) {
            JavascriptExecutor js = (JavascriptExecutor) d;
            return (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
          }
        }
    );
  }
  
  @Override
  public List<Booking> parse() {
    //System.setProperty("webdriver.chrome.driver", "E:/ProjectHome/tools/webdriver/chromedriver.exe");
    //WebDriver driver = new ChromeDriver();
    
    System.setProperty("phantomjs.binary.path", "E:/ProjectHome/tools/webdriver/phantomjs.exe");
    WebDriver driver = new PhantomJSDriver();
    
    List<Booking> bookingList = new ArrayList<>();
    
    bookingList.addAll(thisMonth(driver).extract(driver));
    
    bookingList.addAll(nextMonth(driver).extract(driver));
    
    driver.quit();
    
    logger.debug("parsed Booking List count : {}", bookingList.size());
    
    return bookingList;
  }
  
  public static void main(String[] args) {
    H003BookingParser parser = new H003BookingParser();
    parser.parse();
  }
}
