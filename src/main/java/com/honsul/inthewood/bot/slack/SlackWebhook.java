package com.honsul.inthewood.bot.slack;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.honsul.inthewood.bot.slack.dao.SlackDao;
import com.honsul.inthewood.bot.slack.message.BookingNotificationMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessageResponse;
import com.honsul.inthewood.bot.slack.model.domain.SlackUser;

@Component
public class SlackWebhook {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  private SlackDao dao;
  
  @Autowired
  private SlackWebClient slackClient;
  
  public void setSlackClient(SlackWebClient slackClient) {
    this.slackClient = slackClient;
  }
  
  /**
   * 예약현황 알림메시지 발송
   */
  public SlackMessageResponse sendBookingNotificationMessage(SlackUser slackUser, Map<String, String> booking) {
    SlackMessage message = BookingNotificationMessage.build(booking);
    message.setToken(slackUser.getBotAccessToken());
    message.setChannel(slackUser.getBotImChannel());
    
    return slackClient.chatPostMessage(message);
  }

  /**
   * 발송된 예약현황 알림메시지 삭제
   */
  public SlackMessageResponse deleteBookingNotificationMessage(SlackMessage messageLog) {
    return slackClient.chatDelete(messageLog);
  }
}
