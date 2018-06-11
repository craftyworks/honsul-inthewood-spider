package com.honsul.inthewood.spider.collector.r007;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R007BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R007", new R007BookingParser(), new R007RoomParser());
  }
  
}
