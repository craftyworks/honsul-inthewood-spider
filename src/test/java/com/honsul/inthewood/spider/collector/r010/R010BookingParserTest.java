package com.honsul.inthewood.spider.collector.r010;

import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R010BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    SSLTrustUtils.trustAllCertificate();
    BookingParserTest.setup("R010", new R010BookingParser(), new R010RoomParser());
  }
  
}
