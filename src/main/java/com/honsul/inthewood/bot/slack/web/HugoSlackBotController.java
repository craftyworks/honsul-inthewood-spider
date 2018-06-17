package com.honsul.inthewood.bot.slack.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.honsul.inthewood.bot.slack.SlackBotAPI;
import com.honsul.inthewood.bot.slack.action.ActionCommandHandler;
import com.honsul.inthewood.bot.slack.message.UnknownSlashCommandResponseMessage;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackEventMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;
import com.honsul.inthewood.bot.slack.slash.SlashCommandHandler;

@RestController
@RequestMapping("/bot/slack/hugo")
public class HugoSlackBotController implements SlackBotAPI {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final RestTemplate restTemplate = new RestTemplate();
  
  @Autowired
  private List<ActionCommandHandler> actionCommandHandlers;
  
  @Autowired
  private List<SlashCommandHandler> slashCommandHandlers;
  
  /**
   * Slack Action Handler
   */
  @PostMapping(value = "action", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void actionCommand(@RequestBody SlackActionCommand command) {
    logger.info("received action command : {}", command);
    
    for(ActionCommandHandler handler : actionCommandHandlers) {
      if(handler.support(command)) {
        handler.execute(this, command);
        break;
      }
    }    
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
    
    SlackMessage rtnMessage = null;
    for(SlashCommandHandler handler : slashCommandHandlers) {
      if(handler.support(slashCommand)) {
        rtnMessage = handler.execute(slashCommand);
        break;
      }
    }
    
    if(rtnMessage == null) {
      rtnMessage = UnknownSlashCommandResponseMessage.build(slashCommand);
    }
    return rtnMessage;
  }

  /**
   * Slack Event Handler
   */
  @PostMapping(value = "event")
  public SlackEventMessage onEvent(@RequestBody SlackEventMessage eventMessage) {
    logger.info("received event : {}", eventMessage);
    return eventMessage;
  }    
  
  @Override
  public void sendMessage(String url, SlackMessage slackMessage) {

    ResponseEntity<String> response = restTemplate.postForEntity(url, slackMessage, String.class);
    
    logger.debug("message response : {}, {}", response.getStatusCode(), response.getBody());
  }
}
