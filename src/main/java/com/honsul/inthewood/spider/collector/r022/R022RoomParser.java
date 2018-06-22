package com.honsul.inthewood.spider.collector.r022;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;
import com.honsul.inthewood.core.parser.JsoupRoomParser;
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 안면도자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R022")
public class R022RoomParser extends JsoupRoomParser {
  
	private static final String HUT_URL = "https://www.anmyonhuyang.go.kr:453/utilization.asp?location=001";
	private static final String REFERER = "https://www.anmyonhuyang.go.kr:453/index.asp";
	  
	@Override
	protected List<Document> documents() throws IOException {
		List<Document> docs = new ArrayList<>();
		
		//get the session cookies
	    Connection.Response res = Jsoup.connect(REFERER).ignoreContentType(true).method(Method.POST).execute();
	    final Map<String, String> cookies = res.cookies();
	    
		docs.add(Jsoup.connect(HUT_URL).referrer(REFERER).cookies(cookies).get());    
		return docs;
	}

	@Override
	public List<Room> extract(Document doc) {
		List<Room> roomList = new ArrayList<>();

		for(Element tr : doc.select("#contents > div.utilization > table > tbody > tr")) {
			String roomNm = tr.select("td:nth-child(1)").text().replace("산림휴양관", "휴양관");
			String space = tr.select("td:nth-child(2)").text();
			String people = tr.select("td:nth-child(6)").text().replace("명", "");
			long price = TextUtils.findMoneyLong(tr.select("td:nth-child(7)").text());
			long peakPrice = price;
			
			Room room = new Room();
    	    room.setResortId(SpiderContext.getResortId());
    	    room.setRoomNm(roomNm.trim());
    	    room.setRoomType(getRoomType(roomNm.trim()));
    	    room.setSpace(space);
    	    room.setNumberOfPeople(people);
    	    room.setPeakPrice(peakPrice);
    	    room.setPrice(price);
    	    roomList.add(room); 
		}
		//System.out.println("roomList: " + roomList);
		return roomList;
  }
  
  @Override
  public RoomType getRoomType(String roomTypeNm) {
	return TextUtils.contains(roomTypeNm, "휴양관") ? RoomType.CONDO : RoomType.HUT ;
  }
}