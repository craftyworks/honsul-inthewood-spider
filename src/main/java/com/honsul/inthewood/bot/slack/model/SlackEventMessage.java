package com.honsul.inthewood.bot.slack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SlackEventMessage {
  private String token;
  
  @JsonProperty("team_id")
  private String teamId;
  
  @JsonProperty("api_app_id")
  private String apiAppId;
  
  private Event event;
  
  private String type;
  
  private String challenge;
  
  @JsonProperty("event_id")
  private String eventId;
  
  @JsonProperty("event_time")
  private String eventTime;
  
  @JsonProperty("authed_users")
  private String[] authedUsers;
  
  @Data
  public static class Event {
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
}
