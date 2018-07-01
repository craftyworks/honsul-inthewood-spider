package com.honsul.inthewood.spider.collector.r028;

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
 * 평창자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R028")
public class R028BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "https://www.ddnayo.com/RsvSys/Calendar.aspx?id_hotel=1985";

  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();    
    
    Document doc = Jsoup.connect(CONNECT_URL).post();
    documentList.add(doc);
    
    String EVENTARGUMENT = doc.selectFirst("#ctt_ctt_cal > table.title > tbody > tr > td.td > span.nxt_m > a[href]").attr("href").split("\\(")[1].split("\\)")[0];
    String EVENTTARGET = EVENTARGUMENT.split("\\,")[0].replaceAll("\\'", "");
    EVENTARGUMENT = EVENTARGUMENT.split("\\,")[1].replaceAll("\\'", "");
    
	String VIEWSTATEGENERATOR = doc.select("div.aspNetHidden > input[name=__VIEWSTATEGENERATOR]").val();
	String EVENTVALIDATION = doc.select("div.aspNetHidden > input[name=__EVENTVALIDATION]").val();
	
    Element elm = doc.selectFirst("form[id=form1]");
    if(elm != null) {
      String VIEWSTATE = elm.selectFirst("input[name=__VIEWSTATE]").val();      
      doc = Jsoup.connect(CONNECT_URL).data("ctl00$ctl00$ctt$ctt$sm", "ctl00$ctl00$ctt$ctt$upp|"+EVENTTARGET).data("__EVENTTARGET", EVENTTARGET).data("__EVENTARGUMENT", EVENTARGUMENT).data("__LASTFOCUS", "")
    		  .data("__VIEWSTATE", VIEWSTATE).data("__VIEWSTATEGENERATOR", VIEWSTATEGENERATOR).data("__EVENTVALIDATION", EVENTVALIDATION).data("__ASYNCPOST", "true").post();
      documentList.add(doc);
    }   
    return documentList;
  }
  
  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>(); 
    
    for(Element tr : doc.select("#ctt_ctt_cal > table.cal > tbody > tr")) {
    	for(Element li : tr.select("td > div.tdrooms > ul > li")) { 
	    	if(li.select("span").text().equals("가")) {
		    	String bookingDt = li.select("a").attr("href").split("dt_s=")[1];	
		        String roomNm = li.select("a").text().split("\\(")[0];	
		                	
		    	Booking booking = new Booking();
		        booking.setResortId(SpiderContext.getResortId());
		        booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		        booking.setRoomNm(roomNm);
		        bookingList.add(booking);	    
	    	}	    	
    	}
    }
    //System.out.println("bookingList: " + bookingList);  
    return bookingList;
  }
}