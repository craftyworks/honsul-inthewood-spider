package com.honsul.inthewood.spider.collector.r024;

import org.junit.*;

import com.honsul.inthewood.spider.collector.*;

public class R024BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
	  BookingParserTest.setup("R024", new R024BookingParser(), new R024RoomParser());
  }

}
