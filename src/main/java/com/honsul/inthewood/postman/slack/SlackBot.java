package com.honsul.inthewood.postman.slack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.palantir.roboslack.api.MessageRequest;
import com.palantir.roboslack.webhook.SlackWebHookService;
import com.palantir.roboslack.webhook.api.model.WebHookToken;
import com.palantir.roboslack.webhook.api.model.response.ResponseCode;

@Component
public class SlackBot {
  
  @Autowired
  SlackChannels slackChannels;
  
  public void sendMessage(String channel, String textMessage) {
    WebHookToken token = slackChannels.getWebhookToken(channel);

    MessageRequest messageRequest = MessageRequest.builder()
        .username("혼술")
        .iconEmoji(":smile:")
        .text(textMessage)
        .build();
    
    ResponseCode response = SlackWebHookService.with(token)
        .sendMessage(messageRequest);
  }
}
