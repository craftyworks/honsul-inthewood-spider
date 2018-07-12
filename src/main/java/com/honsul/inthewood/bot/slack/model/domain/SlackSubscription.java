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
public class SlackSubscription {
  @JsonProperty("user_id")
  private String userId;
  @JsonProperty("user_name")
  private String userName;
  @JsonProperty("subscription_id")
  private String subscriptionId;
  @JsonProperty("resort_id")
  private String resortId;
  @JsonProperty("resort_nm")
  private String resortNm;
  private String homepage;
  private String address;
  @JsonProperty("bookingDt")
  private String bookingDt;
}
