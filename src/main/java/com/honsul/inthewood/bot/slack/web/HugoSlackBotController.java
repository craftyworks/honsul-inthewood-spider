package com.honsul.inthewood.bot.slack.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bot/slack/hugo")
public class HugoSlackBotController {
  
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  @PostMapping(value = "action", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public void actionCommand(@RequestBody Map<String, Object> payload, HttpServletRequest request) {
    Enumeration<String> enums = request.getParameterNames();
    while(enums.hasMoreElements()) {
      String key = enums.nextElement();
      logger.info("received params : {} : {}", key, request.getParameter(key));
    }
    logger.info("received incoming message : {}", payload);
  }
  
  @PostMapping(value = "actionMenu", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Map menuAction(Map request) {
    logger.info("received incoming menu : {}", request);
    return request;
  }  
  
  @PostMapping(value = "slash", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Map onReceiveSlashCommand(@RequestParam("token") String token,
                                       @RequestParam("team_id") String teamId,
                                       @RequestParam("team_domain") String teamDomain,
                                       @RequestParam("channel_id") String channelId,
                                       @RequestParam("channel_name") String channelName,
                                       @RequestParam("user_id") String userId,
                                       @RequestParam("user_name") String userName,
                                       @RequestParam("command") String command,
                                       @RequestParam("text") String text,
                                       @RequestParam("response_url") String responseUrl, HttpServletRequest request) {
      
    logger.info(token+","+teamId +"," + teamDomain + ", " + channelId + ", " + userId + ", " + userName + ", " + command + ", " + text + ", " + responseUrl);
    Enumeration<String> enums = request.getParameterNames();
    while(enums.hasMoreElements()) {
      String key = enums.nextElement();
      logger.info("received params : {} : {}", key, request.getParameter(key));
    }
    
    return new HashMap<>();
  }

  @PostMapping(value = "slash2", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Map slashCommand(Map payload, HttpServletRequest request) {
    Enumeration<String> enums = request.getParameterNames();
    while(enums.hasMoreElements()) {
      String key = enums.nextElement();
      logger.info("received params : {} : {}", key, request.getParameter(key));
    }
    logger.info("received incoming message : {}", payload);
    logger.info("received slash command : {}", request);
    return payload;
  }    
}
