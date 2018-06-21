package com.honsul.inthewood.spider.collector.r021;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R021RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
    RoomParserTest.setup("R021", new R021RoomParser());
  }

}
