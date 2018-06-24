package com.honsul.inthewood.spider.collector.r024;

import java.io.*;
import java.util.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import com.honsul.inthewood.core.*;
import com.honsul.inthewood.core.annotation.*;
import com.honsul.inthewood.core.model.*;
import com.honsul.inthewood.core.parser.*;
import com.honsul.inthewood.core.util.*;

/**
 * 와룡자연휴양림 숙소현황 파서
 * 
 * @author koreamic
 */
@RoomParser(resortId = "R024")
public class R024RoomParser extends JsoupRoomParser {

	private static final String URL = "http://www.jangsuhuyang.kr/Waryong/reserve/house_main.asp";
	private static final String SELECTOR_ROOM_INFO = "div.table-responsive > table.table.table-bordered > tbody > tr";
	private static Map<String, String[]> roomMapper = new HashMap<String, String[]>();
	
	static {
		roomMapper.put("숲속의집 1호~4호", new String[] {"숲속의집 1호", "숲속의집 2호", "숲속의집 3호", "숲속의집 4호"});
		roomMapper.put("숲속의집 5호~8호", new String[] {"숲속의집 5호", "숲속의집 6호", "숲속의집 7호", "숲속의집 8호"});
		roomMapper.put("한옥체험관", new String[] {"한옥 1호", "한옥 2호"});
		roomMapper.put("산막 9, 10호", new String[] {"산막 9호", "산막 10호"});
		roomMapper.put("부엉이.소쩍새방 (통합 운영)", new String[] {"부엉이소쩍새"});
		roomMapper.put("복합산막", new String[] {"복합산막 101호", "복합산막 102호", "복합산막 103호", "복합산막 104호", "복합산막 105호", "복합산막 106호"
												, "복합산막 201호", "복합산막 202호", "복합산막 203호", "복합산막 204호", "복합산막 205호", "복합산막 206호"});
		roomMapper.put("산림문화휴양관", new String[] {"휴양관 1호", "휴양관 2호"});
		roomMapper.put("연수의집 2층, 3층", new String[] {"연수의집 2층", "연수의집 3층"});
		roomMapper.put("단체식당", new String[] {"단체식당"});
	}
	
	@Override
	protected Document document() throws IOException {
		return Jsoup.connect(URL).get();
	}

	@Override
	public List<Room> extract(Document doc) {
		List<Room> roomList = new ArrayList<Room>();
		
		Elements tbodies = doc.select(SELECTOR_ROOM_INFO);

		for(Element tr : tbodies) {
    	    if(tr.select("td").size() < 3) continue;
    	    if(tr.select("td").size() > 3) {
    	    	String roomKey = tr.select("td:nth-child(2)").select("span").text();
    	    	String[] infos = tr.select("td:nth-child(2)").text().split("/");
    	    	
    	    	if(roomMapper.containsKey(roomKey)) {
    	    		String space = infos.length > 1 ? infos[0].replace(roomKey, "").trim() : "";
    	    		String people = infos.length > 1 ? infos[1].replace("인용", "").trim() : "";
    	    		long peakPrice = TextUtils.findMoneyLong(tr.select("td:nth-child(4)").text());
    	    		long price = "단체식당".equals(roomKey) ? peakPrice : TextUtils.findMoneyLong(tr.select("td:nth-child(5)").text());
    	    		
    	    		if(!"산림문화휴양관".equals(roomKey)) {
    	    			for (String  roomNm: roomMapper.get(roomKey)) {
    	    				Room room = new Room();
    	    				room.setResortId(SpiderContext.getResortId());
    	    				room.setRoomNm(roomNm);
    	    				room.setSpace(space);
    	    				room.setNumberOfPeople(people);
    	    				room.setPrice(price);
    	    				room.setPeakPrice(peakPrice);
    	    				
    	    				roomList.add(room);
    	    			}
    	    		}else {
    	    			String roomNm = roomMapper.get(roomKey)[0];
    	    			infos = tr.select("td:nth-child(3)").text().split(" ");
    	    			space = infos[0].substring(1, infos[0].length()-1);
    	    			people = infos[1].replace("인용", "").trim();
    	    			
	    				Room room = new Room();
	    				room.setResortId(SpiderContext.getResortId());
	    				room.setRoomNm(roomNm);
	    				room.setSpace(space);
	    				room.setNumberOfPeople(people);
	    				room.setPrice(price);
	    				room.setPeakPrice(peakPrice);
	    				
	    				roomList.add(room);
    	    				
    	    			tr = tr.nextElementSibling();
	    				roomNm = roomMapper.get(roomKey)[1];
	    				infos = tr.select("td:nth-child(1)").text().split(" ");
    	    			space = infos[0].substring(1, infos[0].length()-1);
    	    			people = infos[1].replace("인용", "").trim();
    	    			peakPrice = TextUtils.findMoneyLong(tr.select("td:nth-child(2)").text());
    	    			price = TextUtils.findMoneyLong(tr.select("td:nth-child(3)").text());
    	    			
    	    			room = new Room();
    	    			room.setResortId(SpiderContext.getResortId());
    	    			room.setRoomNm(roomNm);
    	    			room.setSpace(space);
    	    			room.setNumberOfPeople(people);
    	    			room.setPrice(price);
    	    			room.setPeakPrice(peakPrice);
    	    			
    	    			roomList.add(room);
    	    		}
    	    	}
    	    }
		}
		return roomList;
  }

	@Override
	public RoomType getRoomType(String roomTypeNm) {
		return TextUtils.contains(roomTypeNm, "휴양관") ? RoomType.CONDO : RoomType.HUT;
	}
}