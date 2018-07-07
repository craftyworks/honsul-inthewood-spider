package com.honsul.inthewood.spider.collector;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;

public abstract class RoomParserTest {
  private final static Logger logger = LoggerFactory.getLogger(RoomParserTest.class);
  
  protected static String RESORT_ID;
  
  protected static Parser<Room> PARSER;
  
  protected static void setup(String resortId, Parser<Room> parser) {
    RoomParserTest.RESORT_ID = resortId;
    RoomParserTest.PARSER = parser;

    SpiderContext.setResortId(resortId);
  }
  
  @Test
  public void testResortId() {
    RoomParser annotation = PARSER.getClass().getAnnotation(RoomParser.class);
    assertEquals(RoomParserTest.RESORT_ID, annotation.resortId());
    
    assertThat(PARSER.accept(RESORT_ID), is(true));
    
    assertThat(PARSER.accept(new StringBuilder(RESORT_ID).reverse().toString()), is(false));    
  }

  @Test
  public void testParse() {
    testParse(PARSER);
  }
  
  public static void testParse(Parser<Room> roomParser) {
    List<Room> roomList = roomParser.parse();
    assertTrue(!CollectionUtils.isEmpty(roomList));
    
    Room room = roomList.get(0);
    assertThat(SpiderContext.getResortId(), anyOf(is("National"), is(room.getResortId())));
    
    for(Room r : roomList) {
      // 객실 이용료 체크
      if(r.getPeakPrice() == 0 || r.getPrice() == 0 || r.getPrice() > r.getPeakPrice()) {
        logger.debug("요금 오류 : {}", r);
      }
      assertThat("성수기 요금이 비수기보다 싸면 안됨", r.getPeakPrice(),  greaterThanOrEqualTo(r.getPrice()));
      assertThat("성수기 숙박요금은 0원보다 커야 함", r.getPeakPrice(), greaterThan(0L));
      assertThat("숙박요금은 0원보다 커야 함", r.getPrice(), greaterThan(0L));
      
      // 객실명 공백 체크
      assertThat("객실명에 공백이 포함되어 있음. [" + r.getRoomNm() + "]", r.getRoomNm().length(), is(r.getRoomNm().trim().length()));
      
      // 인워수 체크
      if(r.getNumberOfPeople().length() != r.getNumberOfPeople().replaceAll("[^0-9~\\-]*", "").length()) {
        logger.debug("인원수 오류 : {}", r);
      }
      assertThat("인원수에 숫자외에 문자가 존재함", r.getNumberOfPeople().length(), is(r.getNumberOfPeople().replaceAll("[^0-9~\\-]*", "").length()));
      
      //중복 체크
      if(Collections.frequency(roomList, r) > 1) {
        roomList.stream().forEach(x -> logger.debug("room name : {}, count : {}", x.getRoomNm(), Collections.frequency(roomList, x)));
      }
      assertThat("중복된 객실명 발견", Collections.frequency(roomList, r), is(1));
    }
  }
}
