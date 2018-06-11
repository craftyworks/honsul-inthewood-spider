package com.honsul.inthewood.bot.telegram.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.LongPollingBot;

@Profile({"prod", "bot"})
@Configuration
public class PollingBotStarterConfiguration implements CommandLineRunner {
  static {
    ApiContextInitializer.init();
  }
  
  private final List<LongPollingBot> longPollingBots;

  @Autowired
  private TelegramBotsApi telegramBotsApi;

  public PollingBotStarterConfiguration(List<LongPollingBot> longPollingBots) {
    this.longPollingBots = longPollingBots;
  }

  @Override
  public void run(String... args) {
    try {
      for (LongPollingBot bot : longPollingBots) {
        telegramBotsApi.registerBot(bot);
      }
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Bean
  @ConditionalOnMissingBean(TelegramBotsApi.class)
  public TelegramBotsApi telegramBotsApi() {
    return new TelegramBotsApi();
  }
}
