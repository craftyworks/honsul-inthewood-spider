package com.honsul.inthewood.spider.collector.r015;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;
import com.honsul.inthewood.core.parser.JsoupRoomParser;

/**
 * 남이자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R015")
public class R015RoomParser extends JsoupRoomParser {
  
  private static final String CONNECT_URL = "http://forestown.geumsan.go.kr/_prog/reserve/checkroom.php";
  
  private static final Map<String, long[]> PRICE_MAP = new HashMap<>();
  static {
    PRICE_MAP.put("CONDO|10", new long[] {90000, 130000});
    PRICE_MAP.put("HUT|4", new long[] {50000, 70000});
    PRICE_MAP.put("HUT|6", new long[] {60000, 90000});
    PRICE_MAP.put("층층나무방", new long[] {90000, 130000});
    PRICE_MAP.put("고로쇠나무방", new long[] {90000, 130000});
    PRICE_MAP.put("산벚나무", new long[] {95000, 140000});
    PRICE_MAP.put("구상나무", new long[] {95000, 140000});
    PRICE_MAP.put("느티방", new long[] {130000, 190000});
    PRICE_MAP.put("잣나무방", new long[] {190000, 250000});
  }
  
  @Override
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    
    LocalDate now = LocalDate.now();

    //this month
    documentList.add(Jsoup.connect(CONNECT_URL).data("code", "forest").data("year", ""+now.getYear()).data("mon", ""+now.getMonthValue()).post());
    documentList.add(Jsoup.connect(CONNECT_URL).data("code", "edu1").data("year", ""+now.getYear()).data("mon", ""+now.getMonthValue()).post());
    
    return documentList;
  }
  

  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();

    for(Element row : doc.select("div.checkroom_tb > table.table2 > tbody > tr > th")) {
      String roomNm = StringUtils.substringBefore(row.text(), "-");
      String numberOfPeople = StringUtils.substringAfter(row.text(), "-").replaceAll("인실",  "");
      RoomType roomType = getRoomType(roomNm);
      
      String key = roomNm;
      if(PRICE_MAP.containsKey(roomType.getCode() + "|" + numberOfPeople)) {
        key = roomType.getCode() + "|" + numberOfPeople;
      }
      long price = PRICE_MAP.get(key)[0];
      long peakPrice = PRICE_MAP.get(key)[1];
       
      Room room = new Room();
      room.setResortId(SpiderContext.getResortId());
      room.setRoomNm(roomNm);
      room.setRoomType(roomType);
      room.setNumberOfPeople(numberOfPeople);
      room.setPeakPrice(peakPrice);
      room.setPrice(price);
      roomList.add(room);
    }
    return roomList;
  }
  
  @Override
  public RoomType getRoomType(String roomNm) {
    return StringUtils.contains(roomNm, "산림휴양관") ? RoomType.CONDO : RoomType.HUT;
  }

}
