package com.honsul.inthewood.spider.collector.r020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
 * 춘천숲자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R020")
public class R020RoomParser extends JsoupRoomParser {
  
  private static final String HUT_URL = "http://www.ccforest.or.kr/sub/sub1_2.html";

  @Override
  protected Document document() throws IOException {
    return Jsoup.connect(HUT_URL).get();
  }
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();
    
    Pattern patternRoomType = Pattern.compile("숲속의집*|산림휴양관*");
    
    for(Element div : doc.select("#imgbox7 > div")) {
    	if(patternRoomType.matcher(div.selectFirst("div:nth-child(2)").text()).find()){
    		if(div.selectFirst("div:nth-child(2)").text().contains("세미나")) {
        		continue;
        	}
	    	
    		String space = div.select("div:nth-child(6)").text().trim();
        	String people = div.selectFirst("div:nth-child(2)").text().contains("산림휴양관")? (space.contains("42")? "4":"2") : (space.contains("42")? "4":"2");
            String priceText = div.select("div:nth-child(8)").text();
            long price = TextUtils.findMoneyLong(priceText.split("\\~")[0].trim());
            long peakPrice = TextUtils.findMoneyLong(priceText.split("\\~")[1].trim().replace("원", ""));
            
            String roomNames = div.select("div:nth-child(4)").text();
	    	
        	for(String name : roomNames.split("\\,")) {
        		Room room = new Room();
        	    room.setResortId(SpiderContext.getResortId());
        	    name = name.replace("초롱촉", "초롱꽃").replace("구절호", "구절초").replace("옥잠화", "비비추");
        	    room.setRoomNm(name.trim());
        	    room.setRoomType(getRoomType(name.trim()));
        	    room.setSpace(space);
        	    room.setNumberOfPeople(people);
        	    room.setPeakPrice(peakPrice);
        	    room.setPrice(price);
        	    roomList.add(room); 
        	}
    	}
    }
    //System.out.println("roomList: " + roomList);
	return roomList;
  }
  
  @Override
  public RoomType getRoomType(String roomTypeNm) {
	return TextUtils.contains(roomTypeNm, "단풍나무", "산사나무", "느티나무", "이팝나무") ?  RoomType.HUT : RoomType.CONDO;
  }
}
