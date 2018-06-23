package com.honsul.inthewood.spider.collector.r022;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.parser.JsoupBookingParser;

/**
 * 안면도자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R022")
public class R022BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "https://www.anmyonhuyang.go.kr:453/reservation.asp?location=002";
  private static final String REFERER = "https://www.anmyonhuyang.go.kr:453/index.asp";
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    //get the session cookies
    Connection.Response res = Jsoup.connect(REFERER).ignoreContentType(true).method(Method.POST).execute();
    final Map<String, String> cookies = res.cookies();
    
    LocalDate now = LocalDate.now();
    Document doc = Jsoup.connect(CONNECT_URL).referrer(REFERER).cookies(cookies).data("wh_year", String.valueOf(now.getYear())).data("wh_month", String.valueOf(now.getMonthValue())).post();
    documentList.add(doc);
    
    //next month
    Element elm = doc.selectFirst("#contents > div.reservation > div > form[name=form_next]");
    if(elm != null) {
      String year = elm.selectFirst("input[name=wh_year]").val();
      String month = elm.selectFirst("input[name=wh_month]").val();
    
      documentList.add(Jsoup.connect(CONNECT_URL).referrer(REFERER).cookies(cookies).data("wh_year", year).data("wh_month", month).post());
    }
    return documentList;
  }

  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>(); 

    String yearMonth = doc.select("#contents > div.reservation > div > h3").text();
    yearMonth = yearMonth.replace("년", "-").replaceAll("월|\\s", "");    
    
    for(Element tr : doc.select("#contents > div.reservation > table > tbody > tr")) {
    	for(Element td : tr.select("td")) {
        	if(td.text().length() == 0 || td.text().contains("예약완료")) {
	          continue;
	        }
    		String day = td.text().split(" ")[0];
        	String roomNm = td.text().split(" ")[1];
        	
        	Booking booking = new Booking();
	        booking.setResortId(SpiderContext.getResortId());
	        booking.setBookingDt(LocalDate.parse(yearMonth+'-'+day, DateTimeFormatter.ofPattern("yyyy-M-d")));
	        booking.setRoomNm(roomNm);
	        bookingList.add(booking);	        
        
    	}
    }
    //System.out.println("bookingList: " + bookingList);  
    return bookingList;
  }
}