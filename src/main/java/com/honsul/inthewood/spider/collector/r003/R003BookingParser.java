package com.honsul.inthewood.spider.collector.r003;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.parser.AbstractBookingParser;
import com.honsul.inthewood.core.util.WebDriverUtils;

/**
 * 광치자연휴양림 예약현황 파서.
 * <p>https 인증 오류 발생하여 WebDriver 로 파싱함.
 */
@BookingParser(resortId="R003")
public class R003BookingParser extends AbstractBookingParser {
  
  private static final String CONNECT_URL = "https://www.kwangchi.or.kr:451/reservation.asp?location=002";
  
  public R003BookingParser() {
    super(CONNECT_URL);
  }
  
  protected Document thisMonth() {
    return WebDriverUtils.getDocument(CONNECT_URL);
  }
  
  protected Document nextMonth(Document doc) {
    return WebDriverUtils.getDocument(CONNECT_URL, (driver) -> {
      driver.findElement(By.cssSelector("input.next")).click();
    });
  }
  
  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();

    for(Element elm : doc.select("#contents form[action='/reservation.asp?location=002_02']")) {
      String[] attr = elm.select("input[name='rsv_info']").attr("value").split("#@");
      String bookingDt = attr[2];
      String roomNm = elm.select("button").text().replaceAll("\\*", "");
      Booking booking = new Booking();
      booking.setResortId(SpiderContext.getResortId());
      booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      booking.setRoomNo(roomNm);
      booking.setRoomNm(roomNm);
      bookingList.add(booking);
    }
    
    logger.debug("extracted Booking List count : {}", bookingList.size());
    return bookingList;
  }
  
}
