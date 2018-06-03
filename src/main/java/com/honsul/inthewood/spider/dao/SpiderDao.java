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

  void deleteBooking(Resort resort);

  void deleteRoom(Resort resort);

  void deleteBookingPrevious(Resort resort);

  void insertBookingPrevious(Resort resort);

  void insertBooking(Booking item);

  List<Booking> selectNewEntryBookings(Resort resort);

}
