package com.honsul.inthewood.spider.collector.national;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;
import com.honsul.inthewood.core.parser.JsoupRoomParser;
import com.honsul.inthewood.core.util.NationalResortUtils;
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 국립자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="National")
public class NationalRoomParser extends JsoupRoomParser {
  private final static Pattern P_ROOM_INFO = Pattern.compile("(\\d+)인실/(.*)");
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    Map<String, String> loginCookies = NationalResortUtils.loginCookies();
    for(Entry<String, String> entry : NationalResortInfo.MAPPINGS.entrySet()) {
      String resortId = entry.getKey();
      String departCode = entry.getValue();
      Document doc = NationalResortUtils.bookinDocument(departCode, loginCookies);
      if(doc != null) {
        doc.selectFirst("head").attr("RESORT_ID", resortId);
        documentList.add(doc);
      }
    }
    return documentList;
  }
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();
    
    for(Element td : doc.select("div.ins_current_table_wrap > table.table_wrap_04 > tbody > tr > td.locked_left")) {
      RoomType roomType = getRoomType(td.select("span.room_icon icon_yeon_l").text());
      String roomText = td.select("a.room_tit").text();
      
      String roomNm = StringUtils.substringBefore(roomText, "(").trim();
      String roomInfo = TextUtils.stringInBrackets(roomText);
      Matcher matcher = P_ROOM_INFO.matcher(roomInfo);
      if(matcher.find()) {
        String people = matcher.group(1);
        long price = NationalResortUtils.price(roomType, people);
        long peakPrice = NationalResortUtils.peakPrice(roomType, people);
        String space = matcher.group(2);
        
        roomList.add(Room.builder()
            .resortId(doc.selectFirst("head").attr("RESORT_ID"))
            .roomNm(roomNm)
            .roomType(roomType)
            .space(space)
            .numberOfPeople(people)
            .peakPrice(peakPrice)
            .price(price)
            .build());
      }
    }
    
    return roomList;
  }
  
  @Override
  public RoomType getRoomType(String roomTypeNm) {
    return StringUtils.contains(roomTypeNm, "휴양관") ? RoomType.CONDO : RoomType.HUT;
  }

}
