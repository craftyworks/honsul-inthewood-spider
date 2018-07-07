package com.honsul.inthewood.spider.collector.national;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.spider.collector.RoomParserTest;

public class NationalRoomParserTest {
  private static final Logger logger = LoggerFactory.getLogger(NationalRoomParserTest.class);

  private static NationalRoomParser roomParser;
  
  @BeforeClass
  public static void setup() {
    roomParser = new NationalRoomParser();
  }

  @Test
  public void testAccept() throws Exception {
    assertThat(roomParser.accept("휴양림"), is(false));
    
    NationalResortInfo.MAPPINGS.forEach((resortId, deptCode) -> {
      assertThat(roomParser.accept(resortId), is(true));
    });
  }
  
  @Test
  public void testParse() throws Exception {
    NationalResortInfo.MAPPINGS.forEach((resortId, deptCode) -> {
      logger.info("Testing resort : {}", resortId);
      SpiderContext.setResortId(resortId);
      RoomParserTest.testParse(roomParser);
    });
  }

}
