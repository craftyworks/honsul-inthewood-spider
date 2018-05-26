package com.honsul.inthewood.spider.collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class SpiderScheduller {
  
  @Autowired
  SpiderCollector collector;
  
  /**
   * 10분 간격으로 전체 휴양림의 예약현황 집계
   */
  @Scheduled(fixedDelay=6000000)
  private void collectAllBooking() {
    collector.collectAllBooking();
  }
  
}
