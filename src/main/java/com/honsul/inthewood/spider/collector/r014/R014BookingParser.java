package com.honsul.inthewood.spider.collector.r014;

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
 * 소선암자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R014")
public class R014BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://rsof.cbhuyang.go.kr/reservation.asp?location=002";

  private Document getDocumnet(String year, String month, String type) throws IOException {
    return Jsoup.connect(CONNECT_URL)
        .data("wh_year", year)
        .data("wh_month", month)
        .data("man", type)
        .header("Referer", CONNECT_URL)
        .post();
  }
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    LocalDate now = LocalDate.now();
    String year = now.format(DateTimeFormatter.ofPattern("yyyy"));
    String month = now.format(DateTimeFormatter.ofPattern("MM"));
    
    //this month
    Document doc = null;
    for(int i = 1; i<= 5; i++) {
      doc = getDocumnet(year, month, ""+i);
      documentList.add(doc);
    }
    
    //next month
    Element elm = doc.selectFirst("form[name=form_next]");
    if(elm != null) {
      year = elm.selectFirst("input[name=wh_year]").val();
      month = elm.selectFirst("input[name=wh_month]").val();
      for(int i = 1; i<= 5; i++) {
        doc = getDocumnet(year, month, ""+i);
        documentList.add(doc);
      }  
    }
    
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
