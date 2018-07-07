package com.honsul.inthewood.spider.collector.r001;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.util.WebDriverUtils;

/**
 * 좌구산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R001")
public class R001BookingParser implements Parser<Booking>{
  
  private static final String ENTRY_POINT_URL = "http://jwagu.jp.go.kr/reservation.do";
  
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private R001BookingParser thisMonth(WebDriver driver) {
    logger.debug("opening : {}", ENTRY_POINT_URL);
    driver.get(ENTRY_POINT_URL);
    
    driver.findElement(By.className("qhuyang")).click();
    
    driver.findElement(By.className("button02")).click();
    
    Set<String> windowHandles = driver.getWindowHandles();    // get  window id of current window
    String popupHandle = null;
    for(String id : windowHandles) {
      popupHandle = id;
    }
    driver.switchTo().window(popupHandle);
    
    waitForJQuery(driver);
    
    return this;
  }
  
  private R001BookingParser nextMonth(WebDriver driver) {
    logger.debug("moving to next month");

    WebElement btnNext = driver.findElement(By.cssSelector("input.next"));
    if(btnNext.isDisplayed()) {
      btnNext.click();
      waitForJQuery(driver);
      return this;
    }
    
    return null;
  }
  
  private List<Booking> extract(WebDriver driver) {
    List<Booking> bookingList = new ArrayList<>();
    
    List<WebElement> bookingIcons = driver.findElements(By.cssSelector("#tableBody>tr>td>img.cp"));
    for(WebElement elm : bookingIcons) {
      String onClick = elm.getAttribute("onclick");
      Pattern p = Pattern.compile("^resWriteCheck\\((.*)\\);$");
      Matcher m = p.matcher(onClick);
      if(m.find()) {
        //"A", "3101", "길잡이별(114)", "4", "2018-05-17", "2018-05-18", 1, "1", "70000", "50000"
        String[] arguments = m.group(1).split(",\\s*");
        arguments = Arrays.stream(arguments).map(s -> s.replaceAll("\"", "")).toArray(String[]::new);
        String roomNm = StringUtils.substringBefore(StringUtils.substringBefore(arguments[2], "("), "-");
        String bookingDt = arguments[4];
        Booking booking = new Booking();
        booking.setResortId(SpiderContext.getResortId());
        booking.setRoomNm(roomNm);
        booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        bookingList.add(booking);
      }
    }
    logger.info("extracting bookings {}", bookingList.size());
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
    WebDriver driver = WebDriverUtils.createDriver();
    
    List<Booking> bookingList = new ArrayList<>();
    
    bookingList.addAll(thisMonth(driver).extract(driver));
    
    if(nextMonth(driver) != null) {
      bookingList.addAll(extract(driver));
    }
    
    driver.quit();
    
    return bookingList;
  }

  @Override
  public boolean accept(String resortId) {
    BookingParser annotation = this.getClass().getAnnotation(BookingParser.class);
    return resortId.equals(annotation.resortId());
  }
  
}
