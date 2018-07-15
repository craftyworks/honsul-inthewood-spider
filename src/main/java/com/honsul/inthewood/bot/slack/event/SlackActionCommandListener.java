package com.honsul.inthewood.bot.slack.event;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.honsul.inthewood.bot.slack.SlackBotService;
import com.honsul.inthewood.bot.slack.SlackWebClient;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackMessage;

@Component
public class SlackActionCommandListener implements EventBusListener{
  
  private static final Logger logger = LoggerFactory.getLogger(SlackActionCommandListener.class);

  @Autowired
  private SlackWebClient slackClient;
  
  @Autowired
  private SlackBotService service;
  
  /**
   * Slack Action Command Subscriber Method.
   */
  @AllowConcurrentEvents
  @Subscribe
  public void receive(SlackActionCommand actionCommand) {
    logger.debug("receiver action command : {}", actionCommand);
    switch(actionCommand.getCallbackId().trim()) {
    case "add_subscription":
      addSubscription(actionCommand);
      break;
    default:
      break;
    }
  }
  
  private void addSubscription(SlackActionCommand actionCommand) {
    logger.info("action command : {}, {}", actionCommand.getType(), actionCommand.getCallbackId());
    Map<String, String> submission = actionCommand.getSubmission();
    String resortId = submission.get("resort_nm");
    String bookingDt = submission.get("booking_dt");
    logger.debug("add subscription({}, {})", resortId, bookingDt);
    slackClient.sendMessage(actionCommand.getResponseUrl(), SlackMessage.builder().build());
  }
  
}
