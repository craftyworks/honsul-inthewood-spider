package com.honsul.inthewood.spider.collector.r029;

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
 * 대운산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R029")
public class R029RoomParser extends JsoupRoomParser {
  
	private static final String HUT_URL = "https://www.yssisul.or.kr/dwhuyang/EgovPageLink.do?menuNo=22&link=main/contents/DWon02";

	@Override
	protected List<Document> documents() throws IOException {
		List<Document> docs = new ArrayList<>();
	    docs.add(Jsoup.connect(HUT_URL).get());
	    return docs;
	}

	@Override
	public List<Room> extract(Document doc) {
		List<Room> roomList = new ArrayList<>();
		//System.out.println(doc);
		String numberOfPeople = "", roomType = "", space = "";
		long price, peakPrice;
		
		for(Element tr : doc.select("div > table:nth-child(2) > tbody > tr")) {
			if (!TextUtils.contains(tr.text(), "시설명", "비수기", "캐라반", "대회의실")) {
				String roomNm = "";
				if (tr.select("td").size() > 4) {
					roomType = tr.select("td:nth-child(1)").text().replaceAll("\\s+|\\t", "");
					numberOfPeople = tr.select("td:nth-child(2)").text().split("\\(")[0].replaceAll("\\s+|\\t|인실", "");									
					space = tr.select("td:nth-child(2)").text().split("\\(")[1].split("\\)")[0];
					price = TextUtils.findMoneyLong(tr.select("td:nth-child(4)").text());
					peakPrice = TextUtils.findMoneyLong(tr.select("td:nth-child(5)").text());
					
				} else {
					numberOfPeople = tr.select("td:nth-child(1)").text().split("\\(")[0].replaceAll("\\s+|\\t|인실", "");					
					space = tr.select("td:nth-child(1)").text().split("\\(")[1].split("\\)")[0];
					price = TextUtils.findMoneyLong(tr.select("td:nth-child(3)").text());
					peakPrice = TextUtils.findMoneyLong(tr.select("td:nth-child(4)").text());
				}
				
				if (roomType.contains("숲속의집")) {
					roomNm = roomType + numberOfPeople + "인";
				} else {
					roomNm = "산림휴양관숙박";
					numberOfPeople = numberOfPeople.replaceAll("\\s+|\\t|숙박시설", "");
				}	
			    Room room = new Room();
			    room.setResortId(SpiderContext.getResortId());
			    room.setRoomNm(roomNm);
			    room.setRoomType(getRoomType(roomType));
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
		return "숲속의집".contains(roomNm) ? RoomType.HUT : RoomType.CONDO;
  }
}