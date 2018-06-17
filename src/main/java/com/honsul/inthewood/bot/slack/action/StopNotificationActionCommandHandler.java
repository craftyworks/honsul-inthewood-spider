package com.honsul.inthewood.bot.slack.action;

import org.springframework.stereotype.Component;

import com.honsul.inthewood.bot.slack.SlackBotAPI;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackMessage;

@Component
public class StopNotificationActionCommandHandler extends ActionCommandHandler {

  public StopNotificationActionCommandHandler() {
    super("/stop/confirm");
  }

  @Override
  public void execute(SlackBotAPI api, SlackActionCommand command) {
    api.sendMessage(command.getResponseUrl(), SlackMessage.builder().text("처리 완료!").build());
  }

}
