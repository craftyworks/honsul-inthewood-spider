package com.honsul.inthewood.spider.collector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;

public abstract class BookingParserTest {

  protected static String RESORT_ID;
  
  protected static Parser<Booking> PARSER;
  
  protected static void setup(String resortId, Parser<Booking> parser) {
    BookingParserTest.RESORT_ID = resortId;
    BookingParserTest.PARSER = parser;

    SpiderContext.setResortId(resortId);
  }
  
  @Test
  public void testResortId() {
    BookingParser annotation = PARSER.getClass().getAnnotation(BookingParser.class);
    assertEquals(BookingParserTest.RESORT_ID, annotation.resortId());
  }

  @Test
  public void testParse() {
      List<Booking> bookingList = PARSER.parse();
      assertTrue(!CollectionUtils.isEmpty(bookingList));
      assertEquals(BookingParserTest.RESORT_ID, bookingList.get(0).getResortId());
  }
}
