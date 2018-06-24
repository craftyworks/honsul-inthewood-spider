package com.honsul.inthewood.bot.slack.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.honsul.inthewood.bot.slack.model.TokenBarer;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UserAuth implements TokenBarer {
  private boolean ok;
  private String error;
  @JsonProperty("access_token")
  private String accessToken;
  private String scope;
  @JsonProperty("user_id")
  private String userId;
  @JsonProperty("team_name")
  private String teamName;
  @JsonProperty("team_id")
  private String teamId;
  private Bot bot;
  
  @Data
  @JsonInclude(Include.NON_NULL)
  public static class Bot {
    @JsonProperty("bot_user_id")
    private String botUserId;
    @JsonProperty("bot_access_token")
    private String botAccessToken;
  }

  @Override
  public String getToken() {
    return accessToken;
  }
  
}
