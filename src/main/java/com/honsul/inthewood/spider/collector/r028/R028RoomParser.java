package com.honsul.inthewood.spider.collector.r028;

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
 * 평창자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R028")
public class R028RoomParser extends JsoupRoomParser {
  
	private static final String HUT_URL = "https://forest700.or.kr:10462/";
	private static final String[] LOCATIONS = {"sub21.php", "sub22.php", "sub23.php"};
		  
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
		
		for(Element li : doc.select("#right_con > div:nth-child(3) > div.sub_navi_17 > ul > li")) {
			String roomNm = li.text().replaceAll("\\s+|\\호|나무", "");

			Elements tr = doc.select("div.sub_navi_con > ul > li:nth-child(4) > table > tbody > tr:nth-child(3)");
			long price = TextUtils.findMoneyLong(tr.get(0).select("td:nth-child(3)").text());
			long peakPrice = TextUtils.findMoneyLong(tr.get(0).select("td:nth-child(5)").text());
			String numberOfPeople = doc.select("div.sub_navi_con > ul > li:nth-child(5) > p").text().split("\\(")[0].replaceAll("[^\\d]", "");
			String space = "";
		    			  
		    Room room = new Room();
		    room.setResortId(SpiderContext.getResortId());
		    room.setRoomNm(roomNm);
		    room.setRoomType(RoomType.CONDO);
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