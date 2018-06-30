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
    //System.out.println(doc);
    
    String EVENTARGUMENT = doc.selectFirst("#ctt_ctt_cal > table.title > tbody > tr > td.td > span.nxt_m > a[href]").attr("href").split("\\(")[1].split("\\)")[0];
    String EVENTTARGET = EVENTARGUMENT.split("\\,")[0].replaceAll("\\'", "");
    EVENTARGUMENT = EVENTARGUMENT.split("\\,")[1].replaceAll("\\'", "");
    
	String VIEWSTATEGENERATOR = doc.select("div.aspNetHidden > input[name=__VIEWSTATEGENERATOR]").val();
	String EVENTVALIDATION = doc.select("div.aspNetHidden > input[name=__EVENTVALIDATION]").val();
	
    Element elm = doc.selectFirst("form[id=form1]");
    if(elm != null) {
      String VIEWSTATE = elm.selectFirst("input[name=__VIEWSTATE]").val();
      
      System.out.println(EVENTARGUMENT +"/"+EVENTTARGET+"/"+VIEWSTATEGENERATOR+"/"+EVENTVALIDATION +"/"+VIEWSTATE);
      doc = Jsoup.connect(CONNECT_URL).data("ctl00$ctl00$ctt$ctt$sm", "ctl00$ctl00$ctt$ctt$upp|"+EVENTTARGET).data("__EVENTTARGET", EVENTTARGET).data("__EVENTARGUMENT", EVENTARGUMENT).data("__LASTFOCUS", "")
    		  .data("__VIEWSTATE", VIEWSTATE).data("__VIEWSTATEGENERATOR", VIEWSTATEGENERATOR).data("__EVENTVALIDATION", EVENTVALIDATION).data("__ASYNCPOST", "true").post();
      documentList.add(doc);
      System.out.println(doc);
    }
    
    
    return documentList;
  }
  // ctl00$ctl00$ctt$ctt$sm: ctl00$ctl00$ctt$ctt$upp|ctl00$ctl00$ctt$ctt$cal
  
  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>(); 
    
    for(Element td : doc.select("#reservation > table > tbody > tr > td")) {
    	if(td.text().length() == 0 || td.text().contains("예약종료")) {continue;}    
    	
    	for(Element form : td.select("form")) {
    		String[] attr = form.select("input[name='rsv_info']").attr("value").split("#@");
            String bookingDt = attr[2];
            String roomNm = form.text(); 
                    	
        	Booking booking = new Booking();
	        booking.setResortId(SpiderContext.getResortId());
	        booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	        booking.setRoomNm(roomNm);
	        bookingList.add(booking);	       
    	}
    }
    //System.out.println("bookingList: " + bookingList);  
    return bookingList;
  }
}