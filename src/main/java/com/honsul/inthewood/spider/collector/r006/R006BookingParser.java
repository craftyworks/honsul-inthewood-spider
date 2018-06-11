package com.honsul.inthewood.spider.collector.r006;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.parser.WebDriverBookingParser;

/**
 * 계명산자연휴양림 예약현황 파서.
 */
//FIXME
//@BookingParser(resortId="R006")
public class R006BookingParser extends WebDriverBookingParser {

  private static final String CONNECT_URL = "https://gmf.cj100.net:453/main.asp";
  
  @Override
  protected Document thisMonth(WebDriver driver) {
    driver.get(CONNECT_URL);
    new Actions(driver)
      .moveToElement(driver.findElement(By.cssSelector("a[href='/reservation.asp']")))
      .click(driver.findElement(By.cssSelector("a[href='/reservation.asp?location=002']")))
      .perform();
    
    return Jsoup.parse(driver.getPageSource());
  }
  
  @Override
  protected Document nextMonth(WebDriver driver)  {
    driver.findElement(By.cssSelector("button.btn_next")).click();
    
    return Jsoup.parse(driver.getPageSource());
  }
  
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();
    for(Element row : doc.select("#contents form[action=reservation.asp?location=002_00]")) {
      String[] attr = row.selectFirst("input[name=rsv_info]").attr("value").split("#@");
      String bookingDt = attr[2];
      String roomType = attr[5];
      String roomNm = row.selectFirst("button").text().replaceAll("\\*", "");
      Booking booking = new Booking();
      booking.setResortId(SpiderContext.getResortId());
      booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      booking.setRoomNm(roomNm);
      bookingList.add(booking);
    }
    
    return bookingList;
  }

}
