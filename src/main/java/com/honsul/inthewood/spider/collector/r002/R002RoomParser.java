package com.honsul.inthewood.spider.collector.r002;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 충북알프스자연휴양림 숙소현황 파서.
 * 
 * <p>예약현황 페이지에서 숙소 별 예약 페이지로 이동하여 숙소 정보를 추출
 */
@RoomParser(resortId="R002")
public class R002RoomParser implements Parser<Room>{

  private static final String CONNECT_URL = "http://alpshuyang.boeun.go.kr/reservation.asp?location=002";
  
  private static final String ROOM_URL = "http://alpshuyang.boeun.go.kr/reservation.asp?location=002_02";
  
  private static final Pattern ROOM_INFO_PATTERN = Pattern.compile("^기준인원([0-9]+)명사용료일반성수기:([0-9]+)원비수기:([0-9]+)원.*$");
  
  /**
   * 이번달 예약현황 페이지 이동
   */
  private Document thisMonth() {
    try {
      return Jsoup.connect(CONNECT_URL).get();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * 숙소 예약 페이지 이동
   */
  private Document reservePage(String arguments) {
    try {
     return Jsoup.connect(ROOM_URL).header("Referer", CONNECT_URL).data("rsv_info", arguments).post();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private List<Room> extract(Document doc) {
    // 중복 제거된 숙소목록 추출
    Map<String, String> roomMap = new HashMap<>();
    for(Element row : doc.select("#contents form[action^=reservation.asp]")) {
      String attrValue = row.selectFirst("input[name=rsv_info]").attr("value");
      String[] attr = attrValue.split("#@");
      String roomNo = attr[1];
      if(!roomMap.containsKey(roomNo)) {
        roomMap.put(roomNo, attrValue);
      }
    }
    
    // 숙소별로 예약 페이지 이동하여 정보 추출
    List<Room> roomList = new ArrayList<>();
    for(Entry<String, String> entry : roomMap.entrySet()) {
      Document resvDoc = reservePage(entry.getValue());
      String roomNm = resvDoc.selectFirst("#form1>div.reserv_input_tit>h3").text();
      String roomTypeNm = roomNm.split("[0-9]")[0];
      RoomType roomType = getRoomType(roomTypeNm);
      
      //[기준인원] 4명 [사용료] 일반 성수기 : 53,000원, 비수기 : 53,000원
      String roomInfo = resvDoc.selectFirst("#form1>div.reserv_input_tit>p").text().replaceAll("[,\\[\\]\\s*]", "");
      Matcher m = ROOM_INFO_PATTERN.matcher(roomInfo);
      if(m.find()) {
        String occupancy = m.group(1);
        long peakPrice = Long.parseLong(m.group(2));
        long price = Long.parseLong(m.group(3));
        Room room = new Room();
        room.setResortId(SpiderContext.getResortId());
        room.setRoomNo(entry.getKey());
        room.setRoomNm(roomNm);
        room.setRoomType(roomType);
        room.setNumberOfPeople(occupancy);
        room.setPeakPrice(peakPrice);
        room.setPrice(price);
        roomList.add(room);
      }
    }
    
    return roomList;
  }
  
  private RoomType getRoomType(String roomNm) {
    if(TextUtils.contains(roomNm, new String[] {"알프스빌리지", "숲속의작은집", "숲속의집"})) {
      return RoomType.HUT;
    }
    return RoomType.CONDO;
  }

  @Override
  public List<Room> parse() {
    
    List<Room> roomList = new ArrayList<>();
    
    roomList.addAll(extract(thisMonth()));
    
    return roomList;
  }
}
