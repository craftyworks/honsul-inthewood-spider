package com.honsul.inthewood.bot.slack.core;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public enum SlackAPI {
  
  oauth_access(HttpMethod.GET, MediaType.APPLICATION_FORM_URLENCODED), 
  users_identity(HttpMethod.GET, MediaType.APPLICATION_FORM_URLENCODED),
  users_info(HttpMethod.GET, MediaType.APPLICATION_FORM_URLENCODED),
  chat_meMessage(HttpMethod.POST, MediaType.APPLICATION_JSON_UTF8),
  chat_postMessage(HttpMethod.POST, MediaType.APPLICATION_JSON_UTF8),
  auth_test(HttpMethod.POST, MediaType.APPLICATION_JSON_UTF8),
  apps_permissions_scopes_list(HttpMethod.GET, MediaType.APPLICATION_FORM_URLENCODED),
  channels_list(HttpMethod.GET, MediaType.APPLICATION_FORM_URLENCODED),
  im_list(HttpMethod.GET, MediaType.APPLICATION_FORM_URLENCODED),
  chat_delete(HttpMethod.POST, MediaType.APPLICATION_JSON_UTF8),
  chat_update(HttpMethod.POST, MediaType.APPLICATION_JSON_UTF8),
  dialog_open(HttpMethod.POST, MediaType.APPLICATION_JSON_UTF8),
  ;
  
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
