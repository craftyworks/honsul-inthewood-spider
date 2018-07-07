package com.honsul.inthewood.spider.collector.r004;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;
import com.honsul.inthewood.core.parser.JsoupRoomParser;
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 문성자연휴양림 숙소현황 파서.
 * 
 */
@RoomParser(resortId="R004")
public class R004RoomParser extends JsoupRoomParser {
  
  private static final String CONNECT_URL = "http://msf.cj100.net/facilities.asp?location=";
  
  private static final String[] LOCATIONS = {
      "001", "001_01", "001_02", "001_03", "001_04",
      "002", "002_01", "002_02", "002_03",
      "003", "003_01", "003_02", "003_03",
      "004", "004_01", 
      "008_01", "008_02", "008_03", "008_04"
  };

  private static final Pattern PATTERN_ROOM = Pattern.compile("([0-9]+)호[~\\+]{1}([0-9]+)호");
  
  private static final Pattern PATTERN_PRICE = Pattern.compile("성수기([0-9,]+) / 비수기([0-9,]+)");
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> docs = new ArrayList<>();
    for(String loc : LOCATIONS) {
      docs.add(Jsoup.connect(CONNECT_URL + loc).get());
    }
    return docs;
  }
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();

    List<String> roomNames = new ArrayList<>();
    String roomTypeNm = doc.selectFirst("div#nav > h1 > img").attr("alt").replaceAll("\\s", "");
    RoomType roomType = getRoomType(roomTypeNm);
    String roomNm = doc.selectFirst("div#contents > div.house_info > h2 > img").attr("alt").replaceAll("\\s", "").replaceAll(roomTypeNm, "");
    
    String space = null;
    String people = null;
    long peakPrice = 0, price = 0;
    if("휴양관".equals(roomTypeNm)) {
      Elements tds = doc.select("div#contents > div.house_info > table.facil > tbody > tr > td");
      String title = tds.get(0).text();
      Matcher m = PATTERN_ROOM.matcher(title);
      if(m.find()) {
        int start = Integer.parseInt(m.group(1));
        int end =  Integer.parseInt(m.group(2));
        if(StringUtils.contains(title, "단체실")) {
          roomNames.add("휴양관" + start +"호");
        } else {
          for(int i = start; i <=end; i++) {
            roomNames.add("휴양관" + i + "호");
          }
        }
      }
      space = tds.get(1).text();
      people = tds.get(2).text().replaceAll("[\\s명]", "");
      peakPrice = TextUtils.parseLong(tds.get(3).text());
      price = TextUtils.parseLong(tds.get(4).text());
    } else {
      roomNames.add(roomNm);
      Elements dds = doc.select("div#contents > div.house_info > dl > dd");
      space = dds.get(1).text();
      String priceTag = dds.get(2).text();
      Matcher m = PATTERN_PRICE.matcher(priceTag);
      if(m.find()) {
        peakPrice = TextUtils.parseLong(m.group(1));
        price = TextUtils.parseLong(m.group(2));
      }
      people = dds.get(3).text().replaceAll("[\\s명]", "");
    }    
    
    for(String name : roomNames) {
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
    
    logger.debug("parsed Room List count : {}", roomList.size());
    
    return roomList;
  }
  
  @Override
  public RoomType getRoomType(String roomTypeNm) {
    return "숲속의집".equals(roomTypeNm) ? RoomType.HUT : RoomType.CONDO;
  }

}
