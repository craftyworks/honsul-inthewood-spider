package com.honsul.inthewood.bot.slack.action;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.honsul.inthewood.bot.slack.core.SlackClient;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackAttachment;
import com.honsul.inthewood.bot.slack.model.SlackMessage;

@Component
public class StartNotificationActionCommandHandler extends ActionCommandHandler {

  public StartNotificationActionCommandHandler() {
    super("/start/confirm");
  }

  @Override
  public void execute(SlackClient api, SlackActionCommand command) {
    SlackMessage message = command.getOriginalMessage();
    message.setText("휴양림 예약현황 알림 서비스가 시작되었습니다.");
    message.setAttachments(new SlackAttachment[] {
        SlackAttachment.builder()
        .text("휴양림 예약현황 알림 설정을 변경하려면 */setting* 명령을 사용하세요.")
        .markdownIn(Arrays.asList(new String[] {"text"}))
        .color("#3AA3E3")
        .attachmentType("default")
        .build()
    });

    api.sendMessage(command.getResponseUrl(), message);
  }

}
