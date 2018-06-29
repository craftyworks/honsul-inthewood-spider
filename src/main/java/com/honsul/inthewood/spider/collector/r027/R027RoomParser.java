package com.honsul.inthewood.spider.collector.r027;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;
import com.honsul.inthewood.core.parser.JsoupRoomParser;
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 송이밸리자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R027")
public class R027RoomParser extends JsoupRoomParser {
  
	private static final String HUT_URL = "https://www.songivalley.co.kr:454/facilities.asp?location=";
	private static final String[] LOCATIONS = {"003", "003_02", "003_03", "006"};
		  
	@Override
	protected List<Document> documents() throws IOException {
		List<Document> docs = new ArrayList<>();
	    for(String location : LOCATIONS) {
	    	docs.add(Jsoup.connect(HUT_URL + location).get());
	    }
	    return docs;
	}

	@Override
	public List<Room> extract(Document doc) {
		List<Room> roomList = new ArrayList<>();
		
		String numberOfPeople = "";
	    String space = "";
	    long price = 0, peakPrice = 0;
	    String roomTypeNm = doc.select("#content > div.header > h2").text();
	    
		for(Element row : doc.select("#facilities > table > tbody > tr")) {
			Elements tds = row.select("td");
			String roomNm = tds.get(0).text();
			if(tds.size() > 2) {
				space = tds.get(1).text();
				numberOfPeople = tds.get(2).text().replace("명", "");
				peakPrice = TextUtils.findMoneyLong(tds.get(3).text());
				price = TextUtils.findMoneyLong(tds.get(4).text());
			}
			  
		    Room room = new Room();
		    room.setResortId(SpiderContext.getResortId());
		    room.setRoomNm(roomNm);
		    room.setRoomType(getRoomType(roomTypeNm));
		    room.setSpace(space);
		    room.setNumberOfPeople(numberOfPeople);
		    room.setPeakPrice(peakPrice);
		    room.setPrice(price);
		    roomList.add(room);
		}
		//System.out.println("roomList: " + roomList);
		return roomList;
	}
	
	@Override
	protected RoomType getRoomType(String roomNm) {
		return "숲속의집".equals(roomNm) ? RoomType.HUT : RoomType.CONDO;
  }
}