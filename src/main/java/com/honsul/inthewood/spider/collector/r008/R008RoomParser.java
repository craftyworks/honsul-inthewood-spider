package com.honsul.inthewood.spider.collector.r008;

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
 * 봉수산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R008")
public class R008RoomParser extends JsoupRoomParser {
  
  private static final String CONNECT_URL = "http://www.bongsoosan.com/utilization.asp?location=003";

  @Override
  protected Document document() throws IOException {
    return Jsoup.connect(CONNECT_URL).get();
  }
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();

    String roomNm = "";
    String roomTypeNm = "";
    String numberOfPeople = "";
    String space = "";
    long price = 0, peakPrice = 0;

    for(Element row : doc.select("div#contents > div.utilization > table:last-child > tbody > tr")) {
      Elements tds = row.select("td");
      if(row.selectFirst("th") != null) {
        roomTypeNm = row.selectFirst("th").text();
      }
      roomNm = tds.get(0).text();
      if(tds.size() > 1) {
        space = StringUtils.substringBefore(tds.get(1).text(), "(");
        numberOfPeople = TextUtils.stripCursor(tds.get(1).text()).replaceAll("인실",  "");
        peakPrice = TextUtils.parseLong(tds.get(3).text());
        price = TextUtils.parseLong(tds.get(4).text()); 
      }
      Room room = new Room();
      room.setResortId(SpiderContext.getResortId());
      room.setRoomNo(roomNm);
      room.setRoomNm(roomNm);
      room.setRoomType(getRoomType(roomTypeNm));
      room.setSpace(space);
      room.setNumberOfPeople(numberOfPeople);
      room.setPeakPrice(peakPrice);
      room.setPrice(price);
      roomList.add(room);      
    }
    return roomList;
  }
  
  @Override
  public RoomType getRoomType(String roomTypeNm) {
    return StringUtils.contains(roomTypeNm, "숲속의집")? RoomType.HUT : RoomType.CONDO;
  }

}
