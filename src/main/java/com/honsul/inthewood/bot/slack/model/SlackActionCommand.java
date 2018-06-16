package com.honsul.inthewood.bot.slack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SlackActionCommand {
  private String token;
  
  @JsonProperty("callback_id")
  private String callbackId;
  
  private String type;
  
  @JsonProperty("trigger_id")
  private String triggerId;
  
  @JsonProperty("response_url")
  private String responseUrl;
  
  private SlackTeam team;

  private SlackChannel channel;
  
  private SlackUser user;
  
  private SlackMessage message;
}
