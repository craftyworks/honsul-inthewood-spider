package com.honsul.inthewood.bot.slack.slash;

import org.springframework.stereotype.Component;

import com.honsul.inthewood.bot.slack.message.StopNotificationConfirmMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

@Component
public class StopCommandHandler extends SlashCommandHandler {
  public StopCommandHandler() {
    super("/stop");
  }

  @Override
  public SlackMessage execute(SlackSlashCommand command) {
    return StopNotificationConfirmMessage.build(command);
  }
}
