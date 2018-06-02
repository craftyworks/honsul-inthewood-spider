package com.honsul.inthewood.spider.collector.r013;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.util.SSLTrustUtils;

public class R013BookingParserTest {


  static R013BookingParser parser;
  
  @BeforeClass
  public static void setup() {
    parser = new R013BookingParser();
    SSLTrustUtils.trustAllCertificate();
  }
  
  @Test
  public void testResortId() {
    BookingParser annotation = parser.getClass().getAnnotation(BookingParser.class);
    assertEquals("R013", annotation.resortId());
  }
  
  @Test
  public void testParse() {
    SpiderContext.setResortId("R013");
    assertTrue(!CollectionUtils.isEmpty(parser.parse()));
  }
}
