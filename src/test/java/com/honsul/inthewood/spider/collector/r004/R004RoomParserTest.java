package com.honsul.inthewood.spider.collector.r004;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;

public class R004RoomParserTest {

  static R004RoomParser parser;
  
  @BeforeClass
  public static void setup() {
    parser = new R004RoomParser();
  }
  
  @Test
  public void testResortId() {
    RoomParser annotation = R004RoomParser.class.getAnnotation(RoomParser.class);
    assertEquals("R004", annotation.resortId());
  }

  @Test
  public void testParse() {
      SpiderContext.setResortId("R004");
      List<Room> roomList = parser.parse();
      assertTrue(!CollectionUtils.isEmpty(roomList));
      assertEquals("R004", roomList.get(0).getResortId());
  }

}
