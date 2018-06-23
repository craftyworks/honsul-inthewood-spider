package com.honsul.inthewood.bot.slack.core;

import com.honsul.inthewood.bot.slack.model.SlackDeleteMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage;

public interface SlackClient {
  public void sendMessage(String url, SlackMessage slackMessage);

  public void deleteMessage(SlackDeleteMessage message);
}
