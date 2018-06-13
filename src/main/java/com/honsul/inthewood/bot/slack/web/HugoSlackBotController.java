package com.honsul.inthewood.bot.slack.web;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bot/slack/hugo")
public class HugoSlackBotController {
  
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  @PostMapping(value = "action", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public void actionCommand(Map message, HttpServletRequest request) {
    Enumeration<String> enums = request.getParameterNames();
    while(enums.hasMoreElements()) {
      String key = enums.nextElement();
      logger.info("received params : {} : {}", key, request.getParameter(key));
    }
    logger.info("received incoming message : {}", message);
  }
  
  @PostMapping(value = "actionMenu", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Map menuAction(Map request) {
    logger.info("received incoming menu : {}", request);
    return request;
  }  
  
  @PostMapping(value = "slash", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Map slashCommand(Map request) {
    logger.info("received slash command : {}", request);
    return request;
  }    
}
