package com.honsul.inthewood.bot.slack.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bot/slack/hugo")
public class HugoSlackBotController {
  
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  @PostMapping(value = "action", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Map actionCommand(@RequestBody Map request) {
    logger.info("received incoming message : {}", request);
    return request;
  }
  
  @PostMapping(value = "actionMenu", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Map menuAction(@RequestBody Map request) {
    logger.info("received incoming menu : {}", request);
    return request;
  }  
  
  @PostMapping(value = "slash", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Map slashCommand(@RequestBody Map request) {
    logger.info("received slash command : {}", request);
    return request;
  }    
}
