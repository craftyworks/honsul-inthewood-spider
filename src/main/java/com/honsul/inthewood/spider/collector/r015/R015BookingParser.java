package com.honsul.inthewood.spider.collector.r015;

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
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 남이자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R015")
public class R015BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://forestown.geumsan.go.kr/_prog/reserve/checkroom.php";
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    LocalDate now = LocalDate.now();
    LocalDate next = now.plusMonths(1);
    
    //this month
    documentList.add(Jsoup.connect(CONNECT_URL).data("code", "forest").data("year", ""+now.getYear()).data("mon", ""+now.getMonthValue()).post());
    documentList.add(Jsoup.connect(CONNECT_URL).data("code", "edu1").data("year", ""+now.getYear()).data("mon", ""+now.getMonthValue()).post());
    
    //next month
    documentList.add(Jsoup.connect(CONNECT_URL).data("code", "forest").data("year", ""+next.getYear()).data("mon", ""+next.getMonthValue()).post());
    documentList.add(Jsoup.connect(CONNECT_URL).data("code", "edu1").data("year", ""+next.getYear()).data("mon", ""+next.getMonthValue()).post());
    
    return documentList;
  }
  
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();
    
    for(Element row : doc.select("div.checkroom_tb > table.table2 > tbody > tr")) {
      String roomNm = StringUtils.substringBefore(row.selectFirst("th").text(), "-");
      for(Element tds : row.select("a[onclick^=apply]")) {
        String bookingDt = TextUtils.stripCursor(tds.attr("onclick"));
        bookingDt = bookingDt.split(",")[1];
        bookingDt = bookingDt.replaceAll("[\\s']", "");
        
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
