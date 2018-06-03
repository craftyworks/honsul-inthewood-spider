package com.honsul.inthewood.postman.slack;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.palantir.roboslack.webhook.api.model.WebHookToken;

//@Configuration
//@ConfigurationProperties(prefix = "slack")
@Component
public class SlackChannels implements InitializingBean { 
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private Map<String, String> channels = new HashMap<>();
  
  private Map<String, WebHookToken> tokens = new HashMap<>();

  @Override
  public void afterPropertiesSet() throws Exception {
    channels.put("honsul-holiday", "https://hooks.slack.com/services/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");
    
    for(Entry<String, String> entry : channels.entrySet()) {
      tokens.put(entry.getKey(), WebHookToken.fromString(entry.getValue()));
    }
  }
  
  public String getWebhookURL(String channel) {
    return channels.get(channel);
  }
  
  public WebHookToken getWebhookToken(String channel) {
    return tokens.get(channel);
  }
  
}
