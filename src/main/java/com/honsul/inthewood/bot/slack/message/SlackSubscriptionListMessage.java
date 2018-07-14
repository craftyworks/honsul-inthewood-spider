package com.honsul.inthewood.bot.slack.message;

import java.util.Arrays;
import java.util.List;

import com.honsul.inthewood.bot.slack.model.SlackAction;
import com.honsul.inthewood.bot.slack.model.SlackAction.Confirm;
import com.honsul.inthewood.bot.slack.model.SlackAttachment;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.domain.SlackSubscription;

public class SlackSubscriptionListMessage {
  
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
          .build()        
    };
    return actions;
  }
  
  public static SlackMessage build(List<SlackSubscription> subscriptions) {

    SlackAttachment[] attchments = subscriptions
        .stream()
        .map(s -> SlackAttachment.builder()
            .title("대상 : " + s.getResortNm())
            .titleLink(s.getHomepage())
            .text("일정 : " + s.getBookingDt())
            .markdownIn(Arrays.asList(new String[] {"text", "pretext", "fields"}))
            .color("#3AA3E3")
            .actions(actions(s))
            .footer(s.getAddress())
            .footerIcon("https://platform.slack-edge.com/img/default_application_icon.png")
            .build()
         )
        .toArray(SlackAttachment[]::new);
    
    return SlackMessage.builder()
        .username("휴양림 정찰봇")
        .text(":notebook: 현재 *정찰*중인 휴양림 목록은 다음과 같습니다.")
        .attachments(attchments)
        .build();
    
  }  
  
}
