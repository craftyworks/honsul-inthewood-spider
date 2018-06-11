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
  }

  @Test
  public void testParse() {
      List<Room> roomList = PARSER.parse();
      assertTrue(!CollectionUtils.isEmpty(roomList));
      
      Room room = roomList.get(0);
      assertEquals(RoomParserTest.RESORT_ID, room.getResortId());
      assertThat(room.getPeakPrice(),  greaterThanOrEqualTo(room.getPrice()));
      
      //중복 체크
      for(Room r : roomList) {
        if(Collections.frequency(roomList, r) > 1) {
          roomList.stream().forEach(x -> logger.debug("room name : {}, count : {}", x.getRoomNm(), Collections.frequency(roomList, x)));
        }
        assertThat("중복 Room", Collections.frequency(roomList, r), is(1));
      }
  }
}
