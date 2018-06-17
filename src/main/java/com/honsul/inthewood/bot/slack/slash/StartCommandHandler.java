package com.honsul.inthewood.bot.slack.slash;

import org.springframework.stereotype.Component;

import com.honsul.inthewood.bot.slack.message.StartNotificationConfirmMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

@Component
public class StartCommandHandler extends SlashCommandHandler {
  
  public StartCommandHandler() {
    super("/start");
  }
  
  @Override
  public SlackMessage execute(SlackSlashCommand command) {
    return StartNotificationConfirmMessage.build(command);
  }
}
