package com.honsul.inthewood.spider.collector.r009;

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
 * 구재봉자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R009")
public class R009RoomParser extends JsoupRoomParser {
  
  private static final String CONNECT_URL = "http://hadongforest.co.kr/MAIN/c/GUIDE/fee";

  @Override
  protected Document document() throws IOException {
    return Jsoup.connect(CONNECT_URL).get();
  }
  
  private List<Room> extractInternal(Document doc, int tableIndex) {
    List<Room> roomList = new ArrayList<>();

    String roomNm = "";
    String roomTypeNm = "";
    String numberOfPeople = "";
    long price = 0, peakPrice = 0;

    for(Element row : doc.select("div#dvContents > div.dvContentsInner > table:nth-of-type(" + tableIndex + ") > tbody > tr")) {
      Elements tds = row.select("td");
      roomNm = row.selectFirst("th").text();
      if("전시설".equals(roomNm)) {
        continue;
      }
      roomTypeNm = StringUtils.contains(roomNm, "숲속휴양관") ? "휴양관" : "숲속의집";
      String roomNo = StringUtils.contains(roomNm, "숲속휴양관") ? StringUtils.substringBefore(roomNm, "(") : StringUtils.substringAfter(roomNm, ")");
      numberOfPeople = TextUtils.stringInBrackets(roomNm).replaceAll("인",  "");
      
      Elements priceRow = tds.get(3).select("table > tbody > tr > td");
      price = TextUtils.parseLong(priceRow.get(0).text()); 
      peakPrice = TextUtils.parseLong(priceRow.get(1).text());

      Room room = new Room();
      room.setResortId(SpiderContext.getResortId());
      room.setRoomNm(roomNo);
      room.setRoomType(getRoomType(roomTypeNm));
      room.setNumberOfPeople(numberOfPeople);
      room.setPeakPrice(peakPrice);
      room.setPrice(price);
      roomList.add(room);      
    }
    return roomList;
  }
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();

    roomList.addAll(extractInternal(doc, 1));
    roomList.addAll(extractInternal(doc, 3));
    roomList.addAll(extractInternal(doc, 3));
    
    return roomList;
  }
  
  @Override
  public RoomType getRoomType(String roomTypeNm) {
    return StringUtils.contains(roomTypeNm, "숲속의집")? RoomType.HUT : RoomType.CONDO;
  }

}
