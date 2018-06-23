package com.honsul.inthewood.spider.collector.r018;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R018BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R018", new R018BookingParser(), new R018RoomParser());
  }

}
