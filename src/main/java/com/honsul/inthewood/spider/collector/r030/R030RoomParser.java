package com.honsul.inthewood.spider.collector.r030;

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
 * 미숭산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R030")
public class R030RoomParser extends JsoupRoomParser {
  
	private static final String HUT_URL = "http://misungsan.co.kr/2014/rs_system/contents/reservation02.asp";
		  
	@Override
	protected List<Document> documents() throws IOException {
		List<Document> docs = new ArrayList<>();
	    docs.add(Jsoup.connect(HUT_URL).get());
	    return docs;
	}

	@Override
	public List<Room> extract(Document doc) {
		List<Room> roomList = new ArrayList<>();
		
		for(Element tr : doc.select("div.contents > div.subject_cnt.b_dotline > table > tbody > tr")) {
			long price = TextUtils.findMoneyLong(tr.select("td:nth-child(5)").text());
			long peakPrice = TextUtils.findMoneyLong(tr.select("td:nth-child(6)").text());
			String numberOfPeople = tr.select("td:nth-child(3)").text().replace("인기준", "");
			String space = tr.select("td:nth-child(2)").text().replace("형", "");
			
			String roomNm = tr.select("td:nth-child(1)").text();
			roomNm = roomNm.contains("휴양관") ? roomNm = "휴양관" : roomNm.split("\\(")[1].replaceAll("\\s+|\\t|\\)", "");
			
			int loop = roomNm.contains("휴양관") ? 3 : 2;
			for (int i=1; i<=loop; i++ ) {		    			  
			    Room room = new Room();
			    room.setResortId(SpiderContext.getResortId());
			    room.setRoomNm(roomNm + String.valueOf(i));
			    room.setRoomType(getRoomType(roomNm));
			    room.setSpace(space);
			    room.setNumberOfPeople(numberOfPeople);
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
		return roomNm.contains("휴양관") ? RoomType.CONDO : RoomType.HUT ;
	}
}