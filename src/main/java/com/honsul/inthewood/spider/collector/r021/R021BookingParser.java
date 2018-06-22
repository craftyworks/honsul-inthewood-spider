package com.honsul.inthewood.spider.collector.r021;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.parser.JsoupBookingParser;

/**
 * 치악산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R021")
public class R021BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://chiak.huyang.co.kr/page/rsv_list.asp";
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    Document doc = Jsoup.connect(CONNECT_URL).get();
    documentList.add(doc);
    
    //next month
    Element elm = doc.selectFirst("form[name=form_next]");
    if(elm != null) {
      String year = elm.selectFirst("input[name=wh_year]").val();
      String month = elm.selectFirst("input[name=wh_month]").val();
    
      documentList.add(Jsoup.connect(CONNECT_URL).data("wh_year", year).data("wh_month", month).post());
    }
    return documentList;
  }

  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>(); 
    String yearMonth = doc.select("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(3) > table > tbody > tr:nth-child(8) > td > table > tbody > tr > td > table > tbody > tr > td:nth-child(1) > font > strong").text();
    yearMonth = yearMonth.replace("년", "-").replaceAll("월|\\s", "");    
    
    for(Element tr : doc.select("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(3) > table > tbody > tr:nth-child(10) > td > table > tbody > tr > td > table > tbody > tr")) {
    	for(Element td : tr.select("td")) {
	        if(td.text().contains("일(SUN)") || td.text().contains("예약종료")) {
	          continue;
	        }
	        Elements roomToday = td.select("div[style*=font-weight]");
	        Elements roomFuture = td.select("div > a[href]");
	        
	        for(Element div : (roomFuture.size()>0?roomFuture:roomToday)) {
	        	String day = td.text().split(" ")[0];
	        	String roomNm = div.text();
	        	
	        	Booking booking = new Booking();
		        booking.setResortId(SpiderContext.getResortId());
		        booking.setBookingDt(LocalDate.parse(yearMonth+'-'+day, DateTimeFormatter.ofPattern("yyyy-M-d")));
		        booking.setRoomNm(roomNm);
		        bookingList.add(booking);	        
	        }
    	}
    }
    //System.out.println("bookingList: " + bookingList);  
    return bookingList;
  }
}
