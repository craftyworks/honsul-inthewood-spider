package com.honsul.inthewood.spider.collector.r003;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;

public class R003BookingParserTest {
  static R003BookingParser parser;
  
  @BeforeClass
  public static void setup() {
    System.setProperty("phantomjs.binary.path", "E:/ProjectHome/tools/webdriver/phantomjs.exe");
    parser = new R003BookingParser();
  }
  
  @Test
  public void testResortId() {
    BookingParser annotation = parser.getClass().getAnnotation(BookingParser.class);
    assertEquals("R003", annotation.resortId());
  }

  @Test
  public void testParse() {
      SpiderContext.setResortId("R003");
      assertTrue(!CollectionUtils.isEmpty(parser.parse()));
  }

}
