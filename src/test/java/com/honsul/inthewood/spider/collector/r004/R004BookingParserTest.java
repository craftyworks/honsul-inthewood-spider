package com.honsul.inthewood.spider.collector.r004;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R004BookingParserTest  extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R004", new R004BookingParser(), new R004RoomParser());
  }
  
}
