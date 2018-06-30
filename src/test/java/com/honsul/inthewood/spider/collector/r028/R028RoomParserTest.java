package com.honsul.inthewood.spider.collector.r028;

import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R028RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
	  SSLTrustUtils.trustAllCertificate();
	  RoomParserTest.setup("R028", new R028RoomParser());
  }

}