package com.honsul.inthewood.bot.telegram.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class HelpCommand extends BotCommand {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  public HelpCommand() {
      super("help", "Get all the commands this bot provides");
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
    if (ICommandRegistry.class.isInstance(absSender)) {
      ICommandRegistry commandRegistry = (ICommandRegistry) absSender;
      
      StringBuilder helpMessageBuilder = new StringBuilder("<b>Help</b>\n");
      helpMessageBuilder.append("These are the registered commands for this Bot:\n\n");

      for (IBotCommand botCommand : commandRegistry.getRegisteredCommands()) {
          helpMessageBuilder.append(botCommand.toString()).append("\n\n");
      }

      try {
          absSender.execute(new SendMessage(chat.getId(), helpMessageBuilder.toString()).setParseMode("HTML"));
      } catch (TelegramApiException e) {
          logger.error("error", e);
      }
    }
  }
    
}
