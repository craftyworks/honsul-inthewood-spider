package com.honsul.inthewood.spider.collector.r010;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.util.SSLTrustUtils;

public class R010BookingParserTest {

  static R010BookingParser parser;
  
  @BeforeClass
  public static void setup() {
    parser = new R010BookingParser();
    SSLTrustUtils.trustAllCertificate();
  }
  
  @Test
  public void testResortId() {
    BookingParser annotation = parser.getClass().getAnnotation(BookingParser.class);
    assertEquals("R010", annotation.resortId());
  }
  
  @Test
  public void testParse() {
    SpiderContext.setResortId("R010");
    assertTrue(!CollectionUtils.isEmpty(parser.parse()));
  }

}
