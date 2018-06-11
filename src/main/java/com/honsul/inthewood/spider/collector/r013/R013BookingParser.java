package com.honsul.inthewood.spider.collector.r013;

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
 * 박달재자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R013")
public class R013BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://baf.cbhuyang.go.kr/reservation.asp?location=002_01";

  private Document getDocumnet(String year, String month) throws IOException {
    return Jsoup.connect(CONNECT_URL)
        .data("wh_year", year)
        .data("wh_month", month)
        .data("man", "1")
        .header("Referer", CONNECT_URL)
        .post();
  }
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    LocalDate now = LocalDate.now();
    
    //this month
    Document doc = getDocumnet(now.format(DateTimeFormatter.ofPattern("yyyy")), now.format(DateTimeFormatter.ofPattern("MM")));
    documentList.add(doc);
    
    //next month
    Element elm = doc.selectFirst("form[name=form_next]");
    if(elm != null) {
      String year = elm.selectFirst("input[name=wh_year]").val();
      String month = elm.selectFirst("input[name=wh_month]").val();
      documentList.add(getDocumnet(year, month));      
    }
    
    return documentList;
  }
  
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();
    for(Element row : doc.select("form[action^=reservation.asp]")) {
      String[] attr = row.selectFirst("input[name=rsv_info]").attr("value").split("#@");
      String bookingDt = attr[2];
      String roomType = attr[5];
      String roomNm = StringUtils.substringBefore(row.selectFirst("button").text(), "-");
      Booking booking = new Booking();
      booking.setResortId(SpiderContext.getResortId());
      booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      booking.setRoomNm(roomNm);
      bookingList.add(booking);
    }
    
    return bookingList;
  }



}
