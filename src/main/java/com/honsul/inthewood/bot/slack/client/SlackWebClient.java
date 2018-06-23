package com.honsul.inthewood.bot.slack.client;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.honsul.inthewood.bot.slack.client.method.OauthAccessMethod;
import com.honsul.inthewood.bot.slack.client.model.OauthAccessRequest;
import com.honsul.inthewood.bot.slack.client.model.OauthAccessResponse;

public class SlackWebClient {
  
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  private final RestTemplate restTemplate = new RestTemplate();
  
  public OauthAccessResponse oauthAccess(OauthAccessRequest request) {
    OauthAccessMethod method = OauthAccessMethod.builder().code(code).build();
    return method.execute();
  }
  
  public void usersIdentity(String token) {
    
  }
  
  UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://slack.com/api/oauth.access")
      .queryParam("code", code)
      .queryParam("client_id", "5002703614.380331396292")
      .queryParam("client_secret", "0676487d4a0d4e00b50ec35b8a202a23");
      
  logger.debug("request uri : {}", builder.toUriString());
  ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);
  HashMap<String, Object> jsonMap = new ObjectMapper().readValue(response.getBody(), HashMap.class);
  logger.debug("message response : {}, {}, {}, {}", response.getStatusCode(), jsonMap.get("ok"), jsonMap.get("ok").getClass(), jsonMap);
  
}
