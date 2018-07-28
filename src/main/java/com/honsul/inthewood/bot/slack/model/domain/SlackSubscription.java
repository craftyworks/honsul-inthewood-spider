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
  @JsonProperty("booking_dt")
  private String bookingDt;
  @JsonProperty("booking_dt_txt")
  private String bookingDtTxt;
  @JsonProperty("resort_id")
  private String resortId;
  @JsonProperty("resort_nm")
  private String resortNm;
  private String homepage;
  private String address;
  @JsonProperty("room_type")
  private String roomType;
}
