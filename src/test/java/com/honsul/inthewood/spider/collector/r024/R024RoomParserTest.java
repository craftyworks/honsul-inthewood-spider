package com.honsul.inthewood.spider.collector.r024;

import org.junit.*;

import com.honsul.inthewood.spider.collector.*;

public class R024RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
	  RoomParserTest.setup("R024", new R024RoomParser());
  }
}
