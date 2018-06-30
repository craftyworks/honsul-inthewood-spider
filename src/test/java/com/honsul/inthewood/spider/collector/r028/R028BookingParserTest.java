package com.honsul.inthewood.spider.collector.r028;

import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R028BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
	  SSLTrustUtils.trustAllCertificate();
	  BookingParserTest.setup("R028", new R028BookingParser(), new R028RoomParser());
  }

}