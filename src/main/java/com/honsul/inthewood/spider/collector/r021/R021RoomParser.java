package com.honsul.inthewood.spider.collector.r021;


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
 * 치악산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R021")
public class R021RoomParser extends JsoupRoomParser {
  
	private static final String HUT_URL = "http://member.nfcf.or.kr/forest/user.tdf?a=common.HtmlApp&c=4001&d=chiak&page=/member/chiak/fct";
	private static final String[] LOCATIONS = {"0201", "0202", "0203", "0204", "0205"};;
  
	@Override
	protected List<Document> documents() throws IOException {
		List<Document> docs = new ArrayList<>();
		for(String location : LOCATIONS) {      
			docs.add(Jsoup.connect(HUT_URL + location + ".html&mc=MEM_CIA_FCT" + location).get());    
		} 
		return docs;
	}

	@Override
	public List<Room> extract(Document doc) {
		List<Room> roomList = new ArrayList<>();
		
		String title = doc.select("#tabmenu > li.on > a").text();
		long price = 0, peakPrice = 0;
		String people = "";
    
		for(Element tr : doc.select("#content > table > tbody > tr")) {
			Elements tds = tr.select("td");
			if(tds.size() > 1) {
				price = TextUtils.findMoneyLong(tr.select("td:nth-child(3)").text().replace("만원", "0,000"));
	            peakPrice = TextUtils.findMoneyLong(tr.select("td:nth-child(4)").text().replace("만원", "0,000"));
	            people = tr.select("td:nth-child(2)").text();    
			}
            String roomNames = tr.select("td:nth-child(1)").text();            
            String roomNm = "", space = "";
            
            if(roomNames.contains("(")) {
            	roomNm = roomNames.split("\\(")[0].trim();
            	space = roomNames.split("\\(")[1].split("\\)")[0];
            } else if(roomNames.contains("황토방")) {
            	roomNm = "황토방1호,황토방2호,황토방3호,황토방4호,황토방5호,황토방6호,황토방7호,황토방8호";
            	space = title.split("\\s")[1].trim();
            } else {
            	roomNm = roomNames.trim();
            	space = title.split("\\s")[1].trim();
            }
            roomNm = roomNm.replace("굴참나무", "굴참").replace("상수리나무", "상수리");
            
        	for(String name : roomNm.split("\\,")) {
        		Room room = new Room();
        	    room.setResortId(SpiderContext.getResortId());
        	    room.setRoomNm(name.trim());
        	    room.setRoomType(getRoomType(name.trim()));
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
  public RoomType getRoomType(String roomTypeNm) {
	return TextUtils.contains(roomTypeNm, "황토방") ? RoomType.CONDO : RoomType.HUT ;
  }
}