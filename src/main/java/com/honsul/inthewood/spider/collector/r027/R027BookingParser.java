package com.honsul.inthewood.spider.collector.r027;

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
 * 송이밸리산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R027")
public class R027BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "https://www.songivalley.co.kr:454/reservation.asp?location=002";

  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();    
    
    Document doc = Jsoup.connect(CONNECT_URL).get();
    documentList.add(doc);
    
    Element elm = doc.selectFirst("#reservation > form[name=form_next]");
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