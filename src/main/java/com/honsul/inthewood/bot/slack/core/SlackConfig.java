package com.honsul.inthewood.bot.slack.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.palantir.roboslack.webhook.api.model.WebHookToken;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "slack")
public class SlackConfig { 
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private Bot bot;
  
  private Map<String, String> channels;
  
  private Map<String, WebHookToken> tokens = new HashMap<>();
  
  public void setChannels(Map<String, String> channels) {
    this.channels = channels;
    
    for(Entry<String, String> entry : channels.entrySet()) {
      tokens.put(entry.getKey(), WebHookToken.fromString(entry.getValue()));
    }    
  }

  public void setBot(Bot bot) {
    this.bot = bot;
  }

  public Bot getBot() {
    return this.bot;
  }
  
  public String getWebhookURL(String channel) {
    return channels.get(channel);
  }
  
  public WebHookToken getWebhookToken(String channel) {
    return tokens.get(channel);
  }
  
  @Data
  public static class Bot {
    private String clientId;
    
    private String clientSecret;
    
    private String verifyToken;
  }


}
