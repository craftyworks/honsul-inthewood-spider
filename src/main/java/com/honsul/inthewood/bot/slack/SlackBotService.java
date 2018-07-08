package com.honsul.inthewood.bot.slack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honsul.inthewood.bot.slack.dao.SlackDao;
import com.honsul.inthewood.bot.slack.model.api.AuthTestResponse;
import com.honsul.inthewood.bot.slack.model.api.UserAuth;
import com.honsul.inthewood.bot.slack.model.domain.SlackUser;

@Service
public class SlackBotService {

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


}
