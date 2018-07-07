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
  
  public SlackMessageResponse sendBookingNotificationMessage(SlackUser slackUser, Map<String, String> booking) {
    SlackMessage message = BookingNotificationMessage.build(booking);
    message.setToken(slackUser.getBotAccessToken());
    message.setChannel(slackUser.getBotImChannel());
    
    SlackMessageResponse response = slackClient.chatPostMessage(message);
    if(response.isOk()) {
      booking.put("channel", response.getChannel());
      booking.put("ts", response.getTs());
      dao.insertBookingNotificationMessage(booking);
    }
    return response;
  }
}
