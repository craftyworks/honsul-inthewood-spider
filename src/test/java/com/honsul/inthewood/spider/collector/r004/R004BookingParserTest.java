package com.honsul.inthewood.spider.collector.r004;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;

public class R004BookingParserTest {

  static R004BookingParser parser;
  
  @BeforeClass
  public static void setup() {
    parser = new R004BookingParser();
  }
  
  @Test
  public void testResortId() {
    BookingParser annotation = R004BookingParser.class.getAnnotation(BookingParser.class);
    assertEquals("R004", annotation.resortId());
  }

  @Test
  public void testParse() {
      SpiderContext.setResortId("R004");
      assertTrue(!CollectionUtils.isEmpty(parser.parse()));
  }
}
