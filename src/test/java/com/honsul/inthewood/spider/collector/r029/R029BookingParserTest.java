package com.honsul.inthewood.spider.collector.r029;

import org.junit.BeforeClass;

import com.honsul.inthewood.spider.collector.BookingParserTest;

public class R029BookingParserTest extends BookingParserTest {

	  @BeforeClass
	  public static void setup() {
		  BookingParserTest.setup("R029", new R029BookingParser(), new R029RoomParser());
	  }

	}