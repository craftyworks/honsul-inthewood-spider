package com.honsul.inthewood.spider.collector.r027;

import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.RoomParserTest;

public class R027RoomParserTest extends RoomParserTest {

  @BeforeClass
  public static void setup() {
	  SSLTrustUtils.trustAllCertificate();
	  RoomParserTest.setup("R027", new R027RoomParser());
  }

}