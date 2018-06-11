package com.honsul.inthewood.spider.collector.r017;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
 * 방화동자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R017")
public class R017RoomParser extends JsoupRoomParser {
  
  private static final String HUT_URL = "http://www.jangsuhuyang.kr/Banghwa1/reserve/house_main.asp";

  @Override
  protected Document document() throws IOException {
    return Jsoup.connect(HUT_URL).get();
  }
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();

    
    String roomNm = "";
    for(Element row : doc.select("div.col-md-9 > table:first-of-type > tbody > tr.tac")) {
      int idx = 0;
      Element title = row.selectFirst("td > a > span.text-primary");
      if(title != null) {
        roomNm = title.text();
        idx = 2;
      } 
      RoomType roomType = getRoomType(roomNm);
      Elements tds = row.select("td");
      String space = tds.get(idx++).text();
      String numberOfPeople = TextUtils.stringInBrackets(space);
      space = StringUtils.substringBefore(space, "(");
      numberOfPeople = StringUtils.substringBefore(numberOfPeople, ",").replaceAll("인용", "");
      
      long peakPrice = TextUtils.parseLong(tds.get(idx++).text());
      long price = TextUtils.parseLong(tds.get(idx++).text()); 

      List<String> roomNms = new ArrayList<>();
      if(roomType.equals(RoomType.CONDO)) {
        int start = ("6".equals(numberOfPeople)) ? 101 : 201;
        for(int i=start; i<start+8; i++) {
          roomNms.add("휴양관 " + i + "호");
        }
      } else {
        roomNms.add(roomNm);
      }
      
      for(String roomName : roomNms) {
        Room room = new Room();
        room.setResortId(SpiderContext.getResortId());
        room.setRoomNm(roomName);
        room.setRoomType(roomType);
        room.setSpace(space);
        room.setNumberOfPeople(numberOfPeople);
        room.setPeakPrice(peakPrice);
        room.setPrice(price);
        roomList.add(room);
      }
    }
    return roomList;
  }
  
  @Override
  public RoomType getRoomType(String roomNm) {
    return StringUtils.contains(roomNm, "휴양관") ? RoomType.CONDO : RoomType.HUT;
  }

}
