package com.honsul.inthewood.spider.collector.r017;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;
import com.honsul.inthewood.spider.collector.r017.R017BookingParser;

public class R017BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    BookingParserTest.setup("R017", new R017BookingParser());
  }

}
