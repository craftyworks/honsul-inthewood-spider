package com.honsul.inthewood.spider.collector.r007;

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

import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.parser.JsoupBookingParser;

/**
 * 가리산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R007")
public class R007BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://garisan.nowr-b.net/member/index.html";
  
  private static final Pattern PATTERN_PARAM = Pattern.compile("s_year=([0-9]+)&s_month=([0-9]+)&s_day=([0-9]+)&room_num=([0-9]+)&");
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    //this month
    LocalDate now = LocalDate.now();
    String url1 = CONNECT_URL + "?year=" + now.getYear() + "&month=" + now.getMonthValue();
    documentList.add(Jsoup.connect(url1).get());

    //next month
    LocalDate next = now.plusMonths(1);
    String url2 = CONNECT_URL + "?year=" + next.getYear() + "&month=" + next.getMonthValue();
    documentList.add(Jsoup.connect(url2).get());
    
    return documentList;
  }
  
  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();
    for(Element row : doc.select("a[href^=http://garisan.nowr-b.net/m_member/room_check.html]")) {
      if(!"darkgreen".equals(row.selectFirst("font").attr("color"))) {
        continue;
      }
      String queryString = StringUtils.substringAfter(row.attr("href"), "?");

      Matcher matcher = PATTERN_PARAM.matcher(queryString);
      if(matcher.find()) {
        String bookingDt = matcher.group(1) + matcher.group(2) + matcher.group(3);
        String roomNm = row.selectFirst("font").text();

        if(StringUtils.contains(roomNm, "소형산막")) {
          //소형산막은 제외
          continue;
        }
        bookingList.add(Booking.of(
            LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyyMMdd")),
            roomNm
        ));
      }
    }
    
    return bookingList;
  }

}
