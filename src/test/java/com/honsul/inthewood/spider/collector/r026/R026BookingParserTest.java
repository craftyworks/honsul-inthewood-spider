package com.honsul.inthewood.spider.collector.r026;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R026BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
	  BookingParserTest.setup("R026", new R026BookingParser(), new R026RoomParser());
  }

}