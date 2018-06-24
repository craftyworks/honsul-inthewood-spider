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
public class SlackUser {
  @JsonProperty("user_id")
  private String userId;
  @JsonProperty("user_name")
  private String userName;
  @JsonProperty("team_id")
  private String teamId;  
  @JsonProperty("team_name")
  private String teamName;
  @JsonProperty("access_token")
  private String accessToken;  
  @JsonProperty("bot_user_id")
  private String botUserId;
  @JsonProperty("bot_access_token")
  private String botAccessToken;
  @JsonProperty("bot_im_channel")
  private String botImChannel;
}
