package com.honsul.inthewood.bot.slack.model.api;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public enum SlackAPI {
  
  oauth_access(HttpMethod.GET, MediaType.APPLICATION_FORM_URLENCODED), 
  user_identity(HttpMethod.GET, MediaType.APPLICATION_FORM_URLENCODED);
  
  private final HttpMethod httpMethod;
  
  private final MediaType acceptType;
  
  SlackAPI(HttpMethod httpMethod, MediaType acceptType) {
    this.httpMethod = httpMethod;
    this.acceptType = acceptType;
  }
  
  public String getCommand() {
    return name().replace('_', '.');
  }
  
  public String getURL() {
    return "https://slack.com/api/" + getCommand();
  }
  
  public HttpMethod getMethod() {
    return this.httpMethod;
  }
  
  public MediaType getAccessType() {
    return this.acceptType;
  }
}
