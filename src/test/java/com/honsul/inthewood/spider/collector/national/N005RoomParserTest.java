package com.honsul.inthewood.spider.collector.national;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;

public class N005RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
    RoomParserTest.setup("N004", new N004RoomParser());
  }

}

