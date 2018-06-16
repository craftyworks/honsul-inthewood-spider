package com.honsul.inthewood.bot.slack.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackEventMessage;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

@RestController
@RequestMapping("/bot/slack/hugo")
public class HugoSlackBotController {
  
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  @PostMapping(value = "action", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String actionCommand(@ModelAttribute("payload") SlackActionCommand command) {
    logger.info("received action command : {}", command);
    return "ok";
  }
  
  @PostMapping(value = "actionMenu", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String menuAction(@ModelAttribute("payload") SlackActionCommand command) {
    logger.info("received incoming menu : {}", command);
    return "menuAction ok";
  }  
  
  @PostMapping(value = "slash", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public SlackSlashCommand onReceiveSlashCommand(@ModelAttribute SlackSlashCommand slashCommand) {
    logger.info("received slash command : {}", slashCommand);
    
    return slashCommand;
  }

  @PostMapping(value = "event")
  public SlackEventMessage onEvent(@RequestBody SlackEventMessage eventMessage) {
    logger.info("received event : {}", eventMessage);
    return eventMessage;
  }    
}
