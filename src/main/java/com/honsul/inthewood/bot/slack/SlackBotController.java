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
import com.honsul.inthewood.bot.slack.model.SlackActionCommandResponsable;
import com.honsul.inthewood.bot.slack.model.SlackDialogOptionHolder;
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
   * Slack Action Command Handler.
   * <li> 예약현황을 정찰할 휴양림 정보 등록
   * <li> 정찰중인 휴양림 정보를 수정
   * <li> 정찰중인 휴양림 정보를 삭제 
   * Action 수행 후 결과 메시지는 현재 정찰중인 휴양림 목록을 출력한다.
   */
  @ResponseBody
  @PostMapping(value = "action", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public SlackActionCommandResponsable actionCommand(@RequestBody SlackActionCommand command) {
    logger.info("received action command [type : {}, callbackId : {}, payload : {}]", command.getType(), command.getCallbackId(), command);
    SlackActionCommandResponsable resp = null;
    
    switch (command.getType()) {
      case dialog_submission:
        //수정
        if(command.getCallbackId().startsWith("edit_subscription")) {
          String subscriptionId = command.getCallbackId().split("$")[1];
          command.getSubmission().put("subscription_id", subscriptionId);
          command.setCallbackId("edit_subscription");
        }
        
        // 휴양림 정찰 등록/수정
        resp = service.saveSubscription(command);
        break;
      case interactive_message:
      case message_action:
      default:
        resp = SlackActionCommandResponsable.OK;
        break;
    }
    
    // 정찰중인 휴양림 목록 출력
    eventBus.post(command);
    return resp;
  }
  
  /**
   * Slack Dynamic Option loading Command.
   * <p>정찰 휴양림 설정 다이알로그의 휴양림 목록과 대상 일자 콤보의 옵션 목록을 다이나믹하게 로딩한다.
   */
  @ResponseBody
  @PostMapping(value = "loadOption", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public SlackDialogOptionHolder loadOption(@RequestBody SlackActionCommand command) {
    logger.info("received load option : {}", command);
    
    return service.loadDialogOptions(command);
  }  
  
  /** 
   * Slack Slash Command Handler.
   * 
   * <li> /scout list - 현재 정찰중인 휴양림 목록을 출력
   * <li> /scout add - 휴양림 정찰대상 추가 다이알로그 출력
   * <li> /scout help - 사용방법 출력
   * <p>Slash Command 에 대한 처리는 EventBus 에 등록된 {@link com.honsul.inthewood.bot.slack.event.SlackSlashCommandListener} 에서 처리한다.
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
