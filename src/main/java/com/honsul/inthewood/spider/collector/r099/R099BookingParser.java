package com.honsul.inthewood.spider.collector.r099;

import java.util.List;

import org.jsoup.nodes.Document;

import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.parser.JsoupBookingParser;

/**
 * 자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="R099")
public class R099BookingParser extends JsoupBookingParser {

  @Override
  public List<Booking> extract(Document doc) {
    return null;
  }



}
