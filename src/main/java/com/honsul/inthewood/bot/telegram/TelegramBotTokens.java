package com.honsul.inthewood.bot.telegram;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "telegram")
public class TelegramBotTokens {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private Map<String, String> botTokens;
  
  public void setBotTokens(Map<String, String> botTokens) {
    this.botTokens = botTokens;
  }

  public String getBotToken(String botName) {
    return botTokens.get(botName);
  }
}
