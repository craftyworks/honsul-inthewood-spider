package com.honsul.inthewood.bot.slack.message;

import com.honsul.inthewood.bot.slack.model.SlackAction;
import com.honsul.inthewood.bot.slack.model.SlackAttachment;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage.SlackMessageBuilder;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

public class StopNotificationConfirmMessage {

  public static SlackMessage build(SlackSlashCommand command) {
    SlackMessageBuilder builder = SlackMessage.builder();
    
    builder.text("휴양림 예약현황 알림을 중지하시겠습니까?")
      .attachments(new SlackAttachment[] {
          SlackAttachment.builder()
            .color("good")
            .actions(new SlackAction[] {
                SlackAction.builder()
                  .name("stop-notification")
                  .text("확인")
                  .value("YES")
                  .type("button")
                  .style("primary")
                  .build(),
                SlackAction.builder()
                  .name("stop-notification")
                  .text("취소")
                  .value("NO")
                  .type("button")
                  .build()                  
            })
            .build()
      });
    
    return builder.build();
  }

}
