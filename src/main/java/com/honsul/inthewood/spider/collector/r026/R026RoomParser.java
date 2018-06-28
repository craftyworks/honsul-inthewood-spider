package com.honsul.inthewood.spider.collector.r026;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Response;
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
  
	private static final String BASE_URL = "http://gahak.haenam.go.kr";
	private static final String HUT_URL = "http://gahak.haenam.go.kr/index.9is";
	  
	@Override
	protected List<Document> documents() throws IOException {
		List<Document> docs = new ArrayList<>();
		
		Document dummy = Jsoup.connect(HUT_URL).get();
		String contentUid = dummy.select("#gnb_menu > li.gm02 > a[href]").attr("href").split("=")[1];
		Response response = Jsoup.connect(HUT_URL).data("contentUid", contentUid).followRedirects(false).execute();
		response = Jsoup.connect(response.header("Location")).followRedirects(false).execute();
		Document doc = Jsoup.connect(response.header("Location")).get();
		
		for(Element roomType : doc.select("#leftArea > div > ul > li.on > ul > li")) {
			contentUid = roomType.select("a").attr("href").split("=")[1];
			response = Jsoup.connect(HUT_URL).data("contentUid", contentUid).followRedirects(false).execute();
			doc = Jsoup.connect(response.header("Location")).get();
			
			for(Element roomList : doc.select("#content > ul > li")) {
				String param = roomList.select("a").attr("href");
				docs.add(Jsoup.connect(BASE_URL+param).get());				
			}
		}
		contentUid = doc.select("#leftArea > div > ul > li:nth-child(2) > a").attr("href").split("=")[1];
		response = Jsoup.connect(HUT_URL).data("contentUid", contentUid).followRedirects(false).execute();
		doc = Jsoup.connect(response.header("Location")).get();
		
		for(Element roomList : doc.select("#content > ul > li")) {
			String param = roomList.select("a").attr("href");
			docs.add(Jsoup.connect(BASE_URL+param).get());				
		}
		//System.out.println("docs: " + docs.size());
		return docs;
	}

	@Override
	public List<Room> extract(Document doc) {
		List<Room> roomList = new ArrayList<>();		
				
		String roomNm = doc.select("#content > div.room_title > h4").text().replaceAll("\\s+", "");
		String space = doc.select("#content > table > tbody > tr > td:nth-child(1)").text();
		long peakPrice = TextUtils.findMoneyLong(doc.select("#content > table > tbody > tr > td:nth-child(2)").text());
		long price = TextUtils.findMoneyLong(doc.select("#content > table > tbody > tr > td:nth-child(3)").text());
		String people;
		
	    switch(space){
	        case "22평": 
	        	people = "12";break;
	        case "18평":
	        	people = "10";break;
	        case "12평":
	        	people = "6";break;
	        case "7평" :
	        	people = "4";break;
	        default :
	        	people = "4";
	    }
		
		Room room = new Room();
	    room.setResortId(SpiderContext.getResortId());
	    room.setRoomNm(roomNm.trim());
	    room.setRoomType(getRoomType(roomNm));
	    room.setSpace(space);
	    room.setNumberOfPeople(people);
	    room.setPeakPrice(peakPrice);
	    room.setPrice(price);
	    roomList.add(room); 

		return roomList;
	}
	
	@Override
	protected RoomType getRoomType(String roomNm) {
		return TextUtils.contains(roomNm, "가리재", "깃대봉", "두억봉", "바람재") ? RoomType.CONDO : RoomType.HUT ;
  }
}