package com.honsul.inthewood.spider.collector.national;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;
import com.honsul.inthewood.core.parser.JsoupRoomParser;
import com.honsul.inthewood.core.util.NationalResortUtils;
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 국립자연휴양림 숙소현황 파서.
 */
public abstract class AbstractNationalRoomParser extends JsoupRoomParser {
  private final static Pattern P_ROOM_INFO = Pattern.compile("(\\d+)인실/(.*)");
  
  protected abstract Document document() throws IOException;
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();
    
    for(Element td : doc.select("div.ins_current_table_wrap > table.table_wrap_04 > tbody > tr > td.locked_left")) {
      RoomType roomType = getRoomType(td.select("span.room_icon icon_yeon_l").text());
      String roomText = td.select("a.room_tit").text();
      
      String roomNm = StringUtils.substringBefore(roomText, "(");
      String roomInfo = TextUtils.stringInBrackets(roomText);
      Matcher matcher = P_ROOM_INFO.matcher(roomInfo);
      if(matcher.find()) {
        String people = matcher.group(1);
        long price = NationalResortUtils.price(roomType, people);
        long peakPrice = NationalResortUtils.peakPrice(roomType, people);
        String space = matcher.group(2);
        
        Room room = new Room();
        room.setResortId(SpiderContext.getResortId());
        room.setRoomNm(roomNm);
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
    return StringUtils.contains(roomTypeNm, "휴양관") ? RoomType.CONDO : RoomType.HUT;
  }

}
