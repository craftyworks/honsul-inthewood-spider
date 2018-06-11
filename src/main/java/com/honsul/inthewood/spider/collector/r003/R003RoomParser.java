package com.honsul.inthewood.spider.collector.r003;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;
import com.honsul.inthewood.core.parser.AbstractRoomParser;
import com.honsul.inthewood.core.util.TextUtils;
import com.honsul.inthewood.core.util.WebDriverUtils;

/**
 * 광치자연휴양림 숙소현황 파서.
 * 
 * <p>예약현황 페이지에서 숙소 별 예약 페이지로 이동하여 숙소 정보를 추출
 */
@RoomParser(resortId="R003")
public class R003RoomParser extends AbstractRoomParser {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private static final String[] HOUSE_URLS = {
      "https://www.kwangchi.or.kr:451/facilities.asp?location=002",
      "https://www.kwangchi.or.kr:451/facilities.asp?location=002_02",
      "https://www.kwangchi.or.kr:451/facilities.asp?location=002_03",
      "https://www.kwangchi.or.kr:451/facilities.asp?location=002_04",
      "https://www.kwangchi.or.kr:451/facilities.asp?location=002_05",
      "https://www.kwangchi.or.kr:451/facilities.asp?location=002_06",
      "https://www.kwangchi.or.kr:451/facilities.asp?location=002_07",
      "https://www.kwangchi.or.kr:451/facilities.asp?location=001"
  };
  
  @Override
  public List<Document> getPages() {
    return WebDriverUtils.getDocuments(HOUSE_URLS);
  }

  @Override
  public List<Room> extract(Document doc) {
    List<Room> roomList = new ArrayList<>();
    
    String roomTypeNm = doc.selectFirst("div#contents>div.cont_title>h3").text().replaceAll("\\s*",  "");
    for(Element tr : doc.select("div#contents>div.facilities>table>tbody>tr")) {
      Elements tds = tr.select("td");
      String roomNm = tds.get(0).text();
      String space = tds.get(1).text();
      String numberOfPeople = tds.get(2).text();
      long peakPrice = TextUtils.parseLong(tds.get(3).text());
      String sPrice = StringUtils.substringBefore(tds.get(5).text(), "(");
      long price = TextUtils.parseLong(sPrice);
      
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
    
    logger.debug("parsed Room List count : {}", roomList.size());
    
    return roomList;
  }

  @Override
  protected RoomType getRoomType(String roomNm) {
    return RoomType.getRoomType(roomNm);
  }

}
