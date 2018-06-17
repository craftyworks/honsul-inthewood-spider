package com.honsul.inthewood.bot.slack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SlackActionMessage {
  private String type;
  
  private String user;
  
  private String text;

  @JsonProperty("client_msg_id")
  private String clientMsgId;
  
  private String ts;
  
}
