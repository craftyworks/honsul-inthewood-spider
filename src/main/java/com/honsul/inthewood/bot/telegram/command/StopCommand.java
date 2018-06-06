package com.honsul.inthewood.bot.telegram.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class StopCommand extends BotCommand {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  public StopCommand() {
      super("stop", "With this command you can stop the Bot");
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

    String userName = user.getFirstName() + " " + user.getLastName();

    try {
      absSender.execute(new SendMessage(chat.getId(), "Good bye " + userName + "\n" + "Hope to see you soon!"));
    } catch (TelegramApiException e) {
      logger.error("Error", e);
    }
  }
}