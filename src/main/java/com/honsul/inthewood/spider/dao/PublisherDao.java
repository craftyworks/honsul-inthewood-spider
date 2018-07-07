package com.honsul.inthewood.spider.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.honsul.inthewood.bot.slack.model.domain.SlackUser;
import com.honsul.inthewood.core.model.Resort;
import com.honsul.inthewood.core.model.Subscriber;

@Mapper
@Repository
public interface PublisherDao {

  List<Map<String, String>> selectNewEntryBookings(Resort resort);

  List<Subscriber> selectBookingSubscriber(Map<String, String> booking);

  List<SlackUser> selectBookingSlackSubscriber(Map<String, String> booking);

}
