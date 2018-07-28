package com.honsul.inthewood.bot.slack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.honsul.inthewood.bot.slack.core.SlackAPI;
import com.honsul.inthewood.bot.slack.core.SlackClient;
import com.honsul.inthewood.bot.slack.core.SlackConfig;
import com.honsul.inthewood.bot.slack.model.SlackDeleteMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessageResponse;
import com.honsul.inthewood.bot.slack.model.TokenBarer;
import com.honsul.inthewood.bot.slack.model.api.AuthTestResponse;
import com.honsul.inthewood.bot.slack.model.api.DialogOpenRequest;
import com.honsul.inthewood.bot.slack.model.api.DialogOpenResponse;
import com.honsul.inthewood.bot.slack.model.api.ImListResponse;
import com.honsul.inthewood.bot.slack.model.api.UserAuth;

@Component
public class SlackWebClient implements SlackClient {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private SlackConfig slackConfig;
  
  public void setSlackConfig(SlackConfig slackConfig) {
    this.slackConfig = slackConfig;
  }
  
  private final RestTemplate restTemplate = new RestTemplate();
  
  public UserAuth oauthAccess(String code) {
    
    Map<String, Object> param = new HashMap<>();
    param.put("code", code);
    param.put("client_id", slackConfig.getBot().getClientId());
    param.put("client_secret", slackConfig.getBot().getClientSecret());
    
    return executeSlackAPI(SlackAPI.oauth_access, param, UserAuth.class);
  }
  
  public Map<String, Object> usersInfo(String token, String userId) {
    
    Map<String, Object> param = new HashMap<>();
    param.put("token", token);
    param.put("user", userId);
    
    return executeSlackAPI(SlackAPI.users_info, param, Map.class);
  }
  
  
  public Map<String, Object> usersIdentity(String accessToken)  {
    Map<String, Object> param = new HashMap<>();
    param.put("token", accessToken);
    
    return getSlackAPI(SlackAPI.users_identity, param, Map.class);
  }
  
  public SlackMessageResponse chatMeMessage(SlackMessage message) {
    return postSlackAPI(SlackAPI.chat_meMessage, message, SlackMessageResponse.class);
  }
  
  public SlackMessageResponse chatPostMessage(SlackMessage message) {
    return executeSlackAPI(SlackAPI.chat_postMessage, message, SlackMessageResponse.class);
  }
  
  public SlackMessageResponse chatDelete(SlackMessage message) {
    return executeSlackAPI(SlackAPI.chat_delete, message, SlackMessageResponse.class);
  }
  
  public SlackMessageResponse chatUpdate(SlackMessage message) {
    return executeSlackAPI(SlackAPI.chat_update, message, SlackMessageResponse.class);
  }
  
  public Map<String, Object> appsPermissionsScopesList(String accessToken) {
    Map<String, Object> param = new HashMap<>();
    param.put("token", accessToken);
    
    return executeSlackAPI(SlackAPI.apps_permissions_scopes_list, param, Map.class);
  }  
  
  public Map<String, Object> channelsList(String accessToken) {
    Map<String, Object> param = new HashMap<>();
    param.put("token", accessToken);
    param.put("exclude_members", true);
    param.put("exclude_archived", true);
    
    return executeSlackAPI(SlackAPI.channels_list, param, Map.class);
  }  
  
  public ImListResponse imList(String accessToken) {
    Map<String, Object> param = new HashMap<>();
    param.put("token", accessToken);
    
    return executeSlackAPI(SlackAPI.im_list, param, ImListResponse.class);
  }
  
  public AuthTestResponse authTest(String accessToken) {
    Map<String, Object> param = new HashMap<>();
    param.put("token", accessToken);
    
    return executeSlackAPI(SlackAPI.auth_test, param, AuthTestResponse.class);
  }  
  
  
  public DialogOpenResponse dialogOpen(DialogOpenRequest request) {
    return executeSlackAPI(SlackAPI.dialog_open, request, DialogOpenResponse.class);
  }
  
  private <T> T executeSlackAPI(SlackAPI api, Object param, Class<T> resultClass) {
    if(HttpMethod.GET.equals(api.getMethod())) {
      return getSlackAPI(api, param, resultClass);
    } else {
      return postSlackAPI(api, param, resultClass);
    }
  }

  private <T> T postSlackAPI(SlackAPI api, Object param, Class<T> resultClass) {
    logger.info("post api {}, param : {}", api.getCommand(), param);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api.getURL());

    HttpHeaders headers = new HttpHeaders();
    if(param instanceof TokenBarer) {
      headers.set("Authorization", "Bearer " + ((TokenBarer)param).getToken());
    } else if (param instanceof Map) {
      headers.set("Authorization", "Bearer " + ((Map)param).get("token"));
    }
    headers.setContentType(api.getAccessType()); 
    HttpEntity entity = new HttpEntity(param, headers);

    ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
    
    logger.info("post api {}, response : {}, {}", api.getCommand(), response.getStatusCode(), response.getBody());
    
    try {
      return new ObjectMapper().readValue(response.getBody(), resultClass);
    } catch (IOException e) {
      new RuntimeException("Salck API Error", e);
    }
    
    return null;
  }

  private <T> T getSlackAPI(SlackAPI api, Object param, Class<T> resultClass) {
    logger.info("get api {}, param : {}", api.getCommand(), param);
    
    if(MediaType.APPLICATION_FORM_URLENCODED.equals(api.getAccessType())) {
      UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api.getURL());
      
      if(param instanceof Map) {
        for(Entry<String, Object> entry : ((Map<String, Object>) param).entrySet()) {
          builder.queryParam(entry.getKey(), entry.getValue());
        }        
      }
      
      ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

      logger.info("get api {}, http response : {}, {}", api.getCommand(), response.getStatusCode(), response.getBody());
      
      try {
        T result = new ObjectMapper().readValue(response.getBody(), resultClass);
        logger.info("get api {}, result : {}", api.getMethod(), result);
        return result;
      } catch (IOException e) {
        new RuntimeException("Salck API Error", e);
      }
    }
    
    return null;
  }
  
  
  @Override
  public void sendMessage(String url, SlackMessage slackMessage) {

    ResponseEntity<String> response = restTemplate.postForEntity(url, slackMessage, String.class);
    
    logger.info("message response : {}, {}", response.getStatusCode(), response.getBody());
  }
  
  @Override
  public void deleteMessage(SlackDeleteMessage message) {

    ResponseEntity<String> response = restTemplate.postForEntity("https://slack.com/api/chat.delete", message, String.class);
    
    logger.info("message response : {}, {}", response.getStatusCode(), response.getBody());
  }


}
