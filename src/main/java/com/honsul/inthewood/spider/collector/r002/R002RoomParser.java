package com.honsul.inthewood.spider.collector.r002;

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
 * 충북알프스자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R002")
public class R002RoomParser extends JsoupRoomParser {

  private static final String[] ROOM_URLS = {
    "http://alpshuyang.boeun.go.kr/facilities.asp?location=001",
    "http://alpshuyang.boeun.go.kr/facilities.asp?location=002",
    "http://alpshuyang.boeun.go.kr/facilities.asp?location=003",
    "http://alpshuyang.boeun.go.kr/facilities.asp?location=006",
    "http://alpshuyang.boeun.go.kr/facilities.asp?location=007",
    "http://alpshuyang.boeun.go.kr/facilities.asp?location=004"
  };
  
  @Override
  public List<Document> documents() throws IOException {
    List<Document> pages = new ArrayList<>();
    for(String url : ROOM_URLS) {
      pages.add(Jsoup.connect(url).get());
    }
    return pages;
  }
  
  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();
    String roomTypeNm = doc.selectFirst("div#contents>div.con_title>h2>img").attr("alt");
    String roomNm = "";
    String space = "";
    String numberOfPeople = "";
    long peakPrice = 0;
    long price = 0;
    for(Element tr : doc.select("div#contents>table.house_table>tbody>tr")) {
      Elements tds = tr.select("td");
      roomNm = tds.get(0).text();
      if(tds.size() > 3) {
        space = tds.get(1).text();
        numberOfPeople = tds.get(2).text();
        peakPrice = TextUtils.findMoneyLong(tds.get(3).text());
        if(tds.size() > 5) {
          String sPrice = StringUtils.substringAfter(tds.get(5).text(), "(30%)");
          price = TextUtils.parseLong(sPrice);
        } else {
          price = peakPrice;
        }
      }
      
      Room room = new Room();
      room.setResortId(SpiderContext.getResortId());
      room.setRoomNo(roomNm);
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
  
  protected RoomType getRoomType(String roomNm) {
    if(TextUtils.contains(roomNm, new String[] {"알프스빌리지", "숲속의작은집", "숲속의집"})) {
      return RoomType.HUT;
    }
    return RoomType.CONDO;
  }

}
