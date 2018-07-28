package com.honsul.inthewood.bot.slack.message;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.honsul.inthewood.bot.slack.model.SlackAction;
import com.honsul.inthewood.bot.slack.model.SlackAction.Confirm;
import com.honsul.inthewood.bot.slack.model.SlackAttachment;
import com.honsul.inthewood.bot.slack.model.SlackField;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.domain.SlackSubscription;

/**
 * Slack 휴양림 정찰 추가 완료 메시지.
 */
public class SlackSubscriptionCompleteMessage {
  
  private static SlackAction[] actions(SlackSubscription subscription) {
    SlackAction[] actions = {
        SlackAction.builder()
          .name("edit")
          .text("수정")
          .value("edit")
          .type("button")
          .style("primary")
          .build(),
        SlackAction.builder()
          .name("remove")
          .text("삭제")
          .value("remove")
          .type("button")
          .style("danger")
          .confirm(Confirm.builder()
              .title("삭제 하시겠습니까?")
              .text("삭제 하시면 더이상 " + subscription.getResortNm() + " 에 대한 정찰을 수행하지 않겠어요.")
              .okText("Remove")
              .dismissText("Cancel")
              .build())
          .build(),
        SlackAction.builder()
          .name("list")
          .text("목록")
          .value("list")
          .type("button")
          .build()
    };
    return actions;
  }
  
  private static SlackField[] fields(SlackSubscription subscription) {
    String title = subscription.getResortNm();
    if(!StringUtils.isEmpty(subscription.getHomepage())) {
      title = "<" + subscription.getHomepage() + "|" + title + ">";
    }
    return new SlackField[] { SlackField.of("휴양림", title), SlackField.of("일정", subscription.getBookingDt()) };
  }  
  
  public static SlackMessage build(SlackSubscription subscription) {

    SlackAttachment attachment = SlackAttachment.builder()
      .callbackId("list_subscription")
      .title("")
      .text("")
      .fields(fields(subscription))
      .markdownIn(Arrays.asList(new String[] {"text", "pretext", "fields"}))
      .color("good")
      .actions(actions(subscription))
      .footer(subscription.getAddress())
      .footerIcon("https://platform.slack-edge.com/img/default_application_icon.png")
      .build();

    
    return SlackMessage.builder()
        .username("휴양림 정찰봇")
        .text("신규 정찰이 추가되었습니다.")
        .attachments(new SlackAttachment[] {attachment})
        .build();
  }

}
