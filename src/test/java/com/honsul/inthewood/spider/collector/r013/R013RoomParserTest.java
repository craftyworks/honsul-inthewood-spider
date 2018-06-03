package com.honsul.inthewood.spider.collector.r013;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R013RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
    RoomParserTest.setup("R013", new R013RoomParser());
  }

}
