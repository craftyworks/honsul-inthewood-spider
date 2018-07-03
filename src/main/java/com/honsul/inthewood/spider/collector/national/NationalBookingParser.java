package com.honsul.inthewood.spider.collector.national;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.parser.JsoupBookingParser;
import com.honsul.inthewood.core.util.NationalResortUtils;

/**
 * 국립자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="National")
public class NationalBookingParser extends JsoupBookingParser {

  protected static final Pattern P_LINK_PARAM = Pattern.compile("\\('\\w+','([^']+)','\\d+', '(\\d+)'");
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    Map<String, String> loginCookies = NationalResortUtils.loginCookies();
    for(Entry<String, String> entry : NationalResortInfo.MAPPINGS.entrySet()) {
      String resortId = entry.getKey();
      String departCode = entry.getValue();
      Document doc = NationalResortUtils.bookinDocument(departCode, loginCookies);
      if(doc != null) {
        doc.selectFirst("head").attr("RESORT_ID", resortId);
        documentList.add(doc);
      }      
    }
    return documentList;
  }
  
  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();    
    
    for(Element link : doc.select("div.ins_current_table_wrap > table.table_wrap_04 > tbody > tr > td a[href^=javascript:fnViewRoomQntt]")) {
          
      Matcher matcher = P_LINK_PARAM.matcher(link.attr("href"));
      if(matcher.find()) {
        Booking booking = new Booking();
        booking.setResortId(doc.selectFirst("head").attr("RESORT_ID"));
        booking.setBookingDt(LocalDate.parse(matcher.group(2), DateTimeFormatter.ofPattern("yyyyMMdd")));
        booking.setRoomNm(matcher.group(1).trim());
        
        bookingList.add(booking);        
      }
    }
    
    return bookingList;
  }



}
