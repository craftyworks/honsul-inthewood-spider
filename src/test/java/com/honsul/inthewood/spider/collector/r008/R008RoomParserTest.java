package com.honsul.inthewood.spider.collector.r008;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R008RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
    RoomParserTest.setup("R008", new R008RoomParser());
  }
  
}
