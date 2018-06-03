package com.honsul.inthewood.spider.collector.r012;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * 축령산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R012")
public class R012RoomParser extends JsoupRoomParser {
  
  private static final String HUT_URL = "http://farm.gg.go.kr/sigt/43?tab=1";
  private static final String CONDO_URL = "http://farm.gg.go.kr/sigt/43?tab=3";

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

    Elements divs = doc.select("div.inside-content");
    for(Element div : divs) {
      if(div.selectFirst("h3") == null) {
        continue;
      }
      List<String> roomNames = new ArrayList<>();
      
      String[] title = div.selectFirst("h3").text().replace("-", "").split("\\s+");
      logger.debug("title:{}, {}", title, title.length);
      String roomTypeNm = title[0];
      String space = title[1];

      if("숲속의집".equals(roomTypeNm)) {
        roomNames.add(title[2]);
      } else { //휴양관 
        for(Element li : div.select("div:first-of-type > ul > li")) {
          String[] rooms = StringUtils.substringAfter(li.text(), ":").split("\\s+");
          roomNames.addAll(Arrays.asList(rooms));
        }
      }
      
      Elements tds = div.select("div.conDiv > table.des_table:first-of-type > tbody > tr > td");
      String numberOfPeople = tds.get(1).text().replace("명", "");
      String priceTag = tds.last().text();
      
      Pattern p = Pattern.compile("([0-9,]+)원");
      Matcher m = p.matcher(priceTag);
      long price = 0, peakPrice = 0;
      if(m.find()) {
        price = TextUtils.parseLong(m.group(1));
      }
      if(m.find()) {
        peakPrice = TextUtils.parseLong(m.group(1));
      }
        
      for(String roomName : roomNames) {
        roomName = StringUtils.substringBefore(roomName, "(");
        Room room = new Room();
        room.setResortId(SpiderContext.getResortId());
        room.setRoomNo(roomName);
        room.setRoomNm(roomName);
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
    return "숲속의집".equals(roomTypeNm) ? RoomType.HUT : RoomType.CONDO;
  }

}
