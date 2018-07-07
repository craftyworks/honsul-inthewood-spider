package com.honsul.inthewood.spider;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.bot.slack.SlackWebhook;
import com.honsul.inthewood.bot.slack.model.domain.SlackUser;
import com.honsul.inthewood.core.model.Resort;
import com.honsul.inthewood.spider.dao.PublisherDao;

@Service
public class PostmanService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  PublisherDao dao;

  @Autowired
  SlackWebhook slackWebhook;
  
  /**
   * 휴양림 예약변경현황 알림 발송
   */
  @Async
  public void publishBookingChanges(Resort resort) {
    for(Map<String, String> booking : dao.selectNewEntryBookings(resort)) {
      
      List<SlackUser> slackSubscribers = dao.selectBookingSlackSubscriber(booking);
      if(!CollectionUtils.isEmpty(slackSubscribers)) {
        publishNotification(slackSubscribers, booking);
      }
    }
  }
  
  public void publishBookingChangesSync(Resort resort) {
    publishBookingChanges(resort);
  }

  public void publishNotification(List<SlackUser> subscribers, Map<String, String> booking) {
    logger.info("booking notifications");
    for(SlackUser target : subscribers) {
      logger.info("sending {} : {}", target, booking);
      
      slackWebhook.sendBookingNotificationMessage(target, booking);
    }
  }
  
}
