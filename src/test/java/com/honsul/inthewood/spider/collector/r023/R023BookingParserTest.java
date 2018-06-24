package com.honsul.inthewood.spider.collector.r023;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R023BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
	  BookingParserTest.setup("R023", new R023BookingParser(), new R023RoomParser());
  }

}
