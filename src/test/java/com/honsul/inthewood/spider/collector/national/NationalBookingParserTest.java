package com.honsul.inthewood.spider.collector.national;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.spider.collector.BookingParserTest;

public class NationalBookingParserTest {
  
  private static final Logger logger = LoggerFactory.getLogger(NationalBookingParserTest.class);

  private static NationalBookingParser bookingParser;
  
  private static NationalRoomParser roomParser;
  
  @BeforeClass
  public static void setup() {
   bookingParser = new NationalBookingParser();
   roomParser = new NationalRoomParser();
  }
  
  @Test
  public void testAccept() throws Exception {
    assertThat(bookingParser.accept("휴양림"), is(false));
    
    NationalResortInfo.MAPPINGS.forEach((resortId, deptCode) -> {
      assertThat(bookingParser.accept(resortId), is(true));
    });
  }

  @Test
  public void testParse() throws Exception {
    NationalResortInfo.MAPPINGS.forEach((resortId, deptCode) -> {
      logger.info("Testing resort : {}", resortId);
      SpiderContext.setResortId(resortId);
      BookingParserTest.testParse(bookingParser, roomParser);
    });
  }

}
