package com.honsul.inthewood.spider.collector.r019;

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
 * 용문산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R019")
public class R019BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "https://res.ypc114.kr:442/chehum/month.php";
  private static final String REFERER = "https://www.swijapark.com/contents/";

  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    //this month
    documentList.add(Jsoup.connect(CONNECT_URL).referrer(REFERER).data("group_v", "2").post());
        
    //next month
    LocalDate next = LocalDate.now().plusMonths(1);
    String year = next.format(DateTimeFormatter.ofPattern("yyyy"));
    String month = next.format(DateTimeFormatter.ofPattern("MM"));
    
    Document doc = Jsoup.connect(CONNECT_URL)
            .referrer(REFERER)
            .data("year", year)
            .data("month", month)
            .data("cf_idx", "2")
            .data("yr_giyuk", "N")
            .data("group_v", "2")
            .post();
    
    documentList.add(doc);
    return documentList;
  }

  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>(); 

    String title = doc.select("table.mt5.pt5 > tbody > tr > td.wht > b").text().split("월")[0];
    String year = title.split("/")[0].replaceAll("\\s", "");
    String month = title.split("/")[1].replaceAll("\\s", "");
    
    for(Element tr : doc.select("table.mt10.table01 > tbody > tr")) {
    	for(Element td : tr.select("td")) {
        	if (td.select("table > tbody > tr > td > div").text().isEmpty()) {
    			continue;
    		}
        	String day = td.select("table > tbody > tr").select(" td > div > font").text();
        	
        	for (Element div : td.select("table > tbody > tr+tr > td > div > div")) {
        		if (div.text().contains("예약종료")) {
        			continue;
        		}
        		for (Element roomTr : div.select(" a > b")) {
        			String roomNm = roomTr.text().split("\\(")[0];
		        	if (roomNm.contains("야영데크")) {
		        		continue;
		        	}
	        		Booking booking = new Booking();
	    	        booking.setResortId(SpiderContext.getResortId());
	    	        booking.setBookingDt(LocalDate.parse(year + "-" + month + "-" + day, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	    	        booking.setRoomNm(roomNm);
	    	        bookingList.add(booking);
        		}
        	}
        }
    }
    //System.out.println(bookingList);  
    return bookingList;
  }
}
