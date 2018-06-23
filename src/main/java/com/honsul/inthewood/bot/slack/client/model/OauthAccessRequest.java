package com.honsul.inthewood.bot.slack.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class OauthAccessRequest {
  
  @JsonProperty("client_id")
  private String clientId;  

  @JsonProperty("client_secret")
  private String clientRecret;  

  private String code;  

  @JsonProperty("redirect_uri")
  private String redirectUri;  

  @JsonProperty("single_channel")
  private String singleChannel;   

}
