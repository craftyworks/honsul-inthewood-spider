package com.honsul.inthewood.spider.collector.national;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;

public class N001RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
    RoomParserTest.setup("N001", new N001RoomParser());
  }

}

