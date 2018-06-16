package com.honsul.inthewood.bot.slack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SlackEvent {
  private String type;
  
  private String user;
  
  private String text;
  
  @JsonProperty("client_msg_id")
  private String clientMsgId;
  
  private String ts;
  
  private String channel;
  
  @JsonProperty("event_ts")
  private String eventTs;
  
  @JsonProperty("channel_type")
  private String channelType;
}
