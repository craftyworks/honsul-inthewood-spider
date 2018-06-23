package com.honsul.inthewood.spider.collector.r022;

import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R022BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
	  SSLTrustUtils.trustAllCertificate();
	  BookingParserTest.setup("R022", new R022BookingParser(), new R022RoomParser());
  }

}
