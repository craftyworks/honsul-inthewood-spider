package com.honsul.inthewood.spider.collector.r026;

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
 * 가학산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R026")
public class R026BookingParser extends JsoupBookingParser {

  private static final String MAIN_URL = "http://gahak.haenam.go.kr/index.9is";
  private static final String CONNECT_URL = "http://gahak.haenam.go.kr/planweb/reservation/pension_dateTotalRoomList.9is";

  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();    
    
    Document dummy = Jsoup.connect(MAIN_URL).get();
    String contentUid = dummy.select("#gnb_menu > li.gm03 > div > ul > li:nth-child(2) > a[href]").attr("href").split("=")[1];
    dummy = Jsoup.connect(MAIN_URL).data("contentUid", contentUid).get();
    
    String year = String.valueOf(LocalDate.now().getYear());
    String month = String.valueOf(LocalDate.now().getMonthValue());
    String nextYear = String.valueOf(LocalDate.now().plusMonths(1).getYear());
    String nextMonth = String.valueOf(LocalDate.now().plusMonths(1).getMonthValue());
	
    String key = dummy.select("#reserve > ul > li:nth-child(1) > a:nth-child(5)").attr("onclick").split("\\'\\)")[0];
    String categoryUid1 = key.split("','")[1];
    String categoryUid1s = key.split("','")[2];		
    contentUid = dummy.select("#leftArea > div > ul > li.on > a").attr("href").split("=")[1];
    
    documentList.add(Jsoup.connect(CONNECT_URL).data("contentUid", contentUid).data("year", year).data("month", month).data("categoryUid1", categoryUid1).data("categoryUid1s", categoryUid1s).get());
    documentList.add(Jsoup.connect(CONNECT_URL).data("contentUid", contentUid).data("year", nextYear).data("month", nextMonth).data("categoryUid1", categoryUid1).data("categoryUid1s", categoryUid1s).get());
    return documentList;
  }

  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>(); 

    String yearMonth = doc.select("#contents > div.reservation > h3").text();
    yearMonth = yearMonth.replace("년", "-").replaceAll("월|\\s", "");    
    
    for(Element td : doc.select("#contents > div.reservation > table > tbody > tr > td")) {
    	if(td.text().length() == 0 || td.text().contains("예약종료")) {
	          continue;
	    }
    	for(Element form : td.select("form")) {
        	if(td.text().length() == 0 || td.text().contains("예약종료")) {
	          continue;
	        }
        	String[] attr = form.select("input[name='rsv_info']").attr("value").split("#@");
            String bookingDt = attr[2];
            String roomNm = form.text().replace("*",""); // remove '*'
        	
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