package com.honsul.inthewood.spider.collector.r019;


import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R019RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
	SSLTrustUtils.trustAllCertificate();
    RoomParserTest.setup("R019", new R019RoomParser());
  }

}
