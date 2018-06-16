package com.honsul.inthewood.bot.slack.model;

import lombok.Data;

@Data
public class SlackMessage {
  private String type;
  
  private String user;
  
  private String ts;
  
  private String text;
}
