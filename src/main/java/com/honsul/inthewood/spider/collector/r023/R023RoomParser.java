package com.honsul.inthewood.spider.collector.r023;


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
 * 바라산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R023")
public class R023RoomParser extends JsoupRoomParser {
  
	private static final String HUT_URL = "http://barasan.uw21.net/EgovPageLink.do?link=barasan/guide/useFreeTime";
	  
	@Override
	protected List<Document> documents() throws IOException {
		List<Document> docs = new ArrayList<>();
		docs.add(Jsoup.connect(HUT_URL).get());    
		return docs;
	}

	@Override
	public List<Room> extract(Document doc) {
		List<Room> roomList = new ArrayList<>();
		String roomNm = "";
		
		for(Element tr : doc.select("#contentcore > div.contents > div:nth-child(2) > div > table > tbody > tr")) {
			if (TextUtils.contains(tr.select("td:nth-child(1)").text(), "야영데크", "산림문화휴양관")) {
				break;
			}
			
			String space = "", people = "";
			long price = 0, peakPrice = 0;
			
			if (tr.select("td").size() > 5) {
				roomNm = tr.select("td:nth-child(1)").text().split("\\(")[1].split("\\)")[0];
				space = tr.select("td:nth-child(4)").text();
				people = tr.select("td:nth-child(3)").text().replace("명", "");
				price = TextUtils.findMoneyLong(tr.select("td:nth-child(7)").text());
				peakPrice = TextUtils.findMoneyLong(tr.select("td:nth-child(6)").text());
			} else {
				space = tr.select("td:nth-child(2)").text();
				people = tr.select("td:nth-child(1)").text().replace("명", "");
				price = TextUtils.findMoneyLong(tr.select("td:nth-child(5)").text());
				peakPrice = TextUtils.findMoneyLong(tr.select("td:nth-child(4)").text());
			}
			
			if (roomNm.contains("백운산") && space.contains("복층")) {roomNm = "백운산동(4인복층)";} 
			else if (roomNm.contains("백운산")){roomNm = "백운산동(4인테라스)";}
			
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
	return TextUtils.contains(roomTypeNm, "백운산", "청계산") ? RoomType.CONDO : RoomType.HUT ;
  }
}