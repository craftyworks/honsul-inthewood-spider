package com.honsul.inthewood.spider.collector.r017;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R017RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
    RoomParserTest.setup("R017", new R017RoomParser());
  }

}
