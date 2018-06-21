package com.honsul.inthewood.spider.collector.r020;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R020BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
	BookingParserTest.setup("R020", new R020BookingParser(), new R020RoomParser());
  }

}
