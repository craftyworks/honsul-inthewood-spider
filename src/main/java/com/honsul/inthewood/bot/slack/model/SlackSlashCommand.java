package com.honsul.inthewood.bot.slack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackSlashCommand implements TokenBarer {
  private String token;
  
  @JsonProperty("team_id")
  private String teamId;

  @JsonProperty("team_domain")
  private String teamDomain;

  @JsonProperty("channel_id")
  private String channelId;

  @JsonProperty("channel_name")
  private String channelName;
  
  @JsonProperty("user_id")
  private String userId;

  @JsonProperty("user_name")
  private String userName;
  
  private String command;
  
  private String text;

  @JsonProperty("response_url")
  private String responseUrl;

  @JsonProperty("trigger_id")
  private String triggerId;
  
}
