package com.honsul.inthewood.bot.slack.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackActionCommand implements TokenBarer {
  private Type type;

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
  
  @JsonProperty("attachment_id")
  private String attachmentId;  
  
  @JsonProperty("is_app_unfurl")
  private String isAppUnfurl;  
  
  private String name;
  
  private String value;
  
  private Team team;

  private Channel channel;
  
  private User user;
  
  private Message message;
  
  private SlackAction[] actions;
  
  @JsonProperty("original_message")
  private SlackMessage originalMessage;
  
  private Map<String, String> submission;
  
  @Data
  public static class User {
    private String id;
    
    private String name;
  }
  
  @Data
  public static class Team {
    private String id;
    
    private String domain;
  }
  
  @Data
  public static class Channel {
    private String id;
    
    private String name;
  }
  
  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Message {
    private String type;
    
    private String user;
    
    private String username;
    
    private String text;

    @JsonProperty("client_msg_id")
    private String clientMsgId;
    
    private String ts;
  }
  public static enum Type {
    dialog_submission, dialog_suggestion, interactive_message, message_action;  
  }
}
