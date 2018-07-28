package com.honsul.inthewood.bot.slack.message;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.honsul.inthewood.bot.slack.model.SlackAction;
import com.honsul.inthewood.bot.slack.model.SlackAction.Confirm;
import com.honsul.inthewood.bot.slack.model.SlackAttachment;
import com.honsul.inthewood.bot.slack.model.SlackField;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.domain.SlackSubscription;

public class SlackSubscriptionListMessage {
  
  private static SlackAction[] actions(SlackSubscription subscription) {
    SlackAction[] actions = {
        SlackAction.builder()
          .name("edit")
          .text("수정")
          .value(subscription.getSubscriptionId())
          .type("button")
          .style("primary")
          .build(),
        SlackAction.builder()
          .name("remove")
          .text("삭제")
          .value(subscription.getSubscriptionId())
          .type("button")
          .style("danger")
          .confirm(Confirm.builder()
              .title("삭제 하시겠습니까?")
              .text("삭제 하시면 더이상 " + subscription.getResortNm() + " 에 대한 정찰을 수행하지 않겠어요.")
              .okText("Remove")
              .dismissText("Cancel")
              .build())
          .build()        
    };
    return actions;
  }
  
  private static SlackField[] fields(SlackSubscription subscription) {
    String title = subscription.getResortNm();
    if(!StringUtils.isEmpty(subscription.getHomepage())) {
      title = "<" + subscription.getHomepage() + "|" + title + ">";
    }
    return new SlackField[] { SlackField.of("휴양림", title), SlackField.of("일정", subscription.getBookingDtTxt()) };
  }  
  
  public static SlackMessage build(List<SlackSubscription> subscriptions) {

    SlackAttachment[] attchments = subscriptions
        .stream()
        .map(s -> SlackAttachment.builder()
            .callbackId("list_subscription")
            .title("")
            .text("")
            .fields(fields(s))
            .markdownIn(Arrays.asList(new String[] {"text", "pretext", "fields"}))
            .color("#3AA3E3")
            .actions(actions(s))
            .footer(s.getAddress())
            .footerIcon("https://platform.slack-edge.com/img/default_application_icon.png")
            .build()
         )
        .toArray(SlackAttachment[]::new);
    
    String text = attchments.length > 0 
        ? "정찰중인 휴양림 목록은 다음과 같습니다." 
        : "정찰중인 휴양림이 존재하지 않습니다.\n`/scout add` 명령어를 입력하여 정찰할 휴양림 정보를 추가하세요.";
    return SlackMessage.builder()
        .username("휴양림 정찰봇")
        .text(text)
        .attachments(attchments)
        .build();
    
  }

}
