package com.honsul.inthewood.spider.collector.r010;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 군위장곡자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R010")
public class R010RoomParser extends JsoupRoomParser {
  
  /** 요금테이블 URL */
  private static final String CHARGE_URL = "http://janggok.gunwi.go.kr/new/guide/guide.html#charge";
  private static final String CONNECT_URL = "https://janggok.gunwi.go.kr:6449/new/reservation/reserve_status.html?todayed=";
  
  @Override
  protected Document document() throws IOException {
    return Jsoup.connect(CONNECT_URL).get();
  }
  
  /**
   * 시설이용요금 메뉴판 리턴
   */
  private Map<String, String[]> priceList() {
    Map<String, String[]> priceMap = new HashMap<>();
    
    Document chargeDoc;
    try {
      chargeDoc = Jsoup.connect(CHARGE_URL).get();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    for(Element tr : chargeDoc.select("div#cntDetail > table:first-of-type > tbody > tr")) {
      Elements tds = tr.select("td");
      
      String space = StringUtils.substringBefore(tds.get(tds.size()-4).text(), "(");
      String price = tds.last().text();
      String numberOfPeople = TextUtils.stringInBrackets(tds.get(tds.size()-4).text()).replaceAll("명", "");
      priceMap.put(space, new String[] {price, numberOfPeople});
    }
    
    return priceMap;
  }
  
  @Override
  public List<Room> extract(Document doc) {
    Map<String, String[]> priceList = priceList();
    List<Room> roomList = new ArrayList<>();
    
    for(Element row : doc.select("#cntDetail table>tbody>tr")) {
      if(row.selectFirst("td") == null) {
        continue;
      }
      String title = row.selectFirst("td").text();
      String roomNm = StringUtils.substringBefore(title,  "(");
      String space = TextUtils.stringInBrackets(title);
      long peakPrice = TextUtils.parseLong(priceList.get(space)[0]);
      String numberOfPeople = priceList.get(space)[1];
      
      Room room = new Room();
      room.setResortId(SpiderContext.getResortId());
      room.setRoomNm(roomNm);
      room.setRoomType(getRoomType(roomNm));
      room.setSpace(space);
      room.setNumberOfPeople(numberOfPeople);
      room.setPeakPrice(peakPrice);
      room.setPrice(peakPrice);
      roomList.add(room);   
    }

    return roomList;
  }
  
  @Override
  public RoomType getRoomType(String roomTypeNm) {
    return StringUtils.contains(roomTypeNm, "숲속의집")? RoomType.HUT : RoomType.CONDO;
  }

}
