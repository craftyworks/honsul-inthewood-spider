package com.honsul.inthewood.spider.collector.r029;

import org.junit.BeforeClass;

import com.honsul.inthewood.core.util.SSLTrustUtils;
import com.honsul.inthewood.spider.collector.RoomParserTest;
import com.honsul.inthewood.spider.collector.r029.R029RoomParser;

public class R029RoomParserTest extends RoomParserTest {

	  @BeforeClass
	  public static void setup() {
		  SSLTrustUtils.trustAllCertificate();
		  RoomParserTest.setup("R029", new R029RoomParser());
	  }

	}