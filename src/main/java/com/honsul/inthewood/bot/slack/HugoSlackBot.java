package com.honsul.inthewood.bot.slack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.WebSocketSession;

import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.models.Event;

//@JBot
public class HugoSlackBot extends Bot {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Value("${slackBotToken}")
  private String slackToken;

  @Override
  public String getSlackToken() {
      return slackToken;
  }

  @Override
  public Bot getSlackBot() {
      return this;
  }
  
  @Controller(events = {EventType.MESSAGE})
  public void onReceiveMESSAGE(WebSocketSession session, Event event) {
    logger.debug("receive message : {}, {}, {}, {}", event.getBotId(), event.getChannelId(), event.getText(), event.getTs());
    //reply(session, event, "Hi, I am a Slack Bot!");
  }
  @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
  public void onReceiveDM(WebSocketSession session, Event event) {
    logger.debug("receive DM : {}, {}, {}, {}", event.getBotId(), event.getChannelId(), event.getText(), event.getTs());
    reply(session, event, "Hi, I am a Slack Bot!");
  }
  
    
}
