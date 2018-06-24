package com.honsul.inthewood.bot.slack;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.client.RestTemplate;

import com.honsul.inthewood.bot.slack.action.ActionCommandHandler;
import com.honsul.inthewood.bot.slack.message.UnknownSlashCommandResponseMessage;
import com.honsul.inthewood.bot.slack.message.WelcomeMessage;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackEventMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessageResponse;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;
import com.honsul.inthewood.bot.slack.model.api.UserAuth;
import com.honsul.inthewood.bot.slack.slash.SlashCommandHandler;

@Controller
@RequestMapping("/bot/slack")
public class SlackBotController {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  private final RestTemplate restTemplate = new RestTemplate();
  
  @Autowired
  private List<ActionCommandHandler> actionCommandHandlers;
  
  @Autowired
  private List<SlashCommandHandler> slashCommandHandlers;
  
  @Autowired
  private SlackWebClient slackClient;

  @GetMapping("/oauth")
  public String oauth(@RequestParam(name="code", required=false) String code, @RequestParam(name="state", required=false) String state) {
    
    // Slack Oauth 인증
    UserAuth auth = slackClient.oauthAccess(code);
    
    // Slack 사용자 조회
    Map<String, Object> userInfo = slackClient.authTest(auth.getAccessToken());
    
    // Slack 사용자 등록
    
    // Welcome message 발송
    SlackMessageResponse response = slackClient.chatPostMessage(WelcomeMessage.build(auth));
    return "redirect:" + userInfo.get("url") + "messages/" + response.getChannel() + "/";
  }
  
  /**
   * Slack Action Handler
   */
  @PostMapping(value = "action", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void actionCommand(@RequestBody SlackActionCommand command) {
    logger.info("received action command : {}", command);
    
    for(ActionCommandHandler handler : actionCommandHandlers) {
      if(handler.support(command)) {
        handler.execute(slackClient, command);
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
  @ResponseBody
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
  @ResponseBody
  public SlackEventMessage onEvent(@RequestBody SlackEventMessage eventMessage) {
    logger.info("received event : {}", eventMessage);
    return eventMessage;
  }    
  
  @GetMapping("/install")
  public String install(Model model) {
    model.addAttribute("name", "SpringBlog from Millky");
    return "bot/slack/install";
  }
  
}
