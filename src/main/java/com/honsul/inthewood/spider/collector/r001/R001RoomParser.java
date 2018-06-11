package com.honsul.inthewood.spider.collector.r001;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
 * 좌구산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R001")
public class R001RoomParser extends JsoupRoomParser {
  
  private static final String CONNECT_URL = "http://jwagu.jp.go.kr/huyang.do";

  @Override
  protected Document document() throws IOException {
    return Jsoup.connect(CONNECT_URL).data("cmd", "facility.view").post();
  }
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();
    
    String roomTypeNm = "";
    for(Element row : doc.select("div#contents > div.huyang > table > tbody > tr")) {
      if(row.selectFirst("th.first") != null) {
        roomTypeNm = row.selectFirst("th.first").text();
      }
      Elements tds = row.select("td");
      String[] roomNames = tds.get(0).html().replace("<br>", ",").replaceAll("\\s",  "").split(",");
      roomNames = Arrays.stream(roomNames).filter(s -> !s.isEmpty()).toArray(String[]::new);
      
      String people = StringUtils.substringBefore(tds.get(1).text(), "(").replaceAll("[^0-9]*", "");
      String space = TextUtils.stringInBrackets(tds.get(1).text());
      long peakPrice = TextUtils.parseLong(tds.get(3).selectFirst("em").text());
      long price = TextUtils.parseLong(tds.get(4).selectFirst("em").text());
      
      for(String roomName : roomNames) {
        Room room = new Room();
        room.setResortId(SpiderContext.getResortId());
        room.setRoomNm(roomName);
        room.setRoomType(getRoomType(roomTypeNm));
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
    return StringUtils.contains(roomTypeNm, "별무리하우스") ? RoomType.CONDO : RoomType.HUT;
  }

}
