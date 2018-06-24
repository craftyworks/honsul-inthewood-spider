package com.honsul.inthewood.bot.slack.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AuthTestResponse {
  private boolean ok;
  private String error;
  private String url;
  private String team;
  @JsonProperty("team_id")
  private String teamId;
  private String user;
  @JsonProperty("user_id")
  private String userId;  
}
