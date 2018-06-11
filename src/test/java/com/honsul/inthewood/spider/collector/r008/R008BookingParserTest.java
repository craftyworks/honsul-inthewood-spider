package com.honsul.inthewood.spider.collector.r008;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R008BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R008", new R008BookingParser(), new R008RoomParser());
  }
  
}
