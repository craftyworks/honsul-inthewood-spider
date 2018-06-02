package com.honsul.inthewood.spider.collector.r004;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.RoomParserTest;
import com.honsul.inthewood.spider.collector.r005.R005RoomParser;

public class R004RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
    RoomParserTest.setup("R005", new R005RoomParser());
  }

}
