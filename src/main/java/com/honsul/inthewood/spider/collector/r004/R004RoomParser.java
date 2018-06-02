package com.honsul.inthewood.spider.collector.r004;

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
 * 문성자연휴양림 숙소현황 파서.
 * 
 * <p>JSoup 으로 처리. 예약페이지 내 메뉴판에서 스크랩
 */
@RoomParser(resortId="R004")
public class R004RoomParser extends JsoupRoomParser {
  
  private static final String CONNECT_URL = "http://msf.cj100.net/reservation.asp?location=001";
  
  @Override
  protected Document document() throws IOException {
    return Jsoup.connect(CONNECT_URL).header("Referer", CONNECT_URL).get();
  }
  
  @Override
  public List<Room> extract(Document doc) {

    List<Room> roomList = new ArrayList<>();
    
    String roomNm = "";
    String numberOfPeople = "";
    String space = "";
    long price = 0, peakPrice = 0;
    
    for(Element row : doc.select("div#snb>div.group>table>tbody>tr")) {
      roomNm = row.selectFirst("th").text();
      Elements tds = row.select("td");
      if(!tds.isEmpty()) {
        String temp = tds.get(0).text();
        numberOfPeople = StringUtils.substringBefore(temp, "인");
        space = TextUtils.stripCursor(temp);
        peakPrice = TextUtils.parseLong(tds.get(1).text());
        price = TextUtils.parseLong(tds.get(2).text());
      }
      
      Room room = new Room();
      room.setResortId(SpiderContext.getResortId());
      room.setRoomNo(roomNm);
      room.setRoomNm(roomNm);
      room.setRoomType(getRoomType(roomNm));
      room.setNumberOfPeople(numberOfPeople);
      room.setSpace(space);
      room.setPeakPrice(peakPrice);
      room.setPrice(price);
      roomList.add(room);
    }
    
    logger.debug("parsed Room List count : {}", roomList.size());
    
    return roomList;
  }
  
  @Override
  public RoomType getRoomType(String roomNm) {
    if(TextUtils.contains(roomNm, new String[] {"채송화", "민들레", "원추리", "수선화", "제비꽃"})) {
      return RoomType.HUT;
    }
    return RoomType.CONDO;
  }

}
