package com.honsul.inthewood.spider.collector.r007;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;

public class R007BookingParserTest {

  static R007BookingParser parser;
  
  @BeforeClass
  public static void setup() {
    parser = new R007BookingParser();
  }
  
  @Test
  public void testResortId() {
    BookingParser annotation = parser.getClass().getAnnotation(BookingParser.class);
    assertEquals("R007", annotation.resortId());
  }
  
  @Test
  public void testParse() {
    SpiderContext.setResortId("R007");
    assertTrue(!CollectionUtils.isEmpty(parser.parse()));
  }

}
