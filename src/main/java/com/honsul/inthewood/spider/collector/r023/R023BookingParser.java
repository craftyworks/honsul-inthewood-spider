package com.honsul.inthewood.spider.collector.r023;


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
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 바라산자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R023")
public class R023BookingParser extends JsoupBookingParser {

  private static final String CONNECT_URL = "http://forest.maketicket.co.kr/camp/reserve/calendar.jsp";
  private static final String REFERER = "http://forest.maketicket.co.kr/ticket/GD83";
  private static final String REFERER_DUMMY = "http://forest.maketicket.co.kr/member.do?command=member_login&gd_seq=GD83";
	  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();    
    
    Document dummy = Jsoup.connect(REFERER).referrer(REFERER_DUMMY).get();
    Element findKey = dummy.selectFirst("div.container > form");
    String idkey = findKey.selectFirst("input[name=idkey]").val();
    String gd_seq = findKey.selectFirst("input[name=gd_seq]").val();

    String todoy = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd"));
    Document doc = Jsoup.connect(CONNECT_URL).referrer(REFERER).data("idkey", idkey).data("gd_seq", gd_seq).data("yyyymmdd", todoy).data("sd_date", todoy).post();
    documentList.add(doc);

    String nextMonth = doc.select("body > a.nextmonth").attr("href");
    nextMonth = nextMonth.substring(nextMonth.indexOf("(") + 2, nextMonth.indexOf(")")-1);
    
    //next month
    documentList.add(Jsoup.connect(CONNECT_URL).referrer(REFERER).data("idkey", idkey).data("gd_seq", gd_seq).data("yyyymmdd", nextMonth).data("sd_date", nextMonth).post());
    return documentList;
  }

  @Override
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>(); 

    String yearMonth = doc.select("table > caption").text();
    yearMonth = yearMonth.replaceAll("\\.|\\s", "");  
    
    for(Element td : doc.select("table > tbody > tr > td")) {
    	for(Element li : td.select("ul > li")) {
    		if(li.select("span").text().equals("0") || TextUtils.contains(li.select("a").text(), "야영데크","고정식텐트")) {
	          continue;
	        }
        	String day = li.select("a").attr("onclick").split("\\,")[0].split("\\(")[1].trim().replaceAll("\"","");
    		String roomNm = li.select("a").text().replaceFirst(li.select("span").text(),"");
    		
        	Booking booking = new Booking();
	        booking.setResortId(SpiderContext.getResortId());
	        booking.setBookingDt(LocalDate.parse(day, DateTimeFormatter.ofPattern("yyyyMMdd")));
	        booking.setRoomNm(roomNm);
	        bookingList.add(booking);	        
        
    	}
    }
    //System.out.println("bookingList: " + bookingList);  
    return bookingList;
  }
}