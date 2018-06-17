package com.honsul.inthewood.bot.slack.slash;

import org.apache.commons.lang3.StringUtils;

import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

public abstract class SlashCommandHandler {
  private String command;
  
  public SlashCommandHandler(String command) {
    this.command = command;
  }

  public abstract SlackMessage execute(SlackSlashCommand command);

  public boolean support(SlackSlashCommand command) {
    return StringUtils.equals(command.getCommand(), this.command);
  }
  
}
