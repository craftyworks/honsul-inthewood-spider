package com.honsul.inthewood.bot.slack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.remote.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.honsul.inthewood.bot.slack.core.SlackClient;
import com.honsul.inthewood.bot.slack.core.SlackConfig;
import com.honsul.inthewood.bot.slack.model.SlackDeleteMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.api.SlackAPI;

@Component
public class SlackWebClient implements SlackClient {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private SlackConfig slackConfig;
  
  private final RestTemplate restTemplate = new RestTemplate();
  
  public String oauthAccess(String code) {
    
    Map<String, Object> param = new HashMap<>();
    param.put("code", code);
    param.put("client_id", slackConfig.getBot().getClientId());
    param.put("client_secret", slackConfig.getBot().getClientSecret());
    
    Map<String, Object> result = getSlackAPI(SlackAPI.oauth_access, param, Map.class);
    
    return (String) result.get("access_token");
  }
  
  public Map<String, Object> usersIdentity(String accessToken)  {
    Map<String, Object> param = new HashMap<>();
    param.put("access_token", accessToken);
    
    return getSlackAPI(SlackAPI.user_identity, param, Map.class);
  }
  
  private <T> T executeSlackAPI(SlackAPI api, Map<String, Object> param, Class<T> resultClass) {
    if(HttpMethod.GET.equals(api.getMethod())) {
      return getSlackAPI(api, param, resultClass);
    }
    return null;
  }
  
  private <T> T getSlackAPI(SlackAPI api, Map<String, Object> param, Class<T> resultClass) {
    if(MediaType.APPLICATION_FORM_URLENCODED.equals(api.getAccessType())) {
      UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api.getURL());
      
      for(Entry<String, Object> entry : param.entrySet()) {
        builder.queryParam(entry.getKey(), entry.getValue());
      }
      
      ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

      logger.debug("message response : {}, {}", response.getStatusCode(), response.getBody());
      
      try {
        return new ObjectMapper().readValue(response.getBody(), resultClass);
      } catch (IOException e) {
        new RuntimeException("Salck API Error", e);
      }
    }
    
    return null;
  }
  
  
  @Override
  public void sendMessage(String url, SlackMessage slackMessage) {

    ResponseEntity<String> response = restTemplate.postForEntity(url, slackMessage, String.class);
    
    logger.debug("message response : {}, {}", response.getStatusCode(), response.getBody());
  }
  
  @Override
  public void deleteMessage(SlackDeleteMessage message) {

    ResponseEntity<String> response = restTemplate.postForEntity("https://slack.com/api/chat.delete", message, String.class);
    
    logger.debug("message response : {}, {}", response.getStatusCode(), response.getBody());
  }  
}
