package com.honsul.inthewood.spider.collector.r023;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R023RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
	  RoomParserTest.setup("R023", new R023RoomParser());
  }

}
