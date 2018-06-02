package com.honsul.inthewood.spider.collector.r011;

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
 * 강씨봉자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R011")
public class R011BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://gangssibong.gg.go.kr/reservation.asp?location=002";
  
  @Override
  protected Document document() throws IOException {
    return Jsoup.connect(CONNECT_URL).get();
  }
  
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();
    for(Element row : doc.select("form[action^=reservation.asp]")) {
      String[] attr = row.selectFirst("input[name=rsv_info]").attr("value").split("#@");
      String roomNo = attr[1];
      String bookingDt = attr[2];
      String roomType = attr[5];
      String roomNm = row.selectFirst("button").text().replaceAll("\\*", "");
      Booking booking = new Booking();
      booking.setResortId(SpiderContext.getResortId());
      booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      booking.setRoomNo(roomNo);
      booking.setRoomNm(roomNm);
      bookingList.add(booking);
    }
    
    return bookingList;
  }

}
