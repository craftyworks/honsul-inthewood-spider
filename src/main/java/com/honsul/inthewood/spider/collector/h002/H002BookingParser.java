package com.honsul.inthewood.spider.collector.h002;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;

/**
 * 충북알프스자연휴양림 예약현황 파서.
 */
@BookingParser(hotelId="H002")
public class H002BookingParser implements Parser<Booking>{
  private static final String CONNECT_URL = "http://alpshuyang.boeun.go.kr/reservation.asp?location=002";
  
  private Document thisMonth() {
    try {
      return Jsoup.connect(CONNECT_URL).get();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private Document nextMonth(Document doc) {
    Element elm = doc.selectFirst("form[name=form_next]");
    String year = elm.selectFirst("input[name=wh_year]").val();
    String month = elm.selectFirst("input[name=wh_month]").val();
    
    try {
      return Jsoup.connect(CONNECT_URL).data("wh_year", year).data("wh_month", month).post();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private List<Booking> extract(Document doc) {

    List<Booking> bookingList = new ArrayList<>();
    for(Element row : doc.select("#contents form[action^=reservation.asp]")) {
      String[] attr = row.selectFirst("input[name=rsv_info]").attr("value").split("#@");
      String roomNo = attr[1];
      String bookingDt = attr[2];
      String roomType = attr[5];
      String roomNm = row.selectFirst("button").text().replaceAll("\\*", "");
      Booking booking = new Booking();
      booking.setHotelId(SpiderContext.getHotelId());
      booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      booking.setRoomNo(roomNo);
      booking.setRoomNm(roomNm);
      bookingList.add(booking);
    }
    
    return bookingList;
  }
  
  @Override
  public List<Booking> parse() {
    
    List<Booking> bookingList = new ArrayList<>();
    
    Document doc = thisMonth();
    
    bookingList.addAll(extract(doc));
    
    bookingList.addAll(extract(nextMonth(doc)));

    return bookingList;
  }
  
}
