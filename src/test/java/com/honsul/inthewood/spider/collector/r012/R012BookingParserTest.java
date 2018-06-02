package com.honsul.inthewood.spider.collector.r012;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.util.SSLTrustUtils;

public class R012BookingParserTest {

  static R012BookingParser parser;
  
  @BeforeClass
  public static void setup() {
    parser = new R012BookingParser();
    SSLTrustUtils.trustAllCertificate();
  }
  
  @Test
  public void testResortId() {
    BookingParser annotation = parser.getClass().getAnnotation(BookingParser.class);
    assertEquals("R012", annotation.resortId());
  }
  
  @Test
  public void testParse() {
    SpiderContext.setResortId("R012");
    assertTrue(!CollectionUtils.isEmpty(parser.parse()));
  }

}
