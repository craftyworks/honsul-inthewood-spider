package com.honsul.inthewood.spider.collector.r016;

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
 * 칠갑산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R016")
public class R016RoomParser extends JsoupRoomParser {
  
  private static final String HUT_URL = "http://chilgap.cheongyang.go.kr/facilities.asp?location=";
  private static final String[] LOCATIONS = {
      "001", "001_02", "001_03", "001_04", "001_05", "001_06", "001_07", "001_08", "001_09", "001_10", "001_11", "002_2", "002_3", "002_4", "002_5"};
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> docs = new ArrayList<>();
    for(String location : LOCATIONS) {
      docs.add(Jsoup.connect(HUT_URL + location).get());
    }
    return docs;
  }
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();

    String numberOfPeople = "";
    String space = "";
    long price = 0, peakPrice = 0;

    String roomTypeNm = doc.select("div#top > h2 > img").attr("alt");
    for(Element row : doc.select("div.facilities > table.fac_table > tbody > tr")) {
      Elements tds = row.select("td");
      String roomNm = tds.get(0).text();
      space = TextUtils.stripCursor(roomNm);
      roomNm = StringUtils.substringBefore(roomNm, "(");
      if(tds.size() > 1) {
        numberOfPeople = tds.get(1).text().replaceAll("명",  "");
        peakPrice = TextUtils.parseLong(tds.get(2).text());
        price = TextUtils.parseLong(tds.get(3).text()); 
      }

      Room room = new Room();
      room.setResortId(SpiderContext.getResortId());
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
    return "숲속의집".equals(roomTypeNm) ? RoomType.HUT : RoomType.CONDO;
  }

}
