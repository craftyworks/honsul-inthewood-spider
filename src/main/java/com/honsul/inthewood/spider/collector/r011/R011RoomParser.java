package com.honsul.inthewood.spider.collector.r011;

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
 * 강씨봉자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R011")
public class R011RoomParser extends JsoupRoomParser {
  
  private static final String HUT_URL = "http://farm.gg.go.kr/sigt/60?tab=2";
  private static final String CONDO_URL = "http://farm.gg.go.kr/sigt/60?tab=3";

  @Override
  protected List<Document> documents() throws IOException {
    List<Document> docs = new ArrayList<>();
    docs.add(Jsoup.connect(HUT_URL).get());
    docs.add(Jsoup.connect(CONDO_URL).get());
    return docs;
  }
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();

    String roomNm = "";
    String numberOfPeople = "";
    String space = "";
    long price = 0, peakPrice = 0;

    for(Element row : doc.select("table.des_table > tbody > tr")) {
      Elements tds = row.select("td");
      roomNm = tds.get(0).text();
      space = TextUtils.stripCursor(roomNm);
      roomNm = StringUtils.substringBefore(roomNm,  "(").trim();
      if(tds.size() > 1) {
        numberOfPeople = tds.get(1).text().replaceAll("명",  "");
        peakPrice = TextUtils.parseLong(tds.get(2).text());
        price = TextUtils.parseLong(tds.get(3).text()); 
      }
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
    return StringUtils.contains(roomNm, "나무") || StringUtils.contains(roomNm, "주목") ? RoomType.CONDO : RoomType.HUT;
  }

}
