package com.honsul.inthewood.spider.collector.r016;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.parser.JsoupBookingParser;

/**
 * 칠갑산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R016")
public class R016BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://chilgap.cheongyang.go.kr/include/reservation/reservation_002_01.asp";

  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    //this month
    Document doc = Jsoup.connect(CONNECT_URL)
        .referrer(CONNECT_URL)
        .data("man", "0")
        .data("wh_year", "2018")
        .data("wh_month", "6")
        .post();
        
    documentList.add(doc);
    
    //next month
    Element elm = doc.selectFirst("form[name=form_next]");
    if(elm != null) {
      String year = elm.selectFirst("input[name=wh_year]").val();
      String month = elm.selectFirst("input[name=wh_month]").val();
    
      documentList.add(Jsoup.connect(CONNECT_URL).data("wh_year", year).data("wh_month", month).post());
    }
    
    return documentList;
  }
  
  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();
    for(Element tr : doc.select("table.calendar > tbody > tr")) {
      if(tr.selectFirst("th") == null) {
        continue;
      }
      String roomNm = tr.selectFirst("th").text();
      roomNm = StringUtils.substringBefore(roomNm,  "(");
      if("회의실".equals(roomNm)) {
        continue;
      }

      for(Element info : tr.select("input[name=rsv_info]")) {
        String[] attr = info.attr("value").split("#@");
        String bookingDt = attr[2];

        Booking booking = new Booking();
        booking.setResortId(SpiderContext.getResortId());
        booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        booking.setRoomNm(roomNm);
        bookingList.add(booking);
      }
    }
    
    return bookingList;
  }



}
