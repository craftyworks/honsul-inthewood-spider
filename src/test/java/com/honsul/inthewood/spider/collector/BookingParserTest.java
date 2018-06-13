package com.honsul.inthewood.spider.collector;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.parser.JsoupBookingParser;

public abstract class BookingParserTest {

  private final static Logger logger = LoggerFactory.getLogger(BookingParserTest.class);
  
  protected static String RESORT_ID;
  
  protected static Parser<Booking> PARSER;
  
  protected static Parser<Room> ROOM_PARSER;
  
  protected static void setup(String resortId, Parser<Booking> parser) {
    BookingParserTest.RESORT_ID = resortId;
    BookingParserTest.PARSER = parser;

    SpiderContext.setResortId(resortId);
  }
  
  protected static void setup(String resortId, Parser<Booking> parser, Parser<Room> roomParser) {
    BookingParserTest.RESORT_ID = resortId;
    BookingParserTest.PARSER = parser;
    BookingParserTest.ROOM_PARSER = roomParser;

    SpiderContext.setResortId(resortId);
  }
  
  @Test
  public void testResortId() {
    BookingParser annotation = PARSER.getClass().getAnnotation(BookingParser.class);
    assertEquals(BookingParserTest.RESORT_ID, annotation.resortId());
  }

  @Test
  public void testDocuments() {
    if(PARSER instanceof JsoupBookingParser) {
      List<Document> docs = ReflectionTestUtils.invokeMethod(PARSER, "documents", new Class[] {});
      assertTrue(!CollectionUtils.isEmpty(docs));
    }
  }
  
  @Test
  public void testParse() {
      List<Booking> bookingList = PARSER.parse();
      assertTrue(!CollectionUtils.isEmpty(bookingList));
      assertEquals(BookingParserTest.RESORT_ID, bookingList.get(0).getResortId());

      for(Booking b : bookingList) {
        //중복 체크
        if(Collections.frequency(bookingList, b) > 1) {
          bookingList.stream().forEach(x -> {
            if(Collections.frequency(bookingList, x) > 1) {
              logger.debug("booking : {}, count : {}", x, Collections.frequency(bookingList, x));
            }
          });
        }
        assertThat("중복된 예약현황 발견", Collections.frequency(bookingList, b), is(1));
      }
      
      // 예약현황의 숙소정보가 수집되는지 확인
      if(ROOM_PARSER != null) {
        List<Room> roomList = ROOM_PARSER.parse();
        final Set<String> roomNameSet = roomList.stream().map(r -> r.getRoomNm()).collect(Collectors.toSet());
        
        List<Booking> orphans = bookingList.stream().filter(x -> !roomNameSet.contains(x.getRoomNm())).collect(Collectors.toList());
        
        if(logger.isDebugEnabled() && !CollectionUtils.isEmpty(orphans)) {
          List<String> orphanNames = orphans.stream().map(b -> b.getRoomNm()).distinct().collect(Collectors.toList());
          logger.debug("unknown room names : {}", orphanNames);
          logger.debug("room list : {}", roomNameSet);
        }
        assertThat("알려지지 않은 숙소정보 발견", CollectionUtils.isEmpty(orphans), is(true));
      }
  }
}
