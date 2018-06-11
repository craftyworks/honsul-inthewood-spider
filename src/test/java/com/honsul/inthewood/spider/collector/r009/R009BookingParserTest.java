package com.honsul.inthewood.spider.collector.r009;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R009BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R009", new R009BookingParser(), new R009RoomParser());
  }
  
}
