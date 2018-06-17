package com.honsul.inthewood.bot.slack.message;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.honsul.inthewood.bot.slack.model.SlackAttachment;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage.SlackMessageBuilder;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

public class UnknownSlashCommandResponseMessage {

  public static SlackMessage build(SlackSlashCommand slashCommand) {
    String text = "*" + slashCommand.getCommand() + "* 는 유효한 명령이 아닙니다.\\n";
    
    SlackMessageBuilder builder = SlackMessage.builder();
    builder.username("SnapBot")
      .text(text)
      .attachments(
          new SlackAttachment[] {
              SlackAttachment.builder()
                .text("슬랙에서 \"/\"문자로 시작하는 모든 메시지는 명령으로 해석됩니다. \\n메시지를 보내고 명령을 실행하지 않으려는 경우 \"/\"앞에 공백을 넣으십시오.")
                .color("warning")
                .ts("" + LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond())
                .footerIcon("https://platform.slack-edge.com/img/default_application_icon.png")
                .build()
          }
      );
    
    return builder.build();
  }
}
