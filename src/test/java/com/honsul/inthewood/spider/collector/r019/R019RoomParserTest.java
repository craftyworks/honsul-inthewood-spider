package com.honsul.inthewood.spider.collector.r019;


import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R019RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
    RoomParserTest.setup("R019", new R019RoomParser());
  }

}
