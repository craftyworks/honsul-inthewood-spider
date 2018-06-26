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

	private static final String URL = "http://www.jangsuhuyang.kr/Waryong/reserve/house_detail.asp";
	
	@Override
	protected List<Document> documents() throws IOException {
		List<Document> documentList = new ArrayList<Document>();
		List<String> pids = getRoomPIDList();
		for (String pid : pids) {
			documentList.add(Jsoup.connect(URL).data("pid", pid).get());
		}
		return documentList;
	}

	private List<String> getRoomPIDList() throws IOException {
		List<String> pids = new ArrayList<String>();
		
		Document doc = Jsoup.connect(R024BookingParser.URL).get();
		Elements tbodies = doc.select(R024BookingParser.SELECTOR_TBODY);
		for (Element tbody : tbodies) {
			Elements room = tbody.select(R024BookingParser.SELECTOR_TBODY_ROOM_INFO);
			String href = room.attr("href");
			String[] query = href.substring(href.indexOf("?")+1).split("=");
			if(query.length == 2 && "pid".equals(query[0])) pids.add(query[1]);
		}
		
		return pids;
	}

	@Override
	public List<Room> extract(Document doc) {
		List<Room> roomList = new ArrayList<Room>();
		Elements trs = doc.select("fieldset").select("table").first().select("tr");
		
		Room room = null;
		String key, val;
		for (Element tr : trs) {
			if(tr.className().equals("info")) {
				val = tr.select("th").text();
				if(TextUtils.contains(val, "부엉이소쩍새")) val = "부엉이소쩍새";
				if(TextUtils.contains(val, "산림문화휴양관")) val = val.replaceFirst("산림문화", "");
				
				room = new Room();
				room.setResortId(SpiderContext.getResortId());
				room.setRoomNm(val);
				room.setRoomType(getRoomType(val));
			}else {
				key = tr.select("td:nth-child(1)").text();
				val = tr.select("td:nth-child(2)").text();
				if(room == null) continue;
				
				switch (key) {
				case "면 적":
					room.setSpace(val.split(" ")[0].trim());
					break;
				case "사 용 료":
					String[] price = val.split("/");
					long peak = TextUtils.findMoneyLong(price[0]);
					long normal= TextUtils.findMoneyLong(price[1]);
					room.setPeakPrice(peak);
					room.setPrice(normal < 100 ? peak : normal);
					break;
				case "기준인원":
					room.setNumberOfPeople(val.split(" ")[0].trim());
					break;
				default:
					break;
				}
			}
		}
		
		if(room != null) roomList.add(room);
		return roomList;
	}

	@Override
	public RoomType getRoomType(String roomTypeNm) {
		return TextUtils.contains(roomTypeNm, "휴양관", "복합", "단체") ? RoomType.CONDO : RoomType.HUT;
	}
}