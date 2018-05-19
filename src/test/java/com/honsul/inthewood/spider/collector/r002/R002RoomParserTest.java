package com.honsul.inthewood.spider.collector.r002;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Room;

public class R002RoomParserTest {

  static R002RoomParser parser;
  
  @BeforeClass
  public static void setup() {
    parser = new R002RoomParser();
  }
  
  @Test
  public void testResortId() {
    RoomParser annotation = parser.getClass().getAnnotation(RoomParser.class);
    assertEquals("R002", annotation.resortId());
  }
  
  @Test
  public void testParse() {
    SpiderContext.setResortId("R002");
    List<Room> roomList = parser.parse();
    assertTrue(!CollectionUtils.isEmpty(roomList));
    assertEquals("R002", roomList.get(0).getResortId());
  }

}
