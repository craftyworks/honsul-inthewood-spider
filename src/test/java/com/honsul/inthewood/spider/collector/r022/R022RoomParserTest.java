package com.honsul.inthewood.spider.collector.r022;

import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R022RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
	  SSLTrustUtils.trustAllCertificate();
	  RoomParserTest.setup("R022", new R022RoomParser());
  }

}
