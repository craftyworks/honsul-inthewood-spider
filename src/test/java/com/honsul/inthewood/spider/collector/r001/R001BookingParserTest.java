package com.honsul.inthewood.spider.collector.r001;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;

public class R001BookingParserTest {
  static R001BookingParser parser;
  
  @BeforeClass
  public static void setup() {
    System.setProperty("phantomjs.binary.path", "E:/ProjectHome/tools/webdriver/phantomjs.exe");
    parser = new R001BookingParser();
  }
  
  @Test
  public void testResortId() {
    BookingParser annotation = parser.getClass().getAnnotation(BookingParser.class);
    assertEquals("R001", annotation.resortId());
  }

  @Test
  public void testParse() {
      SpiderContext.setResortId("R001");
      assertTrue(!CollectionUtils.isEmpty(parser.parse()));
  }
}
