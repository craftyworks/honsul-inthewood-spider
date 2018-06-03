package com.honsul.inthewood.spider.collector.r014;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R014RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
    RoomParserTest.setup("R014", new R014RoomParser());
  }

}
