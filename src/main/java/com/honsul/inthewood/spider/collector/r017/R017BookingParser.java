package com.honsul.inthewood.spider.collector.r017;

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
 * 방화동자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R017")
public class R017BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://www.jangsuhuyang.kr/Banghwa1/reserve/list.asp";

  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    LocalDate now = LocalDate.now();
    
    Document doc = Jsoup.connect(CONNECT_URL).get();
    for(Element option : doc.select("select#ChoiceMonth > option")) {
      documentList.add(Jsoup.connect(CONNECT_URL).data("ChoiceMonth", option.val()).get());
    }    
    return documentList;
  }
  
  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();
    for(Element tr : doc.select("div.table-responsive > table > tbody > tr.tac")) {
      String roomNm = tr.selectFirst("td.tal > a > span").text();
      if("세미나실".equals(roomNm) || "단체식당".equals(roomNm)) {
        continue;
      }

      for(Element info : tr.select("img[alt^=가능]")) {
        String[] attr = info.attr("alt").split(",");
        String bookingDt = attr[2];

        Booking booking = new Booking();
        booking.setResortId(SpiderContext.getResortId());
        booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-M-d")));
        booking.setRoomNm(roomNm);
        bookingList.add(booking);
      }
    }
    
    return bookingList;
  }



}
