package com.honsul.inthewood.spider.collector.r005;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.parser.AbstractBookingParser;

/**
 * 봉황자연휴양림 예약현황 파서.
 * 
 * <p>JSoup 으로 처리.
 */
@BookingParser(resortId="R005")
public class R005BookingParser extends AbstractBookingParser {

  private static final String CONNECT_URL = "http://bhf.cj100.net/reservation.asp?location=002";
  
  public R005BookingParser() {
    super(CONNECT_URL);
    header("Referer", "http://bhf.cj100.net/reservation.asp");
  }
  
  protected Document nextMonth(Document doc) {
    Element elm = doc.selectFirst("form[name=form_next]");
    String year = elm.selectFirst("input[name=wh_year]").val();
    String month = elm.selectFirst("input[name=wh_month]").val();
    
    try {
      return Jsoup.connect(CONNECT_URL).header("Referer", "http://bhf.cj100.net/reservation.asp").data("wh_year", year).data("wh_month", month).post();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();
    for(Element row : doc.select("#contents form[action=reservation.asp?location=002_00]")) {
      String[] attr = row.selectFirst("input[name=rsv_info]").attr("value").split("#@");
      String roomNo = attr[1];
      String bookingDt = attr[2];
      String roomType = attr[5];
      String roomNm = row.selectFirst("button").text().replaceAll("\\*", "");
      Booking booking = new Booking();
      booking.setResortId(SpiderContext.getResortId());
      booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      booking.setRoomNo(roomNm);
      booking.setRoomNm(roomNm);
      bookingList.add(booking);
    }
    
    return bookingList;
  }

}
