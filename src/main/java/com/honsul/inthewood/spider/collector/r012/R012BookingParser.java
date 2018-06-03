package com.honsul.inthewood.spider.collector.r012;

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
import com.honsul.inthewood.core.parser.JsoupBookingParser;

/**
 * 축령산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R012")
public class R012BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://chukryong.gg.go.kr/reservation.asp?location=002";

  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    LocalDate now = LocalDate.now();
    
    //숲속의 집
    documentList.add(Jsoup.connect(CONNECT_URL)
      .data("wh_year", now.format(DateTimeFormatter.ofPattern("yyyy")))
      .data("wh_month", now.format(DateTimeFormatter.ofPattern("MM")))
      .data("man", "1").post());
    
    //산립휴양관
    documentList.add(Jsoup.connect(CONNECT_URL)
        .data("wh_year", now.format(DateTimeFormatter.ofPattern("yyyy")))
        .data("wh_month", now.format(DateTimeFormatter.ofPattern("MM")))
        .data("man", "2").post());
    
    return documentList;
  }
  
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();
    for(Element row : doc.select("form[action^=reservation.asp]")) {
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
