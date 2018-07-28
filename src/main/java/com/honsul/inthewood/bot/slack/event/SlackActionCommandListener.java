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
import com.honsul.inthewood.bot.slack.message.SlackSubscriptionCompleteMessage;
import com.honsul.inthewood.bot.slack.message.SlackSubscriptionListMessage;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.domain.SlackSubscription;

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
    switch (actionCommand.getCallbackId()) {
      case add_subscription:
        addSubscription(actionCommand);
        break;
      case list_subscription:
        switch(actionCommand.getActions()[0].getName()) {
          case "list":
            listSubscription(actionCommand);
            break;
          case "edit":
            editSubscription(actionCommand);
            break;
          case "remove":
            removeSubscription(actionCommand);
            break;
          default:
            throw new AssertionError("Never Happend");
        }
      default:
        break;
    }
  }
  
  /**
   * 정찰중인 휴양림 목록 출력.
   */
  private void listSubscription(SlackActionCommand actionCommand) {
    logger.info("action command : {}, {}", actionCommand.getType(), actionCommand.getCallbackId());
    
    List<SlackSubscription> subscriptions = service.selectSlackSubscription(actionCommand.getUser().getId(), actionCommand.getChannel().getId());
    
    slackClient.sendMessage(actionCommand.getResponseUrl(), SlackSubscriptionListMessage.build(subscriptions));
  }
  
  /**
   * 신규 휴양림 정찰 등록 후 결과 메시지 출력.
   */
  private void addSubscription(SlackActionCommand actionCommand) {
    logger.info("action command : {}, {}", actionCommand.getType(), actionCommand.getCallbackId());
    
    SlackSubscription subscription = service.getSlackSubscriptionFromSubmissionCommand(actionCommand);
    
    slackClient.sendMessage(actionCommand.getResponseUrl(), SlackSubscriptionCompleteMessage.build(subscription));
  }
  
  /**
   * 정찰 휴양림 정보 수정.
   */
  private void editSubscription(SlackActionCommand actionCommand) {
    logger.info("action command : {}, {}", actionCommand.getType(), actionCommand.getCallbackId());
    
    String subscriptionId = actionCommand.getActions()[0].getValue();
    SlackSubscription subscription = service.getSlackSubscriptionById(subscriptionId);
    
    slackClient.sendMessage(actionCommand.getResponseUrl(), SlackSubscriptionCompleteMessage.build(subscription));
  }
  
  /**
   * 정찰 대상 휴양림 삭제 요청.
   */
  private void removeSubscription(SlackActionCommand actionCommand) {
    logger.info("action command : {}, {}", actionCommand.getType(), actionCommand.getCallbackId());
    
    // subscritionId 로 구독 삭제
    String subscriptionId = actionCommand.getActions()[0].getValue();
    service.removeSlackSubscription(subscriptionId);
    
    listSubscription(actionCommand);
  }  
}
