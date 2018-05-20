package com.honsul.inthewood.spider.collector.r002;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;

public class R002BookingParserTest {
static R002BookingParser parser;
  
  @BeforeClass
  public static void setup() {
    parser = new R002BookingParser();
  }
  
  @Test
  public void testResortId() {
    BookingParser annotation = parser.getClass().getAnnotation(BookingParser.class);
    assertEquals("R002", annotation.resortId());
  }

  @Test
  public void testParse() {
      SpiderContext.setResortId("R002");
      assertTrue(!CollectionUtils.isEmpty(parser.parse()));
  }
}
