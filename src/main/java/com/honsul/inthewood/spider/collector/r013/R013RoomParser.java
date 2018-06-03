package com.honsul.inthewood.spider.collector.r013;

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
 * 박달재자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R013")
public class R013RoomParser extends JsoupRoomParser {
  
  private static final String HUT_URL = "http://baf.cbhuyang.go.kr/facilities.asp?location=";
  private static final String[] LOCATIONS = {"001", "002", "003", "004", "005", "007"};
  
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

    String roomTypeNm = doc.select("div#content > div.header > h2").text();
    for(Element row : doc.select("div#facilities > table.house_table > tbody > tr")) {
      List<String> roomNames = new ArrayList<>();
      String roomNm = row.selectFirst("th").text();
      if(StringUtils.contains(roomNm, ",")) {
        //휴양관
        String[] names = roomNm.replaceAll("\\[.*\\]", "").trim().split(",");
        roomNames.addAll(Arrays.asList(names));
      } else {
        roomNames.add(roomNm);
      }
      
      Elements tds = row.select("td");
      if(!tds.isEmpty()) {
        space = tds.get(0).text();
        numberOfPeople = tds.get(1).text().replaceAll("명",  "");
        price = TextUtils.parseLong(tds.get(2).text()); 
        peakPrice = TextUtils.parseLong(tds.get(3).text());
      }
      
      for(String nm : roomNames) {
        Room room = new Room();
        room.setResortId(SpiderContext.getResortId());
        room.setRoomNo(nm);
        room.setRoomNm(nm);
        room.setRoomType(getRoomType(roomTypeNm));
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
  public RoomType getRoomType(String roomTypeNm) {
    return StringUtils.contains(roomTypeNm, "복합산막") ? RoomType.CONDO : RoomType.HUT;
  }

}
