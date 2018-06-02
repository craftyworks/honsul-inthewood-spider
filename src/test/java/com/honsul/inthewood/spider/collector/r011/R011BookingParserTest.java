package com.honsul.inthewood.spider.collector.r011;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R011BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R011", new R011BookingParser());
  }
  
}
