package com.honsul.inthewood.spider.collector.r007;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;
import com.honsul.inthewood.core.parser.JsoupRoomParser;
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 가리산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="R007")
public class R007RoomParser extends JsoupRoomParser {
  
  private static final String CONNECT_URL = "http://garisan.nowr-b.net/member/?co_id=garisan&ref=&buyer=&m_out=&c_area=";

  private static final Pattern PATTERN_PARAM = Pattern.compile("&room_num=([0-9]*)&");
  
  @Override
  protected List<Document> documents() throws IOException {
    Map<String, Document> docs = new HashMap<>();
    
    Document doc = Jsoup.connect(CONNECT_URL).get();
    for(Element elm : doc.select("a[href^=http://garisan.nowr-b.net/m_member/room_check.html]")) {
      String pageUrl = elm.attr("href");
      Matcher m = PATTERN_PARAM.matcher(pageUrl);
      if(m.find()) {
        String key = m.group(1);
        if(!docs.containsKey(key)) {
          docs.put(key, Jsoup.connect(pageUrl).get());
        }
      }
    }
    return new ArrayList<Document>(docs.values());
  }

  @Override
  protected List<Room> extracts(List<Document> docs) {
    Set<Room> roomSet = new HashSet<>();
    for (Document doc : docs) {
      if (logger.isDebugEnabled()) {
        logger.debug("extracting document : {}", doc.location());
      }
      
      extract(roomSet, doc);
    }
    return Lists.newArrayList(roomSet);
  }
  
  private void extract(Set<Room> roomSet, Document doc) {
    for(Element row : doc.select("body > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > table:nth-child(6) > tbody > tr")) {
      Elements tds = row.select("td");
      if(tds.get(0).select("input").isEmpty()) {
        continue;
      }
      String roomNm = StringUtils.substringBeforeLast(tds.get(1).text(), "(").replaceAll("\\s", "");
      String space = TextUtils.stringInLastBrackets(tds.get(1).text());
      String numberOfPeople = tds.get(2).text();
      long price = TextUtils.parseLong(tds.get(5).text()); 
      long peakPrice = TextUtils.parseLong(tds.get(6).text());
      
      Room room = new Room();
      room.setResortId(SpiderContext.getResortId());
      room.setRoomNm(roomNm);
      room.setRoomType(getRoomType(roomNm));
      room.setSpace(space);
      room.setNumberOfPeople(numberOfPeople);
      room.setPeakPrice(peakPrice);
      room.setPrice(price);
      if(!roomSet.contains(room)) {
        roomSet.add(room);
      }
    }
  }
  
  @Override
  public RoomType getRoomType(String roomNm) {
    return StringUtils.contains(roomNm, "휴양관")? RoomType.CONDO : RoomType.HUT;
  }

  @Override
  public List<Room> extract(Document doc) {
    // 사용안함
    return null;
  }

}
