package com.honsul.inthewood.bot.slack.web;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

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
  public SlackSlashCommand onReceiveSlashCommand(@ModelAttribute SlackSlashCommand slashCommand) {
    logger.info("received slash command : {}", slashCommand);
    
    return slashCommand;
  }

  @PostMapping(value = "event")
  public Map<String, Object> onEvent(@RequestBody Map<String, Object> event) {
    logger.info("received event : {}", event);
    return event;
  }    
}
