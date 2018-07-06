package com.honsul.inthewood.spider.collector.r030;

import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R030BookingParserTest extends BookingParserTest {

  @BeforeClass
  public static void setup() {
	  SSLTrustUtils.trustAllCertificate();
	  BookingParserTest.setup("R030", new R030BookingParser(), new R030RoomParser());
  }

}