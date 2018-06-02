package com.honsul.inthewood.spider.collector.r007;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 가리산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R007")
public class R007BookingParser extends AbstractBookingParser {

  private static final String CONNECT_URL = "http://garisan.nowr-b.net/member/?co_id=garisan&ref=&buyer=&m_out=&c_area=";
  
  public R007BookingParser() {
    super(CONNECT_URL);
  }
  
  @Override
  protected Document nextMonth(Document doc) {
    return Jsoup.parse("");
  }
  
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();
    for(Element row : doc.select("a[href^=http://garisan.nowr-b.net/m_member/room_check.html]")) {
      if(!"darkgreen".equals(row.selectFirst("font").attr("color"))) {
        continue;
      }
      String queryString = TextUtils.substringAfter(row.attr("href"), "?");

      Pattern p = Pattern.compile("s_year=([0-9]+)&s_month=([0-9]+)&s_day=([0-9]+)&room_num=([0-9]+)&");
      Matcher matcher = p.matcher(queryString);
      if(matcher.find()) {
        String roomNo = matcher.group(4);
        String bookingDt = matcher.group(1) + matcher.group(2) + matcher.group(3);
        String roomNm = row.selectFirst("font").text();

        Booking booking = new Booking();
        booking.setResortId(SpiderContext.getResortId());
        booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyyMMdd")));
        booking.setRoomNo(roomNm);
        booking.setRoomNm(roomNm);
        bookingList.add(booking);
      }
    }
    
    return bookingList;
  }

}
