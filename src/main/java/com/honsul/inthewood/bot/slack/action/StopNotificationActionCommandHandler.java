package com.honsul.inthewood.bot.slack.action;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.honsul.inthewood.bot.slack.core.SlackClient;
import com.honsul.inthewood.bot.slack.model.SlackAction;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackAttachment;
import com.honsul.inthewood.bot.slack.model.SlackDeleteMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage;

@Component
public class StopNotificationActionCommandHandler extends ActionCommandHandler {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  public StopNotificationActionCommandHandler() {
    super("/stop/confirm");
  }

  @Override
  public void execute(SlackClient api, SlackActionCommand command) {
    SlackAction action = command.getActions()[0];
    logger.debug("##### : {}, {}", action.getValue(), action);
    if("cancel".equals(action.getValue())) {
      logger.debug("deleting message : {}", command.getToken(), command.getChannel(), command.getMessageTs());
      SlackDeleteMessage message = SlackDeleteMessage.builder()
        .token(command.getToken())
        .channel(command.getChannel().getId())
        .ts(command.getMessageTs())
        .build();
      api.deleteMessage(message);
    } else {
      SlackMessage message = command.getOriginalMessage();
      message.setText("휴양림 예약현황 알림 서비스가 중지되었습니다.");
      message.setAttachments(new SlackAttachment[] {
          SlackAttachment.builder()
          .text("*중지*된 예약현황 알림 서비스를 재시작하려면 */start* 명령을 사용하세요.")
          .markdownIn(Arrays.asList(new String[] {"text"}))
          .color("#3AA3E3")
          .attachmentType("default")
          .build()
      });
      api.sendMessage(command.getResponseUrl(), message);
    }
  }

}
