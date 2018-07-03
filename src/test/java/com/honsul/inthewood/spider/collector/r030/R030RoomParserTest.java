package com.honsul.inthewood.spider.collector.r030;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R030RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
	  RoomParserTest.setup("R030", new R030RoomParser());
  }

}