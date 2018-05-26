package com.honsul.inthewood.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.model.Resort;
import com.honsul.inthewood.core.model.Room;

@Mapper
@Repository
public interface ApiDao {

  Resort getResort(String resortId);

  List<Resort> selectResort();

  Resort getResort(Map<String, Object> param);

  List<Room> selectResortRoom(Map<String, Object> param);

  List<Booking> selectResortBooking(Map<String, Object> param);

  List<Booking> selectBooking(Map<String, Object> param);

}
