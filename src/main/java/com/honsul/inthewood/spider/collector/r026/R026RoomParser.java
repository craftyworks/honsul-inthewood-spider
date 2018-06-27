package com.honsul.inthewood.spider.collector.r026;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * 가학산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R026")
public class R026RoomParser extends JsoupRoomParser {
  
	private static final String HUT_URL = "http://gahak.haenam.go.kr/index.9is";
	  
	@Override
	protected List<Document> documents() throws IOException {
		List<Document> docs = new ArrayList<>();
		docs.add(Jsoup.connect(HUT_URL).get());    
		return docs;
	}

	@Override
	public List<Room> extract(Document doc) {
		List<Room> roomList = new ArrayList<>();
		
		for(Element tr : doc.select("#contents > div.facilities > table:nth-child(4) > tbody > tr")) {
			String[] roomTitle = tr.select("td:nth-child(1)").text().split(" ");
			String space = roomTitle[0].contains("물소리") ? "82㎡" : "29.95㎡"; 
			String people = tr.select("td:nth-child(2)").text().replace("명", "");
			long price = TextUtils.findMoneyLong(tr.select("td:nth-child(5)").text());
			long peakPrice = TextUtils.findMoneyLong(tr.select("td:nth-child(3)").text());
			
			for (int i=1; i <= Integer.valueOf(roomTitle[1].substring(roomTitle[1].length() - 1)); i++) {
				String roomNm = roomTitle[0] + String.valueOf(i);

				Room room = new Room();
	    	    room.setResortId(SpiderContext.getResortId());
	    	    room.setRoomNm(roomNm.trim());
	    	    room.setRoomType(RoomType.HUT);
	    	    room.setSpace(space);
	    	    room.setNumberOfPeople(people);
	    	    room.setPeakPrice(peakPrice);
	    	    room.setPrice(price);
	    	    roomList.add(room); 
    	    }
		}
		//System.out.println("roomList: " + roomList);
		return roomList;
	}
	
	@Override
	protected RoomType getRoomType(String roomNm) {
		return RoomType.getRoomType(roomNm);
  }
}