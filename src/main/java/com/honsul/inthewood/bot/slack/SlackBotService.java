package com.honsul.inthewood.bot.slack;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honsul.inthewood.bot.slack.dao.SlackDao;
import com.honsul.inthewood.bot.slack.message.SlackAddSubscriptionDialog;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackDialog;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.Option;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.OptionGroup;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;
import com.honsul.inthewood.bot.slack.model.api.AuthTestResponse;
import com.honsul.inthewood.bot.slack.model.api.UserAuth;
import com.honsul.inthewood.bot.slack.model.domain.SlackSubscription;
import com.honsul.inthewood.bot.slack.model.domain.SlackUser;

@Service
public class SlackBotService {
  
  private static final Logger logger = LoggerFactory.getLogger(SlackBotService.class);

  @Autowired
  private SlackDao dao;
  
  @Autowired
  private SlackWebClient slackClient;
  
  @Transactional
  public SlackUser updateSlackUser(UserAuth auth) {
    // Slack 사용자 조회
    AuthTestResponse userInfo = slackClient.authTest(auth.getAccessToken());
    
    // Slack 사용자의 BOT IM Channel 조회
    String botChannel = slackClient.imList(auth.getAccessToken()).getIms()
        .stream()
        .filter(im -> im.getUser().equals(auth.getBot().getBotUserId()))
        .findFirst()
        .get().getId();
    
    SlackUser user = SlackUser.builder()
        .userId(auth.getUserId())
        .userName(userInfo.getUser())
        .teamId(auth.getTeamId())
        .teamName(auth.getTeamName())
        .accessToken(auth.getAccessToken())
        .botUserId(auth.getBot().getBotUserId())
        .botAccessToken(auth.getBot().getBotAccessToken())
        .botImChannel(botChannel)
        .build();
    dao.updateSlackUser(user);
    
    return user;
  }

  public String getSlackBotAccessToken(String userId) {
    return dao.getSlackBotAcccessTokenByUserId(userId);
  }

  public String getSlackUserAccessToken(String userId) {
    return dao.getSlackUserAcccessTokenByUserId(userId);
  }

  public List<SlackSubscription> selectSlackSubscription(SlackSlashCommand slashCommand) {
    return dao.selectSlackSubscription(slashCommand);
  }

  public SlackDialogSelectElement loadOption(SlackActionCommand command) {
    switch(command.getCallbackId()) {
      case "add_subscription":
        if("resort_nm".equals(command.getName())) {
          return loadResortNmSelectElement(command);
        } else if("booking_dt".equals(command.getName()) ) {
          return loadBookingDtSelectElement(command);
        }
      default:
        return new SlackDialogSelectElement();
    }
  }

  private SlackDialogSelectElement loadBookingDtSelectElement(SlackActionCommand command) {
    String param = command.getValue();
    param = param.replaceAll("[^0-9]", "");
    Map<String, String> dateInfo = dao.getSubscribeBookingDt(command.getValue());
    
    SlackDialogSelectElement element = SlackDialogSelectElement.builder().build();
    if(dateInfo != null) {
      element.addOption(Option.of(dateInfo.get("bookingDtTxt"), dateInfo.get("bookingDt")));
    }
    return element;
  }

  private SlackDialogSelectElement loadResortNmSelectElement(SlackActionCommand command) {
    List<Map<String, String>> result = dao.selectResortOptionList(command.getValue());
    
    SlackDialogSelectElement element = SlackDialogSelectElement.builder().build();

    OptionGroup g = null;
    for(Map<String, String> row : result) {
      if("Y".equals(row.get("groupYn"))) {
        g = OptionGroup.of(row.get("region"));
        element.addOptionGroup(g);
      }
      g.addOption(Option.of(row.get("resortNm"), row.get("resortId")));
    }
    return element;
  }

  public SlackDialog getSlackAddSubscriptionDialog() {
    int resortCount = dao.getBookingResortCount();
    
    SlackDialog dialog = SlackAddSubscriptionDialog.build(resortCount);
    
    return dialog;
  }

}
