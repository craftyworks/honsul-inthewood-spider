package com.honsul.inthewood.spider.collector.r018;

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
 * 석모도자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R018")
public class R018BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://forest.ganghwa.go.kr/lodge/lodgeRoomSchedule.do";

  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    LocalDate now = LocalDate.now();
    
    documentList.add(Jsoup.connect(CONNECT_URL).data("schym", "" + now.getYear() + "-" + String.format("%02d", now.getMonthValue())).get());
    if (now.getMonthValue() < 12) {
    	documentList.add(Jsoup.connect(CONNECT_URL).data("schym", "" + now.getYear() + "-" + String.format("%02d", now.plusMonths(1).getMonthValue())).get());
    } else {
    	documentList.add(Jsoup.connect(CONNECT_URL).data("schym", "" + now.plusYears(1) + "-" + String.format("%02d", now.plusMonths(1).getMonthValue())).get());
    }
    //System.out.println(documentList);
    
    return documentList;
  }
  
  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();    
    
    for(Element tr : doc.select("div.calendarBox > table > tbody > tr.con")) {
      String roomNm = tr.selectFirst("th").text();
      if(roomNm.contains("회의실")) {
        continue;
      }      
      
      for(Element info : tr.select("td > a > span")) {       
    	String[] attr = info.attr("title").split("\\)");
        String bookingDt = attr[1].split("\\(")[0].replaceAll("\\s|일", "").replaceAll("년|월", "-");
        
        Booking booking = new Booking();
        booking.setResortId(SpiderContext.getResortId());
        booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-M-d")));
        booking.setRoomNm(roomNm);
        bookingList.add(booking);
      }
    }
    //System.out.println(bookingList);
    
    return bookingList;
  }



}
