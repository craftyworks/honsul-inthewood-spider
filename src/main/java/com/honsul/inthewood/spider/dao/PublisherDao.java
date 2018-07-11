package com.honsul.inthewood.spider.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.domain.SlackSubscriber;
import com.honsul.inthewood.core.model.Resort;

@Mapper
@Repository
public interface PublisherDao {

  List<Map<String, String>> selectNewEntryBookings(Resort resort);

  List<SlackSubscriber> selectBookingSlackSubscriber(Map<String, String> booking);

  List<SlackMessage> selectClosedBookingNotification(Resort resort);

  void updateMessageStatusDead(SlackMessage messageLog);

  void insertBookingNotificationMessage(Map booking);

}
