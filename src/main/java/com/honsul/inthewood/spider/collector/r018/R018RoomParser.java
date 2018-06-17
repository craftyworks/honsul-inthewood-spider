package com.honsul.inthewood.spider.collector.r018;

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
 * 석모도자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R018")
public class R018RoomParser extends JsoupRoomParser {
  
  private static final String[] LOCATIONS = {
      "http://forest.ganghwa.go.kr/facility/resort4.jsp"
    , "http://forest.ganghwa.go.kr/facility/house.jsp"
  };
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> docs = new ArrayList<>();
    for(String location : LOCATIONS) {
      docs.add(Jsoup.connect(location).get());
    }
    return docs;
  }
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();

    String numberOfPeople = "";
    String space = "";
    long price = 0, peakPrice = 0;
    
    System.out.println(doc.select("ol.location"));

    String roomTypeNm = StringUtils.substringBefore(doc.select("table.table_st2 > caption").text(), "(");
    for(Element row : doc.select("table.table_st2 > tbody > tr")) {
      Elements tds = row.select("td");
      String roomNm = tds.first().text().replaceAll("\\s", "");
      
      //백두대간문화휴양관 예외처리
      if(StringUtils.contains(roomTypeNm, "백두대간문화휴양관")) {
        roomNm = "(백)" + roomNm;
      }
      //도담산봉
      if("도담산봉".equals(roomNm)) {
        roomNm = "도담삼봉";
      }
      if(tds.size() > 1) {
        space = tds.get(1).text();
        numberOfPeople = tds.get(2).text().replaceAll("명",  "");
        peakPrice = TextUtils.parseLong(tds.get(4).text().replaceAll("원",  ""));
        price = TextUtils.parseLong(tds.get(5).text().replaceAll("원",  "")); 
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
    return StringUtils.contains(roomTypeNm, "집") ? RoomType.HUT : RoomType.CONDO;
  }

}
