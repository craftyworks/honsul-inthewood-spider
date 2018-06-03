package com.honsul.inthewood.postman;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.model.Resort;
import com.honsul.inthewood.core.model.Subscriber;
import com.honsul.inthewood.postman.slack.SlackBot;
import com.honsul.inthewood.spider.dao.PublisherDao;

@Service
public class PostmanService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  PublisherDao dao;

  @Autowired
  SlackBot slackBot;
  
  /**
   * 휴양림 예약변경현황 알림 발송
   */
  @Async
  public void publishBookingChanges(Resort resort) {
    for(Booking booking : dao.selectNewEntryBookings(resort)) {
      List<Subscriber> subscribers = dao.selectBookingSubscriber(booking);
      publishNotification(subscribers, booking);
    }
  }

  public void publishNotification(List<Subscriber> subscribers, Booking booking) {
    logger.info("publishe booking notifications");
    for(Subscriber target : subscribers) {
      logger.info("publishe {} to {}", booking, target);
      String message = booking.getResortNm() 
          + " " + booking.getBookingDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
          + " " + booking.getRoomNm();
      slackBot.sendMessage(target.getSubscriberId(), message);
    }
  }
  
  
}
