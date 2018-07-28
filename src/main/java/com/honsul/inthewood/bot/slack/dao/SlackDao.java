package com.honsul.inthewood.bot.slack.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.honsul.inthewood.bot.slack.model.domain.SlackSubscription;
import com.honsul.inthewood.bot.slack.model.domain.SlackUser;

@Mapper
@Repository
public interface SlackDao {

  void updateSlackUser(SlackUser user);

  String getSlackBotAcccessTokenByUserId(String userId);

  String getSlackUserAcccessTokenByUserId(String userId);

  List<SlackSubscription> selectSlackSubscription(Map<String, String> param);

  List<Map<String, String>> selectResortOptionList(String value);

  Map<String, String> getSubscribeBookingDt(String value);

  int getBookingResortCount();

  List<Map<String, String>> selectComingWeekendBookingDt();

  void insertNewSubscription(SlackSubscription subscription);

  void updateSubscription(SlackSubscription subscription);

  void removeSubscription(String subscriptionId);

  SlackSubscription getSlackSubscription(SlackSubscription subscription);

}
