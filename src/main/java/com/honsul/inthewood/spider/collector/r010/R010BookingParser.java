package com.honsul.inthewood.spider.collector.r010;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.parser.AbstractBookingParser;
import com.honsul.inthewood.core.util.DateUtils;
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 군위장곡자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R010")
public class R010BookingParser extends AbstractBookingParser {

  private static final String CONNECT_URL = "https://janggok.gunwi.go.kr:6449/new/reservation/reserve_status.html?todayed=";
  
  public R010BookingParser() {
  }
  
  @Override
  protected Document thisMonth() throws IOException {
    return null;
  }
  
  @Override
  protected Document nextMonth(Document doc) throws IOException {
    return null;
  }

  @Override
  protected List<Booking> extractCustom() throws IOException {
    List<Booking> bookingList = new ArrayList<>();
    
    LocalDate now = LocalDate.now();
    LocalDate nextMonth = now.plusMonths(1);
    while(now.isBefore(nextMonth)) {
      String url = CONNECT_URL + DateUtils.getEpochSecond(now);
      System.out.println(url);
      
      bookingList.addAll(extract(Jsoup.connect(url).get()));
      now = now.plusWeeks(1);
    }
    
    return bookingList;
  }
  
  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();

    for(Element row : doc.select("#cntDetail table>tbody>tr")) {
      if(row.selectFirst("td") == null) {
        continue;
      }
      String title = row.selectFirst("td").text();
      String roomNm = TextUtils.substringBefore(title, "(");
      String space = TextUtils.stripCursor(title);
      
      for(Element link : row.select("a[href^=./reserve.html]")) {
        Pattern p = Pattern.compile("sdate=([0-9]+)\\s*&stateroom=([0-9A-Z_\\-]+)");
        Matcher matcher = p.matcher(link.attr("href"));
        if(matcher.find()) {
          LocalDate bookingDt = DateUtils.fromEpochSecond(Long.parseLong(matcher.group(1)));
          String roomNo = matcher.group(2);
          
          Booking booking = new Booking();
          booking.setResortId(SpiderContext.getResortId());
          booking.setBookingDt(bookingDt);
          booking.setRoomNo(roomNo);
          booking.setRoomNm(roomNm);

          bookingList.add(booking);          
        }
      }
    }
    
    return bookingList;
  }
}
