package com.honsul.inthewood.bot.slack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SlackActionCommand {
  private String type;

  private String token;
  
  @JsonProperty("action_ts")
  private String actionTs;
  
  @JsonProperty("callback_id")
  private String callbackId;
  
  @JsonProperty("trigger_id")
  private String triggerId;
  
  @JsonProperty("response_url")
  private String responseUrl;
  
  @JsonProperty("message_ts")
  private String messageTs;  
  
  private SlackTeam team;

  private SlackChannel channel;
  
  private SlackUser user;
  
  private SlackActionMessage message;
}
