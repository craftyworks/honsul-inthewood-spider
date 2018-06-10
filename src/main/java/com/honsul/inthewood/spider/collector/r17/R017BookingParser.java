package com.honsul.inthewood.spider.collector.r17;

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
 * 바라산 자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R017")
public class R017BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://forest.maketicket.co.kr/camp/reserve/calendar.jsp";

  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    
    //this month 
    Document doc = Jsoup.connect(CONNECT_URL)
        .data("idkey", "5M4230")
        .data("gd_seq", "GD83")
        .data("yyyymmdd", now)
        .data("sd_date", now)
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
