package com.honsul.inthewood.spider.collector.r025;


import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R025RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
	  SSLTrustUtils.trustAllCertificate();
	  RoomParserTest.setup("R025", new R025RoomParser());
  }

}
