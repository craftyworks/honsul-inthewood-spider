package com.honsul.inthewood.spider.collector.national;

import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.BookingParserTest;

public class N010BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
    SSLTrustUtils.trustAllCertificate();
    BookingParserTest.setup("N010", new N010BookingParser(), new N010RoomParser());
  }

}
