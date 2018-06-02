package com.honsul.inthewood.spider.collector.r009;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.parser.AbstractBookingParser;
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 봉수산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R009")
public class R009BookingParser extends AbstractBookingParser {

  private static final String CONNECT_URL = "http://hadongforest.co.kr/?r=MAIN&m=hyr&kind=108";
  
  public R009BookingParser() {
    super(CONNECT_URL);
  }
  
  protected Document nextMonth(Document doc) throws IOException {
    LocalDate next = LocalDate.now().plusMonths(1);
    String url = CONNECT_URL + "&yy=" + next.getYear() + "&mm=" + next.getMonthValue();
    return Jsoup.connect(url).get();
    
  }
  
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();

    String year = doc.selectFirst("#yy>option[selected]").val();
    String month = StringUtils.leftPad(doc.selectFirst("#mm>option[selected]").val(), 2, '0');
    
    for(Element row : doc.select("a[href^=javascript:reserve(]")) {
      String title = row.text();
      String roomTypeNm = TextUtils.substringBefore(title, "(");
      String roomNm = TextUtils.substringAfter(title, ")");
      if(StringUtils.isEmpty(roomNm)) {
        continue;
      }
      String attr = row.attr("href");
      //javascript:reserve('56', '1', '1', '2018-05-20')
      Pattern p = Pattern.compile("javascript:reserve\\('[0-9]+', '[0-9]+', '([0-9]+)',");
      Matcher matcher = p.matcher(attr);
      if(matcher.find()) {
        String bookingDt = year + month + StringUtils.leftPad(matcher.group(1), 2, '0');
        Booking booking = new Booking();
        booking.setResortId(SpiderContext.getResortId());
        booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyyMMdd")));
        booking.setRoomNo(roomNm);
        booking.setRoomNm(roomNm);
        if(LocalDate.now().isAfter(booking.getBookingDt())) {
          continue;
        }
        bookingList.add(booking);
      }
    }
    
    return bookingList;
  }

}
