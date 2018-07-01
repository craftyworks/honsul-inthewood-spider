package com.honsul.inthewood.spider.collector.r029;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
 * 대운산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R029")
public class R029BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://forest.maketicket.co.kr/camp/reserve/calendar.jsp";
  private static final String BASE_URL = "http://forest.maketicket.co.kr/ticket/GD81";

  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();    
    
    Document dummy = Jsoup.connect(BASE_URL).get();
    String idkey = "", gd_seq = "", yyyymmdd = "", sd_date = "";
    
    Elements scripts = dummy.getElementsByTag("script");
    Loop:
	for (Element element : scripts) {
		for (DataNode node : element.dataNodes()) {
			String content = node.getWholeData();

			if (content.contains("document") && content.contains("ready")) {
				String[] args = content.split("function")[1].replace("\"", "").split(",");
				idkey = args[1].split(":")[1].trim();
				gd_seq = args[2].split(":")[1].trim();
				yyyymmdd = args[3].split(":")[1].trim();
				sd_date = args[4].split(":")[1].split("}")[0].trim();
				break Loop;
			}
        }
	}
    Document doc = Jsoup.connect(CONNECT_URL).data("idkey", idkey).data("gd_seq", gd_seq).data("yyyymmdd", yyyymmdd).data("sd_date", sd_date).post();
    documentList.add(doc);
    
    String nextmonth = doc.select("body > a.nextmonth").attr("href").split("\\(")[1].split("\\)")[0].replaceAll("\'", "");
    doc = Jsoup.connect(CONNECT_URL).data("idkey", idkey).data("gd_seq", gd_seq).data("yyyymmdd", nextmonth).data("sd_date", nextmonth).post();
    if (!doc.select("body > table > tbody > tr").text().contains("예매 가능한 일자가 없습니다")) {
    	documentList.add(doc);
    }
    return documentList;
  }
  
  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>(); 
    //System.out.println(doc.select("table > tbody > tr").size());
    
    for(Element tr : doc.select("table > tbody > tr")) {
    	for(Element li : tr.select("td > ul > li")) { 
    		if(!li.select("a > span").text().contains("0")) {
	    		String roomNm = li.select("a").text().replaceFirst(li.select("a > span").text(), "").replaceAll("\\s|\\t", "");	
	    		if (roomNm.contains("숲속의집") || roomNm.contains("산림휴양관숙박")) {
				    String bookingDt = li.select("a").attr("onclick").split("\\(")[1].split("\\,")[0].replaceAll("\\s+|\"", "");	
			        
		        	Booking booking = new Booking();
			        booking.setResortId(SpiderContext.getResortId());
			        booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyyMMdd")));
			        booking.setRoomNm(roomNm);
			        bookingList.add(booking);	  
		        }
	    	}	    	
    	}
    }
    //System.out.println("bookingList: " + bookingList);  
    return bookingList;
  }
}