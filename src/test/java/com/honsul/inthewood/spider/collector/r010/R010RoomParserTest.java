package com.honsul.inthewood.spider.collector.r010;

import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R010RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
    SSLTrustUtils.trustAllCertificate();
    RoomParserTest.setup("R010", new R010RoomParser());
  }

}
