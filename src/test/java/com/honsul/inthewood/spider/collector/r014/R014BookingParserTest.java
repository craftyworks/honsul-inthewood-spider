package com.honsul.inthewood.spider.collector.r014;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.util.SSLTrustUtils;

public class R014BookingParserTest {

  static R014BookingParser parser;
  
  @BeforeClass
  public static void setup() {
    parser = new R014BookingParser();
    SSLTrustUtils.trustAllCertificate();
  }
  
  @Test
  public void testResortId() {
    BookingParser annotation = parser.getClass().getAnnotation(BookingParser.class);
    assertEquals("R014", annotation.resortId());
  }
  
  @Test
  public void testParse() {
    SpiderContext.setResortId("R014");
    assertTrue(!CollectionUtils.isEmpty(parser.parse()));
  }
}
