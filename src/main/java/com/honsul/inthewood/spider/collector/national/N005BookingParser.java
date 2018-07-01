package com.honsul.inthewood.spider.collector.national;

import java.io.IOException;

import org.jsoup.nodes.Document;

import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.util.NationalResortUtils;

/**
 * 국립자연휴양림
 * 남해편백자연휴양림 예약현황 파서.
 */
@BookingParser(resortId="N005")
public class N005BookingParser extends AbstractNationalBookingParser {

  @Override
  protected Document document() throws IOException {
    return NationalResortUtils.bookinDocument("0192");
  }

}
