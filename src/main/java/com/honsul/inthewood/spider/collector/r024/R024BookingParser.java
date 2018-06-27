package com.honsul.inthewood.spider.collector.r024;

import java.io.*;
import java.time.*;
import java.util.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import com.honsul.inthewood.core.*;
import com.honsul.inthewood.core.annotation.*;
import com.honsul.inthewood.core.model.*;
import com.honsul.inthewood.core.parser.*;

/**
 * 와룡자연휴양림 예약형황 파
 * @author koreamic
 */
@BookingParser(resortId="R024")
public class R024BookingParser extends JsoupBookingParser {
	static final String URL = "http://www.jangsuhuyang.kr/Waryong/reserve/list.asp";
	static final String SELECTOR_TBODY = "table.table.table-treserve > tbody";
	static final String SELECTOR_TBODY_ROOM_INFO = "td.tal > span > a";
	private static final String SELECTOR_TBODY_ROOM_STATUS = "td.tac";
	
	private static final String POSSIBLE = "가능";
	
	@Override
	protected List<Document> documents() throws IOException {
		List<Document> documentList = new ArrayList<>();
		int cMonth = LocalDate.now().getMonthValue();
		int nMonth = LocalDate.now().plusMonths(1).getMonthValue();
		int nnMonth = LocalDate.now().plusMonths(2).getMonthValue();
		
		documentList.add(Jsoup.connect(URL).data("ChoiceMonth", String.valueOf(cMonth)).get());
		documentList.add(Jsoup.connect(URL).data("ChoiceMonth", String.valueOf(nMonth)).get());
		documentList.add(Jsoup.connect(URL).data("ChoiceMonth", String.valueOf(nnMonth)).get());
		
		return documentList;
	}
	
	@Override
	public List<Booking> extract(Document doc) {
		List<Booking> bookingList = new ArrayList<>();
		
		Elements tbodies = doc.select(SELECTOR_TBODY);
		for (Element tbody : tbodies) {
			Elements room = tbody.select(SELECTOR_TBODY_ROOM_INFO);
			String roomNm = room.text();
			
			Elements tds = tbody.select(SELECTOR_TBODY_ROOM_STATUS);
			for (Element td : tds) {
				String[] status = td.select("img").attr("alt").trim().split(",");
				if(status.length < 3 || !POSSIBLE.equals(status[0])) continue;
				
				Booking booking = new Booking();
				booking.setResortId(SpiderContext.getResortId());
				booking.setRoomNm(roomNm);
				String[] date = status[2].split("-");
				booking.setBookingDt(LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])));
				
				bookingList.add(booking);
			}
		}
		
		return bookingList;
	}
}
