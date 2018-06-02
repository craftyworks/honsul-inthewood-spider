package com.honsul.inthewood.spider.collector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;

public abstract class RoomParserTest {

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
      assertEquals(RoomParserTest.RESORT_ID, roomList.get(0).getResortId());
  }
}
