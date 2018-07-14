package com.honsul.inthewood.bot.slack.event;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.honsul.inthewood.bot.slack.SlackBotService;
import com.honsul.inthewood.bot.slack.SlackWebClient;
import com.honsul.inthewood.bot.slack.message.SlackSubscriptionListMessage;
import com.honsul.inthewood.bot.slack.message.UnknownSlashCommandResponseMessage;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackDialog;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;
import com.honsul.inthewood.bot.slack.model.api.DialogOpenRequest;
import com.honsul.inthewood.bot.slack.model.api.DialogOpenResponse;
import com.honsul.inthewood.bot.slack.model.domain.SlackSubscription;

@Component
public class SlackActionCommandListener implements EventBusListener{
  
  private static final Logger logger = LoggerFactory.getLogger(SlackActionCommandListener.class);

  @Autowired
  private SlackWebClient slackClient;
  
  @Autowired
  private SlackBotService service;
  
  /**
   * Slack Slash Command Subscriber Method.
   */
  @AllowConcurrentEvents
  @Subscribe
  public void receive(SlackActionCommand actionCommand) {
    logger.debug("receiver action command : {}", actionCommand);
    switch(actionCommand.getCallbackId().trim()) {
    case "add_subscription":
      break;
    case "add":
      break;
    case "도움말": case "help": case "?":
      break;
    default:
      break;
    }
  }
  
  private void unknown(SlackSlashCommand slashCommand) {
    logger.info("unknown slash command : {}, {}", slashCommand.getCommand(), slashCommand.getText());
    SlackMessage slackMessage = UnknownSlashCommandResponseMessage.build(slashCommand);
    slackClient.sendMessage(slashCommand.getResponseUrl(), slackMessage);
  }
  
  private void help(SlackSlashCommand slashCommand) {
    logger.info("help slash command : {}, {}", slashCommand.getCommand(), slashCommand.getText());
    SlackMessage slackMessage = UnknownSlashCommandResponseMessage.build(slashCommand);
    slackClient.sendMessage(slashCommand.getResponseUrl(), slackMessage);
  }
  
  private void add(SlackSlashCommand slashCommand) {
    logger.info("slash add command : {}, {}", slashCommand.getCommand(), slashCommand.getText());
    
    String token = service.getSlackBotAccessToken(slashCommand.getUserId());
    String triggerId = slashCommand.getTriggerId();
    SlackDialog dialog = service.getSlackAddSubscriptionDialog();
    
    logger.info("Dialog open request {}, {}, {}", token, triggerId, dialog);
    DialogOpenResponse response = slackClient.dialogOpen(DialogOpenRequest.builder()
        .token(token)
        .triggerId(triggerId)
        .dialog(dialog).build()
    );
    logger.info("Dialog Open response : {}", response);
  }

  private void list(SlackSlashCommand slashCommand) {
    logger.info("slash list command : {}, {}", slashCommand.getCommand(), slashCommand.getText());
    
    List<SlackSubscription> subscriptions = service.selectSlackSubscription(slashCommand);
    SlackMessage slackMessage = SlackSubscriptionListMessage.build(subscriptions);
    slackClient.sendMessage(slashCommand.getResponseUrl(), slackMessage);
  }
  
}
