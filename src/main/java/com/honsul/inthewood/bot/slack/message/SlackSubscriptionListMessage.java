package com.honsul.inthewood.bot.slack.message;

import java.util.Arrays;
import java.util.List;

import com.honsul.inthewood.bot.slack.model.SlackAction;
import com.honsul.inthewood.bot.slack.model.SlackAttachment;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.domain.SlackSubscription;

public class SlackSubscriptionListMessage {
  
  public static SlackMessage build(List<SlackSubscription> subscriptions) {
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
          .build()                  
    };
    
    SlackAttachment[] attchments = subscriptions
        .stream()
        .map(s -> SlackAttachment.builder()
            .title(s.getResortNm())
            .titleLink(s.getHomepage())
            .text(s.getBookingDt())
            .markdownIn(Arrays.asList(new String[] {"text", "pretext", "fields"}))
            .color("good")
            .actions(actions)
            .footer(s.getAddress())
            .footerIcon("https://platform.slack-edge.com/img/default_application_icon.png")
            .build()
         )
        .toArray(SlackAttachment[]::new);
    
    return SlackMessage.builder()
        .username("휴양림 정찰봇")
        .text(":loudspeaker: 정찰중인 휴양림 목록")
        .attachments(attchments)
        .build();
    
  }  
  
}
