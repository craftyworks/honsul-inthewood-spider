package com.honsul.inthewood.bot.slack.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;
import com.honsul.inthewood.bot.slack.model.domain.SlackSubscription;
import com.honsul.inthewood.bot.slack.model.domain.SlackUser;

@Mapper
@Repository
public interface SlackDao {

  void updateSlackUser(SlackUser user);

  String getSlackBotAcccessTokenByUserId(String userId);

  String getSlackUserAcccessTokenByUserId(String userId);

  List<SlackSubscription> selectSlackSubscription(SlackSlashCommand slashCommand);

  List<Map<String, String>> selectResortOptionList(String value);

  Map<String, String> getSubscribeBookingDt(String value);

  int getBookingResortCount();

  List<Map<String, String>> selectComingWeekendBookingDt();

}
