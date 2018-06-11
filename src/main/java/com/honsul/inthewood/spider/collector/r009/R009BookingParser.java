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
import com.honsul.inthewood.core.parser.JsoupBookingParser;
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 구재봉자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R009")
public class R009BookingParser extends JsoupBookingParser {

  private static final String[] CONNECT_URLS = {
      "http://hadongforest.co.kr/?r=MAIN&m=hyr&kind=108", 
      "http://hadongforest.co.kr/?r=MAIN&m=hyr&kind=110"
  };
    
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    for(String url : CONNECT_URLS) {
      documentList.add(Jsoup.connect(url).get());
      
      LocalDate next = LocalDate.now().plusMonths(1);
      String nextUrl = url + "&yy=" + next.getYear() + "&mm=" + next.getMonthValue();
      documentList.add(Jsoup.connect(nextUrl).get());
    }
    
    return documentList;
  }
  
  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();

    String year = doc.selectFirst("#yy>option[selected]").val();
    String month = StringUtils.leftPad(doc.selectFirst("#mm>option[selected]").val(), 2, '0');
    
    for(Element row : doc.select("a[href^=javascript:reserve(]")) {
      String roomNm = row.text();
      if(StringUtils.contains(roomNm, "세미나실")) {
        continue;
      }      
      String roomNo = TextUtils.contains(roomNm, "숲속휴양관", "본관", "숙박동") ? StringUtils.substringBefore(roomNm, "(") : StringUtils.substringAfter(roomNm, ")");

      String attr = row.attr("href");
      //javascript:reserve('56', '1', '1', '2018-05-20')
      Pattern p = Pattern.compile("javascript:reserve\\('[0-9]+', '[0-9]+', '([0-9]+)',");
      Matcher matcher = p.matcher(attr);
      if(matcher.find()) {
        String bookingDt = year + month + StringUtils.leftPad(matcher.group(1), 2, '0');
        Booking booking = new Booking();
        booking.setResortId(SpiderContext.getResortId());
        booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyyMMdd")));
        booking.setRoomNm(StringUtils.trim(roomNo));
        if(LocalDate.now().isAfter(booking.getBookingDt())) {
          continue;
        }
        bookingList.add(booking);
      }
    }
    
    return bookingList;
  }

}
