package com.honsul.inthewood.bot.slack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SlackMessageResponse {
  private boolean ok;
  
  private String error;
  
  private String channel;
  
  private String ts;
  
  private SlackMessage message;
  
}
