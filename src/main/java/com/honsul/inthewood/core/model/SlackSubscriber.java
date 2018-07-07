package com.honsul.inthewood.core.model;

import lombok.Data;

@Data
public class SlackSubscriber {
  private String userId;
  private String token;
  private String channel;
}
