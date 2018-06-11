package com.honsul.inthewood.spider.collector.r002;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R002BookingParserTest  extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R002", new R002BookingParser(), new R002RoomParser());
  }
  
}
