package com.honsul.inthewood.postman.slack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.palantir.roboslack.api.MessageRequest;
import com.palantir.roboslack.webhook.SlackWebHookService;
import com.palantir.roboslack.webhook.api.model.WebHookToken;
import com.palantir.roboslack.webhook.api.model.response.ResponseCode;

@Component
public class SlackBot {
  
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  SlackChannels slackChannels;

  public void sendMessage(String channel, MessageRequest messageRequest) {
    WebHookToken token = slackChannels.getWebhookToken(channel);
    
    ResponseCode response = SlackWebHookService.with(token).sendMessage(messageRequest);
    
    logger.debug("message response : {}", response);
  }
    
}
