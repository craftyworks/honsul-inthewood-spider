package com.honsul.inthewood.bot.slack.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.honsul.inthewood.bot.slack.model.domain.SlackUser;

@Mapper
@Repository
public interface SlackDao {

  void updateSlackUser(SlackUser user);

  void insertBookingNotificationMessage(Map booking);
  
}
