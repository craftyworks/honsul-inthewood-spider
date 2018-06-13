package com.honsul.inthewood.bot.slack.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bot/slack/hugo")
public class HugoSlackBotController {
  
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  @PostMapping("action")
  public Map action(@RequestBody Map request) {
    logger.info("received incoming message : {}", request);
    return request;
  }
  
  @PostMapping("actionMenu")
  public Map menuAction(@RequestBody Map request) {
    logger.info("received incoming menu : {}", request);
    return request;
  }  
}
