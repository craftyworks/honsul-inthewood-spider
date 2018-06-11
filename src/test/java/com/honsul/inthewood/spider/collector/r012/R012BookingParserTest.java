package com.honsul.inthewood.spider.collector.r012;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R012BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R012", new R012BookingParser(), new R012RoomParser());
  }
  
}
