package com.honsul.inthewood.spider.collector.r026;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R026RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
	  RoomParserTest.setup("R026", new R026RoomParser());
  }

}