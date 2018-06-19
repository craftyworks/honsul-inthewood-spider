package com.honsul.inthewood.spider.collector.r019;

import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R019BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
	SSLTrustUtils.trustAllCertificate();
    BookingParserTest.setup("R019", new R019BookingParser(), new R019RoomParser());
  }

}
