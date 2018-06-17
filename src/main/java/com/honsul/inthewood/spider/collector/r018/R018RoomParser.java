package com.honsul.inthewood.spider.collector.r018;

import java.io.IOException;
import java.util.ArrayList;
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
    
    String roomTypeNm = doc.selectFirst("div#container > div.wrap > h3").text().replaceAll("\\s", "");
    RoomType roomType = getRoomType(roomTypeNm);
    
    for(Element tr : doc.select("div#detail_con > div.tableScroll > table > tbody > tr")) {
      Elements roomNames = tr.select("ul > li");
      
      int idx = RoomType.HUT.equals(roomType) ? 1 : 2;
      
      Elements tds = tr.select("td");
      String people = tds.get(idx++).text();
      String space = "";
      String priceText = tds.get(idx++).text();
      long price = 0, peakPrice = 0;
      Pattern p = Pattern.compile("수용인원 : ([0-9]+)인[\\s,]*실당 총면적 : ([0-9\\.]+㎡)");
      Matcher m = p.matcher(people);
      if(m.find()) {
        people = m.group(1);
        space = m.group(2);
      }
      
      if(RoomType.HUT.equals(roomType)) {
        Pattern pricePattern = Pattern.compile("성수기 : ([0-9,]+) 비성수기\\(공휴일\\) : ([0-9,]+) 비성수기\\(평일\\) : ([0-9,]+)");
        Matcher priceMatcher = pricePattern.matcher(priceText);
        priceMatcher.find();
        peakPrice = TextUtils.findMoneyLong(priceMatcher.group(1));
        price = TextUtils.findMoneyLong(priceMatcher.group(3));
      } else {
        Pattern pricePattern = Pattern.compile("일반 : ([0-9,]+) 휴일 : ([0-9,]+) 성수기 : ([0-9,]+)");
        Matcher priceMatcher = pricePattern.matcher(priceText);
        priceMatcher.find();
        price = TextUtils.findMoneyLong(priceMatcher.group(1));
        peakPrice = TextUtils.findMoneyLong(priceMatcher.group(3));        
      }
      for(Element roomName : roomNames) {
        String name = RoomType.HUT.equals(roomType) ? StringUtils.substringBefore(roomName.text(), " ") : TextUtils.stringInBrackets(roomName.text());
        
        Room room = new Room();
        room.setResortId(SpiderContext.getResortId());
        room.setRoomNm(name);
        room.setRoomType(roomType);
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
    return StringUtils.equals(roomTypeNm, "숲속의집") ? RoomType.HUT : RoomType.CONDO;
  }

}
