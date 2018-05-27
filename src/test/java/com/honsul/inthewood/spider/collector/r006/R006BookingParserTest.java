package com.honsul.inthewood.spider.collector.r006;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;

public class R006BookingParserTest {

static R006BookingParser parser;
  
  @BeforeClass
  public static void setup() {
    System.setProperty("phantomjs.binary.path", "E:/ProjectHome/tools/webdriver/phantomjs.exe");
    parser = new R006BookingParser();
  }
  
  @Test
  public void testResortId() {
    BookingParser annotation = parser.getClass().getAnnotation(BookingParser.class);
    assertEquals("R006", annotation.resortId());
  }
  
  @Test
  public void testParse() {
    SpiderContext.setResortId("R006");
    assertTrue(!CollectionUtils.isEmpty(parser.parse()));
  }

}
