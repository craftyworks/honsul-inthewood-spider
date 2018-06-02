package com.honsul.inthewood.spider.collector.r013;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R013BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R013", new R013BookingParser());
  }
  
}
