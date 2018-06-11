package com.honsul.inthewood.bot.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.honsul.inthewood.bot.telegram.command.HelpCommand;
import com.honsul.inthewood.bot.telegram.command.StartCommand;
import com.honsul.inthewood.bot.telegram.command.StopCommand;

@Component
public class HuegoBot extends TelegramLongPollingCommandBot {
  private final Logger logger = LoggerFactory.getLogger(HuegoBot.class);

  @Autowired
  private TelegramBotTokens botTokens;
  
  public HuegoBot() {
    super("HuegoBot");
    
    register(new HelpCommand());
    register(new StartCommand());
    register(new StopCommand());
  }
  
  @Override
  public void processNonCommandUpdate(Update update) {
    logger.debug("Update Received :{}", update);
    
    if(update.hasMessage()) {
      logger.debug("message : {}, {}, {}", update.getMessage().getChatId(), update.getMessage().getFrom().getUserName(), update.getMessage());
    }
    
    if (update.hasMessage() && update.getMessage().hasText()) {
      SendMessage message = new SendMessage()
              .setChatId(update.getMessage().getChatId())
              .setText(update.getMessage().getText());
      try {
          execute(message); // Call method to send the message
      } catch (TelegramApiException e) {
          e.printStackTrace();
      }
    }    
  }

  @Override
  public String getBotToken() {
    return botTokens.getBotToken(getBotUsername());
  }

}
