package com.honsul.inthewood.spider.collector.r003;

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
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;

/**
 * 광치자연휴양림 숙소현황 파서.
 * 
 * <p>예약현황 페이지에서 숙소 별 예약 페이지로 이동하여 숙소 정보를 추출
 */
@RoomParser(resortId="R003")
public class R003RoomParser implements Parser<Room>{

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private static final String HOUSE_FACILITY_URL = "https://www.kwangchi.or.kr:451/facilities.asp?location=002";
  
  private static final String RESORT_FACILITY_URL = "https://www.kwangchi.or.kr:451/facilities.asp?location=001";
  
  /**
   * 숲속의집 시설현황
   */
  private R003RoomParser house(WebDriver driver) {
    logger.debug("opening : {}", HOUSE_FACILITY_URL);
    driver.get(HOUSE_FACILITY_URL);
    
    waitForJQuery(driver);
    
    return this;
  }
  
  /**
   * 휴양관 시설현황
   */
  private R003RoomParser resort(WebDriver driver) {
    logger.debug("opening : {}", RESORT_FACILITY_URL);
    driver.get(RESORT_FACILITY_URL);
    
    waitForJQuery(driver);
    
    return this;
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
  
  private List<Room> extract(WebDriver driver) {
    List<Room> roomList = new ArrayList<>();
    
    String roomTypeNm = driver.findElement(By.cssSelector("div#contents>div.cont_title>h3")).getText().replaceAll("\\s*",  "");
    for(WebElement tr : driver.findElements(By.cssSelector("div#contents>div.facilities>table>tbody>tr"))) {
      List<WebElement> tds = tr.findElements(By.cssSelector("td"));
      String roomNm = tds.get(0).getText();
      String capacity = tds.get(1).getText();
      String occupancy = tds.get(2).getText();
      long peakPrice = Long.parseLong(tds.get(3).getText().replaceAll("[,\\s*]",  ""));
      String sPrice = tds.get(5).getText().replaceAll("[,\\s*]",  "");
      long price = Long.parseLong(sPrice.substring(0, sPrice.indexOf("(")));
      
      Room room = new Room();
      room.setResortId(SpiderContext.getResortId());
      room.setRoomNo(roomNm);
      room.setRoomNm(roomNm);
      room.setRoomType(getRoomType(roomTypeNm));
      room.setRoomTypeNm(roomTypeNm);
      room.setOccupancy(occupancy);
      room.setPeakPrice(peakPrice);
      room.setPrice(price);
      roomList.add(room);
    }
    return roomList;
  }
  
  private String getRoomType(String roomTypeNm) {
    return "숲속의집".equals(roomTypeNm) ? RoomType.HUT.toString() : RoomType.CONDO.toString();
  }

  @Override
  public List<Room> parse() {
    
    //System.setProperty("webdriver.chrome.driver", "E:/ProjectHome/tools/webdriver/chromedriver.exe");
    //WebDriver driver = new ChromeDriver();
    
    System.setProperty("phantomjs.binary.path", "E:/ProjectHome/tools/webdriver/phantomjs.exe");
    WebDriver driver = new PhantomJSDriver();
    
    List<Room> roomList = new ArrayList<>();
    
    roomList.addAll(house(driver).extract(driver));
    
    roomList.addAll(resort(driver).extract(driver));
    
    driver.quit();
    
    logger.debug("parsed Room List count : {}", roomList.size());
    
    return roomList;
  }
  
  public static void main(String[] args) throws Exception {
    R003RoomParser parser = new R003RoomParser();
    parser.parse();
  }
}
