package com.honsul.inthewood.spider.collector.r005;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R005BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R005", new R005BookingParser());
  }
  
}
