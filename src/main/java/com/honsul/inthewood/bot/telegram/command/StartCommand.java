package com.honsul.inthewood.bot.telegram.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class StartCommand extends BotCommand {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  public StartCommand() {
    super("start", "With this command you can start the Bot");
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

    String userName = user.getFirstName() + " " + user.getLastName();

    StringBuilder messageBuilder = new StringBuilder();
    messageBuilder.append("Hi ").append(userName).append("\n");
    messageBuilder.append("i think we know each other already!");
    messageBuilder.append("Welcome ").append(userName).append("\n");
    messageBuilder.append("this bot will demonstrate you the command feature of the Java TelegramBots API!");

    try {
      absSender.execute(new SendMessage(chat.getId(), messageBuilder.toString()));
    } catch (TelegramApiException e) {
      logger.error("error", e);
    }
  }
}