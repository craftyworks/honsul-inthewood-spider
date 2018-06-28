package com.honsul.inthewood.spider.collector.r026;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    String month = String.format("%02d", LocalDate.now().getMonthValue());
    String nextYear = String.valueOf(LocalDate.now().plusMonths(1).getYear());
    String nextMonth = String.format("%02d", LocalDate.now().plusMonths(1).getMonthValue());
    
	Elements scripts = dummy.getElementsByTag("script");
	for (Element element : scripts) {
		for (DataNode node : element.dataNodes()) {
			String content = node.getWholeData();
			if (content.contains("contentUid")) {
				contentUid = content.split("contentUid=")[1].split("&year")[0];
				break;
			}
        }
	}
	
    String key = dummy.select("#reserve > ul > li:nth-child(1) > a:nth-child(5)").attr("onclick").split("\\'\\)")[0];
    String categoryUid1 = key.split("','")[1];
    String categoryUid1s = key.split("','")[2];		
    documentList.add(Jsoup.connect(CONNECT_URL).data("contentUid", contentUid).data("year", year).data("month", month).data("categoryUid1", categoryUid1).data("categoryUid1s", categoryUid1s).get());
    documentList.add(Jsoup.connect(CONNECT_URL).data("contentUid", contentUid).data("year", nextYear).data("month", nextMonth).data("categoryUid1", categoryUid1).data("categoryUid1s", categoryUid1s).get());
    return documentList;
  }

  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>(); 

    Elements scripts = doc.getElementsByTag("script");
    
    for(Element tr : doc.select("#content > table > tbody > tr")) {
    	if(tr.select("td").size() == 0) {continue;}
    	
    	String roomNm = tr.select("th").text().split("\\(")[0].replaceAll("\\s|\\t", "");
    	
    	for(Element td : tr.select("td")) {   
    		Boolean findFlag = false;
    		outerloop:
    		for (Element element : scripts) {
    			for (DataNode node : element.dataNodes()) {
    				String content = node.getWholeData();    				
    				String pattern = "\\b"+td.attr("id")+"\\b";
    			    Pattern p = Pattern.compile(pattern);
    			    Matcher m = p.matcher(content);
    			    if (m.find()) {
    					findFlag = true;
    					break outerloop;
    				}
    	        }
    		}
    		if(td.select("a[href]").size() == 0 || findFlag == true) {continue;}
    		    		
        	String bookingDt = td.select("a").attr("href").split("Day=")[1];            
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