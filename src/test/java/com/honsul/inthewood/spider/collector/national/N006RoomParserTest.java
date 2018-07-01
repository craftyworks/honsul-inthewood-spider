package com.honsul.inthewood.spider.collector.national;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;

public class N006RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
    RoomParserTest.setup("N006", new N006RoomParser());
  }

}

