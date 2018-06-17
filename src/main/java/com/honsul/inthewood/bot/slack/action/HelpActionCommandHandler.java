package com.honsul.inthewood.bot.slack.action;

import org.springframework.stereotype.Component;

import com.honsul.inthewood.bot.slack.SlackBotAPI;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;

@Component
public class HelpActionCommandHandler extends ActionCommandHandler {

  public HelpActionCommandHandler() {
    super("help");
  }

  @Override
  public void execute(SlackBotAPI api, SlackActionCommand command) {
    
  }

}
