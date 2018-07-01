package com.honsul.inthewood.spider.collector.national;

import java.io.IOException;

import org.jsoup.nodes.Document;

import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.util.NationalResortUtils;

/**
 * 방태산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="N012")
public class N012RoomParser extends AbstractNationalRoomParser {
  @Override
  protected Document document() throws IOException {
    return NationalResortUtils.bookinDocument("0109");
  }

}
