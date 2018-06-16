package com.honsul.inthewood.bot.slack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SlackSlashCommand {
  private String token;
  
  private String command;
  
  private String text;

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

  @JsonProperty("response_url")
  private String responseUrl;
}
