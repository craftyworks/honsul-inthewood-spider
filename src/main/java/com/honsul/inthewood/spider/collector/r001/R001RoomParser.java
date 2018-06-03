package com.honsul.inthewood.spider.collector.r001;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;
import com.honsul.inthewood.core.util.WebDriverUtils;

/**
 * 좌구산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R001")
public class R001RoomParser implements Parser<Room>{

  private static final String ENTRY_POINT_URL = "http://jwagu.jp.go.kr/reservation.do";
  
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private R001RoomParser thisMonth(WebDriver driver) {
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
  
  private List<Room> extract(WebDriver driver) {
    System.out.println(driver.findElement(By.tagName("body")).toString());
    List<Room> roomList = new ArrayList<>();
    
    String roomTypeNm = null;
    for(WebElement row : driver.findElements(By.cssSelector("#tableBody>tr"))) {
      List<WebElement> th = row.findElements(By.tagName("th"));
      if(th.size() > 1) {
        roomTypeNm = th.get(0).getText().replaceAll("\\s+", "");
      }
      
      List<WebElement> bookingIcons = row.findElements(By.cssSelector("td>img.cp"));
      if(!CollectionUtils.isEmpty(bookingIcons)) {
        WebElement elm = bookingIcons.get(0);
        String onClick = elm.getAttribute("onclick");
        Pattern p = Pattern.compile("^resWriteCheck\\((.*)\\);$");
        Matcher m = p.matcher(onClick);
        if(m.find()) {
          //"A", "3101", "길잡이별(114)", "4", "2018-05-17", "2018-05-18", 1, "1", "70000", "50000"
          String[] arguments = m.group(1).split(",\\s*");
          arguments = Arrays.stream(arguments).map(s -> s.replaceAll("\"", "")).toArray(String[]::new);
          String roomNm = arguments[2];
          String occupancy = arguments[3];
          long peakPrice = Long.parseLong(arguments[8]);
          long price = Long.parseLong(arguments[9]);

          Room room = new Room();
          room.setResortId(SpiderContext.getResortId());
          room.setRoomNm(roomNm);
          room.setRoomType(RoomType.getRoomType(roomTypeNm));
          room.setNumberOfPeople(occupancy);
          room.setPeakPrice(peakPrice);
          room.setPrice(price);
          roomList.add(room);
        }
      }
    }
    return roomList;
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
  public List<Room> parse() {
    WebDriver driver = WebDriverUtils.createDriver();
    
    List<Room> roomList = new ArrayList<>();
    
    roomList.addAll(thisMonth(driver).extract(driver));
    
    driver.quit();
    
    return roomList;
  }
}
