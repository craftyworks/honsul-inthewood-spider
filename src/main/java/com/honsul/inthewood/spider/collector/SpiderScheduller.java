package com.honsul.inthewood.spider.collector;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.honsul.inthewood.core.model.Resort;
import com.honsul.inthewood.core.util.CommandUtils;

@Component
@Profile("prod")
public class SpiderScheduller {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  SpiderCollector collector;
  
  @Autowired
  Environment env;
  
  /**
   * 10분 간격으로 전체 휴양림의 예약현황 집계
   */
  @Scheduled(cron = "0 */10 * * * *" )
  private void collectAllBooking() {
    for(Resort resort : collector.selectAllResort()) {
      try {
        collector.collectBooking(resort);
        
      } catch(Throwable e) {
        logger.error("error", e);
      }
    }
  }
  
  /**
   * 매 시 정각 전체 휴양림의 숙소정보 집계
   */
  @Scheduled(cron="0 0 * * * *")
  private void collectAllRoom() {
    for(Resort resort : collector.selectAllResort()) {
      try {
        collector.collectRoom(resort);
      } catch(Throwable e) {
        logger.error("error", e);
      }
    }
  }
  
  /**
   * 30분 간격으로 phantomjs process kill
   */
  @Scheduled(cron="0 */10 * * * *")
  private void killPhantomjsProcess() {
    if(!env.acceptsProfiles("prod")) {
      return;
    }
    logger.info("killing phantomjs ghost process");
    try {
      CommandUtils.executeCommand("killall --older-than 1h phantomjs");
    } catch (IOException e) {
      logger.error("Kill Phantom Failed", e);
    }
  }  
}
