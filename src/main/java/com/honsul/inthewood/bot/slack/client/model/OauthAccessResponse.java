package com.honsul.inthewood.bot.slack.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.honsul.inthewood.bot.slack.model.SlackTeam;
import com.honsul.inthewood.bot.slack.model.SlackUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class OauthAccessResponse {
  
  private boolean ok;
  
  @JsonProperty("access_token")
  private String accessToken;
  
  private String scope;
  
  private SlackUser user;
  
  private SlackTeam team;

}
