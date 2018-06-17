package com.honsul.inthewood.bot.slack.action;

import org.apache.commons.lang3.StringUtils;

import com.honsul.inthewood.bot.slack.SlackBotAPI;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;

public abstract class ActionCommandHandler {
  private String callbackId;
  
  public ActionCommandHandler(String callbackId) {
    this.callbackId = callbackId;
  }

  public abstract void execute(SlackBotAPI api, SlackActionCommand command);

  public boolean support(SlackActionCommand command) {
    return StringUtils.equals(command.getCallbackId(), this.callbackId);
  }
  
}