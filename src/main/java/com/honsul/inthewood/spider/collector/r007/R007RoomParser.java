package com.honsul.inthewood.spider.collector.r007;

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
 * 가리산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R007")
public class R007RoomParser extends JsoupRoomParser {
  
  private static final String CONNECT_URL = "http://garisan.nowr-b.net/member/?co_id=garisan&ref=&buyer=&m_out=&c_area=";

  protected Document document() throws IOException {
    Document doc = Jsoup.connect(CONNECT_URL).get();
    String pageUrl = doc.selectFirst("a[href^=http://garisan.nowr-b.net/m_member/room_check.html]").attr("href");
    
    return Jsoup.connect(pageUrl).get();
  }
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();
    
    for(Element row : doc.select("body > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > table:nth-child(6) > tbody > tr")) {
      Elements tds = row.select("td");
      if(tds.get(0).select("input").isEmpty()) {
        continue;
      }
      String roomNm = StringUtils.substringBeforeLast(tds.get(1).text(), "(");
      String space = TextUtils.stringInLastBrackets(tds.get(1).text());
      String numberOfPeople = tds.get(2).text();
      long price = TextUtils.parseLong(tds.get(5).text()); 
      long peakPrice = TextUtils.parseLong(tds.get(6).text());
      
      Room room = new Room();
      room.setResortId(SpiderContext.getResortId());
      room.setRoomNm(roomNm);
      room.setRoomType(getRoomType(roomNm));
      room.setSpace(space);
      room.setNumberOfPeople(numberOfPeople);
      room.setPeakPrice(peakPrice);
      room.setPrice(price);
      roomList.add(room);      
    }
    return roomList;
  }
  
  @Override
  public RoomType getRoomType(String roomNm) {
    return StringUtils.contains(roomNm, "휴양관")? RoomType.CONDO : RoomType.HUT;
  }

}
