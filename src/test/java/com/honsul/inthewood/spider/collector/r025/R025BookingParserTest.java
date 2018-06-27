package com.honsul.inthewood.spider.collector.r025;


import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R025BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
	  SSLTrustUtils.trustAllCertificate();
	  BookingParserTest.setup("R025", new R025BookingParser(), new R025RoomParser());
  }

}