package com.honsul.inthewood.spider.collector.r019;

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
 * 용문산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R019")
public class R019RoomParser extends JsoupRoomParser {
  
  private static final String HUT_URL = "https://www.swijapark.com/contents/sjp_sub03b.php";
  
  
  @Override
  protected Document document() throws IOException {
    return Jsoup.connect(HUT_URL).get();
  }

  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();

    for(Element tr : doc.select("body > table:nth-child(14) > tbody > tr:nth-child(10) > td > table > tbody > tr > td > table:nth-child(8) > tbody > tr")) {
    	Pattern pattern = Pattern.compile("솔마루*|행복마루*|산림휴양관*|하늘마루*");
    	
    	if(pattern.matcher(tr.selectFirst("td").text()).find()){
    		String name = tr.select("td.text14").text().replaceAll("\\s|호", "");
    		RoomType roomType = getRoomType(name);
    		String space = tr.select("td:nth-child(2)").text();
    		long peakPrice = TextUtils.parseLong(tr.select("td:nth-child(4)").text().replace("원", ""));
    		long price = TextUtils.parseLong(tr.select("td:nth-child(5)").text().replace("원", ""));
    		String people = tr.select("td:nth-child(6)").text().replace("명", "");
    		
    		Room room = new Room();
            room.setResortId(SpiderContext.getResortId());
            room.setRoomNm(name);
            room.setRoomType(roomType);
            room.setSpace(space);
            room.setNumberOfPeople(people);
            room.setPeakPrice(peakPrice);
            room.setPrice(price);
            roomList.add(room);
    	}
    }
    return roomList;
  }
  
  @Override
  public RoomType getRoomType(String roomTypeNm) {
    return roomTypeNm.contains("산림휴양관") ? RoomType.CONDO : RoomType.HUT;
  }

}
