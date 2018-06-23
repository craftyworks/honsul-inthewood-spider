package com.honsul.inthewood.bot.slack.client.method;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.honsul.inthewood.bot.slack.client.model.OauthAccessRequest;
import com.honsul.inthewood.bot.slack.client.model.OauthAccessResponse;

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
public class OauthAccessMethod {
  
  private final String method = "oauth.access";
  
  private final HttpMethod httpMethod = HttpMethod.GET;
  
  private String code;

  public OauthAccessResponse execute(OauthAccessRequest request) {
    
    return null;
  }
}
