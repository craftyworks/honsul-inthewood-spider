package com.honsul.inthewood.bot.slack.slash;

import org.springframework.stereotype.Component;

import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

@Component
public class SettingCommandHandler extends SlashCommandHandler {
  public SettingCommandHandler() {
    super("/setting");
  }

  @Override
  public SlackMessage execute(SlackSlashCommand command) {
    return null;
  }
  
}
