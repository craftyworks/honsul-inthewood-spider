package com.honsul.inthewood.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.model.Resort;
import com.honsul.inthewood.core.model.Room;

@Mapper
@Repository
public interface SpiderDao {

  void updateBooking(Booking items);

  void updateRoom(Room item);

  List<Resort> selectResortList();

}
