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
  @JsonProperty("subscription_id")
  private String subscriptionId;
  @JsonProperty("user_id")
  private String userId;
  @JsonProperty("user_name")
  private String userName;
  private String channel;
  @JsonProperty("bookingDt")
  private String bookingDt;
  @JsonProperty("resort_id")
  private String resortId;
  @JsonProperty("resort_nm")
  private String resortNm;
  private String homepage;
  private String address;
  @JsonProperty("room_type")
  private String roomType;
}
