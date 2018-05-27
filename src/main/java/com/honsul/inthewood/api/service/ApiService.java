package com.honsul.inthewood.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honsul.inthewood.api.dao.ApiDao;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.model.Resort;
import com.honsul.inthewood.core.model.Room;

@Service
public class ApiService {
  @Autowired
  ApiDao dao;

  public List<Resort> resortList() {
    return dao.selectResort();
  }

  public Resort getResort(String resortId) {
    Map<String, Object> param = new HashMap<>();
    param.put("resortId", resortId);
    
    return dao.getResort(param);
  }

  public List<Room> resortRoomList(String resortId) {
    Map<String, Object> param = new HashMap<>();
    param.put("resortId", resortId);
    
    return dao.selectResortRoom(param);
  }

  public Room getResortRoom(String resortId, String roomNo) {
    Map<String, Object> param = new HashMap<>();
    param.put("resortId", resortId);
    param.put("roomNo", roomNo);
    
    return dao.getResortRoom(param);
  }
  
  public List<Booking> resortBookingList(String resortId, String bookingDt) {
    Map<String, Object> param = new HashMap<>();
    param.put("resortId", resortId);
    param.put("bookingDt", bookingDt);
    
    return dao.selectResortBooking(param);
  }

  public List<Booking> bookingList(String bookingDt) {
    Map<String, Object> param = new HashMap<>();
    param.put("bookingDt", bookingDt);
    
    return dao.selectBooking(param);    
  }



}
