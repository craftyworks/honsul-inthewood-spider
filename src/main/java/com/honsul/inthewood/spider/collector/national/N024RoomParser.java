package com.honsul.inthewood.spider.collector.national;

import java.io.IOException;

import org.jsoup.nodes.Document;

import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.util.NationalResortUtils;

/**
 * 오서산자연휴양림 숙소현황 파서.
 */
@RoomParser(resortId="N024")
public class N024RoomParser extends AbstractNationalRoomParser {
  @Override
  protected Document document() throws IOException {
    return NationalResortUtils.bookinDocument("0191");
  }

}
