package com.honsul.inthewood.bot.slack.message;

import java.util.Arrays;

import com.honsul.inthewood.bot.slack.model.SlackAction;
import com.honsul.inthewood.bot.slack.model.SlackAttachment;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage.SlackMessageBuilder;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

public class StartNotificationConfirmMessage {

  public static SlackMessage build(SlackSlashCommand command) {
    SlackMessageBuilder builder = SlackMessage.builder();
    
    builder.text("휴양림 예약현황 알림을 시작하시겠습니까?")
      .responseType("in_channel")
      .attachments(new SlackAttachment[] {
          SlackAttachment.builder()
            .text("*확인* 버튼을 클릭하면 예약현황 알림 서비스가 시작 됩니다.\n휴양림 예약현황 알림 설정을 변경하려면 */setting* 명령을 사용하세요.")
            .callbackId("/start/confirm")
            .markdownIn(Arrays.asList(new String[] {"text"}))
            .color("#3AA3E3")
            .attachmentType("default")
            .actions(new SlackAction[] {
                SlackAction.builder()
                  .name("confirm")
                  .text("확인")
                  .value("yes")
                  .type("button")
                  .style("primary")
                  .build(),
                SlackAction.builder()
                  .name("confirm")
                  .text("취소")
                  .value("cancel")
                  .type("button")
                  .build()                  
            })
            .build()
      });
    
    return builder.build();
  }

}
