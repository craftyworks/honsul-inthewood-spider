package com.honsul.inthewood.bot.slack;

import com.honsul.inthewood.bot.slack.model.SlackMessage;

public interface SlackBotAPI {
  public void sendMessage(String url, SlackMessage slackMessage);
}
