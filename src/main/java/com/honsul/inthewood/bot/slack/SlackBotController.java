package com.honsul.inthewood.bot.slack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.honsul.inthewood.bot.slack.message.WelcomeMessage;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackEventMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessageResponse;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;
import com.honsul.inthewood.bot.slack.model.api.UserAuth;
import com.honsul.inthewood.bot.slack.model.domain.SlackUser;

@Controller
@RequestMapping("/bot/slack")
public class SlackBotController {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private SlackBotService service;
  
  @Autowired
  private SlackWebClient slackClient;

  @Autowired
  private EventBus eventBus;
  
  @GetMapping("/install")
  public String install(Model model) {
    model.addAttribute("name", "SpringBlog from Millky");
    return "bot/slack/install";
  }
  
  @GetMapping("/oauth")
  public String oauth(@RequestParam(name="code", required=false) String code, @RequestParam(name="state", required=false) String state) {
    
    // Slack Oauth 인증
    UserAuth auth = slackClient.oauthAccess(code);
    
    // Slack 사용자 등록 
    SlackUser user = service.updateSlackUser(auth);
    
    // Welcome message 발송
    SlackMessageResponse response = slackClient.chatPostMessage(WelcomeMessage.build(user));
    return "redirect:" + "https://" + user.getUserName() + ".slack.com/messages/" + response.getChannel() + "/";
  }
  
  /**
   * Slack Action Handler
   */
  @PostMapping(value = "action", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void actionCommand(@RequestBody SlackActionCommand command) {
    logger.info("received action command : {}", command);
    
    eventBus.post(command);
  }
  
  /**
   * Slack Menu Action Handler
   */
  @PostMapping(value = "actionMenu", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void menuAction(@RequestBody SlackActionCommand command) {
    logger.info("received incoming menu : {}", command);
    
    eventBus.post(command);
  }  
  
  /** 
   * Slack Slash Command Handler 
   */
  @ResponseBody
  @PostMapping(value = "slash", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void onReceiveSlashCommand(@RequestBody SlackSlashCommand slashCommand) {
    logger.info("received slash command : {}", slashCommand);
    
    eventBus.post(slashCommand);
  }

  /**
   * Slack Event Handler
   */
  @PostMapping(value = "event")
  @ResponseBody
  public SlackEventMessage onEvent(@RequestBody SlackEventMessage eventMessage) {
    logger.info("received event : {}", eventMessage);
    
    eventBus.post(eventMessage);
    
    return eventMessage;
  }    
  
}
