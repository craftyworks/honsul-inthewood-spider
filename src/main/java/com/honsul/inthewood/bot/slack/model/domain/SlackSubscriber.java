package com.honsul.inthewood.bot.slack.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlackSubscriber {
  @JsonProperty("subscription_id")
  private String subscriptionId;
  @JsonProperty("user_id")
  private String userId;
  private String channel;
  private String token;
}
