package com.honsul.inthewood.spider.collector.r027;

import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R027BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
	  SSLTrustUtils.trustAllCertificate();
	  BookingParserTest.setup("R027", new R027BookingParser(), new R027RoomParser());
  }

}