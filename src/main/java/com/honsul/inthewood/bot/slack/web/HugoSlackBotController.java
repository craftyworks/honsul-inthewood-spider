package com.honsul.inthewood.bot.slack.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackEventMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

@RestController
@RequestMapping("/bot/slack/hugo")
public class HugoSlackBotController {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  /**
   * Slack Action Handler
   */
  @PostMapping(value = "action", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void actionCommand(@RequestBody SlackActionCommand command) {
    logger.info("received action command : {}", command);
  }
  
  /**
   * Slack Menu Action Handler
   */
  @PostMapping(value = "actionMenu", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void menuAction(@RequestBody SlackActionCommand command) {
    logger.info("received incoming menu : {}", command);
  }  
  
  /** 
   * Slack Slash Command Handler 
   */
  @PostMapping(value = "slash", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public SlackMessage onReceiveSlashCommand(@RequestBody SlackSlashCommand slashCommand) {
    logger.info("received slash command : {}", slashCommand);
    
    return SlackMessage.builder().text("너의 명령을 잘 받았다.").build();
  }

  /**
   * Slack Event Handler
   */
  @PostMapping(value = "event")
  public SlackEventMessage onEvent(@RequestBody SlackEventMessage eventMessage) {
    logger.info("received event : {}", eventMessage);
    return eventMessage;
  }    
}
