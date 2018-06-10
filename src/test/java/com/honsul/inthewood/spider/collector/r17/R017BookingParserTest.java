package com.honsul.inthewood.spider.collector.r17;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R017BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R017", new R017BookingParser());
  }

}
