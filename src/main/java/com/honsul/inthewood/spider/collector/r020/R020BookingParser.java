package com.honsul.inthewood.spider.collector.r020;

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
 * 춘천숲자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R020")
public class R020BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://cforest.cafe24.com/sys/bbs/board.php";
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    LocalDate next = LocalDate.now().plusMonths(1);
    
    documentList.add(Jsoup.connect(CONNECT_URL).data("bo_table","booking").get());
    documentList.add(Jsoup.connect(CONNECT_URL).data("bo_table","booking").data("select", next.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).get());
    
    return documentList;
  }

  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>(); 

    for(Element tr : doc.select("#calendar > table > tbody > tr")) {
    	for(Element td : tr.select("td")) {
    		if (td.select("p").size() == 0 || td.select("p > a > span.open").size() == 0) {
    			continue;
    		}
    		for(Element p : td.select("p")) {
    			if (p.select("a > span.open").size() != 0) {
	    			String room = p.select("a > span.open").text();
	    			if (room.contains("캠핑장") || room.contains("글램핑")) {
	            		continue;
	            	}
	    			String day = p.select("a[href]").attr("href").split("\\=")[2];
		        	String[] roomNm = room.split("\\.|\\(");
		    		Booking booking = new Booking();
			        booking.setResortId(SpiderContext.getResortId());
			        booking.setBookingDt(LocalDate.parse(day, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			        booking.setRoomNm(roomNm[1].replaceAll("\\s+|　",""));
			        bookingList.add(booking);
    			}
    		}        	
	    }
    }
    //System.out.println("bookingList: " + bookingList);  
    return bookingList;
  }
}
