package com.honsul.inthewood.bot.slack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honsul.inthewood.bot.slack.dao.SlackDao;
import com.honsul.inthewood.bot.slack.message.SlackSubscriptionDialog;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackDialog;
import com.honsul.inthewood.bot.slack.model.SlackDialogOptionHolder;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.Option;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.OptionGroup;
import com.honsul.inthewood.bot.slack.model.SlackDialogSubmissionResponse;
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

  public List<SlackSubscription> selectSlackSubscription(String userId, String channelId) {
    Map<String, String> param = new HashMap<>();
    param.put("userId", userId);
    param.put("channelId", channelId);
    return dao.selectSlackSubscription(param);
  }

  public SlackDialogOptionHolder loadDialogOptions(SlackActionCommand command) {
    if(command.getCallbackId().startsWith("add_subscription") || command.getCallbackId().startsWith("edit_subscription")) {
      if("resort_nm".equals(command.getName())) {
        return loadResortDialogOptions(command);
      } else if("booking_dt".equals(command.getName()) ) {
        if(StringUtils.isEmpty(command.getValue())) {
          return defaultBookingDtDialogOptions(command);
        } else {
          return loadBookingDtDialogOptions(command);
        }
      }
    }
    
    return SlackDialogOptionHolder.ofOptionGroups(new ArrayList<>());
  }

  private SlackDialogOptionHolder loadBookingDtDialogOptions(SlackActionCommand command) {
    String param = command.getValue();
    param = param.replaceAll("[^0-9]", "");
    Map<String, String> dateInfo = dao.getSubscribeBookingDt(param);
    
    List<Option> options = new ArrayList<>();
    if(dateInfo != null) {
      options.add(Option.of(dateInfo.get("bookingDtTxt"), dateInfo.get("bookingDt")));
    }
    return SlackDialogOptionHolder.ofOptions(options);
  }
  
  private SlackDialogOptionHolder defaultBookingDtDialogOptions(SlackActionCommand command) {
    List<Map<String, String>> weeks = dao.selectComingWeekendBookingDt();
    
    List<Option> options = new ArrayList<>();
    for(Map<String, String> row  : weeks) {
      options.add(Option.of(row.get("bookingDtTxt"), row.get("bookingDt")));
    }
    return SlackDialogOptionHolder.ofOptions(options);
  }
  
  private SlackDialogOptionHolder loadResortDialogOptions(SlackActionCommand command) {
    List<Map<String, String>> result = dao.selectResortOptionList(command.getValue());
    
    List<OptionGroup> optionGroups = new ArrayList<>();
    OptionGroup g = null;

    // 빈 문자열로 검색 시 전국 옵션 추가
    if(StringUtils.isEmpty(command.getValue())) {
      g = OptionGroup.of("All");
      g.addOption(Option.of("전국 " + result.size() + "개 휴양림" , "*"));
      optionGroups.add(g);
    }
    
    for(Map<String, String> row : result) {
      if("Y".equals(row.get("groupYn"))) {
        g = OptionGroup.of(row.get("region"));
        optionGroups.add(g);
      }
      g.addOption(Option.of(row.get("resortNm"), row.get("resortId")));
    }
    return SlackDialogOptionHolder.ofOptionGroups(optionGroups);
  }

  /**
   * 휴양림 정찰 추가 다이알로그 리턴.
   */
  public SlackDialog getSlackAddSubscriptionDialog() {
    return SlackSubscriptionDialog.build();
  }
  
  /**
   * 휴양림 정찰 수정 다이알로그 리턴.
   */
  public SlackDialog getSlackEditSubscriptionDialog(String subscriptionId) {
    SlackSubscription subscription = getSlackSubscriptionById(subscriptionId);
    
    Option selectedResort = Option.of(subscription.getResortNm(), subscription.getResortId());
    Option selectedBookingDt = Option.of(subscription.getBookingDtTxt(), subscription.getBookingDt());
    
    return SlackSubscriptionDialog.build("edit_subscription$" + subscriptionId, selectedResort, selectedBookingDt);
  }
  
  /**
   * 휴양림 정찰 등록/수정.
   */
  public SlackDialogSubmissionResponse saveSubscription(SlackActionCommand command) {
    switch(command.getCallbackId()) {
      case "add_subscription":
        insertNewSubscription(command);
        break;
      case "edit_subscription":
        updateSubscription(command);
        break;
      default:
        throw new AssertionError("Never happened");
    }
    
    return SlackDialogSubmissionResponse.ok();
  }

  /**
   * SlackSubscription 수정
   */
  private void updateSubscription(SlackActionCommand command) {
    SlackSubscription subscription = createSlackSubscriptionFromSubmissionCommand(command);
    dao.updateSubscription(subscription);
  }

  /**
   * 신규 SlackSubscription 등록
   */
  private void insertNewSubscription(SlackActionCommand command) {
    SlackSubscription subscription = createSlackSubscriptionFromSubmissionCommand(command);
    dao.insertNewSubscription(subscription);
  }

  public SlackSubscription getSlackSubscriptionById(String subscriptionId) {
    SlackSubscription vo = new SlackSubscription();
    vo.setSubscriptionId(subscriptionId);
    return dao.getSlackSubscription(vo);
  }
  
  public SlackSubscription getSlackSubscriptionFromSubmissionCommand(SlackActionCommand actionCommand) {
    SlackSubscription vo = createSlackSubscriptionFromSubmissionCommand(actionCommand);
    return dao.getSlackSubscription(vo);
  }

  private SlackSubscription createSlackSubscriptionFromSubmissionCommand(SlackActionCommand command) {
    SlackSubscription subscription = SlackSubscription.builder()
        .userId(command.getUser().getId())
        .channel(command.getChannel().getId())
        .bookingDt(command.getSubmission().get("booking_dt"))
        .resortId(command.getSubmission().get("resort_nm"))
        .subscriptionId(command.getSubmission().get("subscription_id"))
        .roomType("*")
        .build();
    return subscription;
  }

  public void removeSlackSubscription(String subscriptionId) {
    dao.removeSubscription(subscriptionId);
  }

}
