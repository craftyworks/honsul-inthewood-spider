package com.honsul.inthewood.spider.collector.r002;

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
 * 충북알프스자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R002")
public class R002BookingParser extends JsoupBookingParser {
  
  private static final String CONNECT_URL = "http://alpshuyang.boeun.go.kr/reservation.asp?location=002";
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    //this month
    Document doc = Jsoup.connect(CONNECT_URL).get();
    documentList.add(doc);

    //next month
    Element elm = doc.selectFirst("form[name=form_next]");
    if(elm != null) {
      String year = elm.selectFirst("input[name=wh_year]").val();
      String month = elm.selectFirst("input[name=wh_month]").val();
    
      Document next = Jsoup.connect(CONNECT_URL).data("wh_year", year).data("wh_month", month).post();
      documentList.add(next);
    }
    
    return documentList;
  }
  
  @Override
  public List<Booking> extract(Document doc) {

    List<Booking> bookingList = new ArrayList<>();
    for(Element row : doc.select("#contents form[action^=reservation.asp]")) {
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
