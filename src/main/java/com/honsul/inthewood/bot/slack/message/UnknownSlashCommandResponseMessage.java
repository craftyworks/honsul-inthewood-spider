package com.honsul.inthewood.bot.slack.message;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.honsul.inthewood.bot.slack.model.SlackAttachment;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage.SlackMessageBuilder;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

public class UnknownSlashCommandResponseMessage {

  public static SlackMessage build(SlackSlashCommand slashCommand) {
    SlackMessageBuilder builder = SlackMessage.builder();
    builder.username("SnapBot")
      .text("문의하신 내용을 처리할 수 없습니다.")
      .attachments(
          new SlackAttachment[] {
              SlackAttachment.builder()
                .text("시스템에 문제가 발생했습니다.")
                .color("bad")
                .ts("" + LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond())
                .footerIcon("https://platform.slack-edge.com/img/default_application_icon.png")
                .build()
          }
      );
    
    return builder.build();
  }
}
