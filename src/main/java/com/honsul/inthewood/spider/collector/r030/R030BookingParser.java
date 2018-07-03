package com.honsul.inthewood.spider.collector.r030;

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
 * 미숭산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R030")
public class R030BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://misungsan.co.kr/2014/rs_system/contents/reservation03_room.asp";
  private static final String REFERER = "http://misungsan.co.kr/2014/rs_system/contents/reservation03.asp";
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();    
    
    Document doc = Jsoup.connect(CONNECT_URL).referrer(REFERER).timeout(30000).get();
    documentList.add(doc);
    
    String date = doc.select("#container > div.contents > div > div > a:nth-child(3)").attr("href");
    doc = Jsoup.connect(CONNECT_URL).referrer(REFERER).data("a_date",date.split("a_date=")[1]).timeout(30000).get();
	documentList.add(doc);

    return documentList;
  }
  
  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>(); 
    
    for(Element tr : doc.select("table > tbody > tr")) {
    	for(Element li : tr.select("td > div > ul > li")) { 
    		if (li.select("a[href]").size() > 0) {
	    		String bookingDt = li.select("a").attr("href");	
	    		String year =bookingDt.split("yy=")[1].split("&mm")[0];
	    		String month = String.format("%02d", Integer.parseInt(bookingDt.split("mm=")[1].split("&dd")[0]));
	    		String day = String.format("%02d", Integer.parseInt(bookingDt.split("dd=")[1].split("&seqno")[0]));
		        String roomNm = li.text();	
		        
		    	Booking booking = new Booking();
		        booking.setResortId(SpiderContext.getResortId());
		        booking.setBookingDt(LocalDate.parse(year+month+day, DateTimeFormatter.ofPattern("yyyyMMdd")));
		        booking.setRoomNm(roomNm);
		        bookingList.add(booking);	    
    		}
    	}
    }
    //System.out.println("bookingList: " + bookingList);  
    return bookingList;
  }
}