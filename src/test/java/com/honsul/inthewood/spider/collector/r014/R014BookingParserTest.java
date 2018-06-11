package com.honsul.inthewood.spider.collector.r014;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R014BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R014", new R014BookingParser(), new R014RoomParser());
  }
  
}
