package com.honsul.inthewood.spider.collector.r021;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R021BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
	BookingParserTest.setup("R021", new R021BookingParser(), new R021RoomParser());
  }

}
