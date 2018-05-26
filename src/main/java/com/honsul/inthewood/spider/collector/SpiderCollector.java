package com.honsul.inthewood.spider.collector;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.model.Resort;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.spider.dao.SpiderDao;

@Service
public class SpiderCollector {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  ApplicationContext applicationContext;
  
  @Autowired
  SpiderDao dao;
  
  public void collect(Resort resort) {
    collectRoom(resort);
    collectBooking(resort);
  }
  
  /**
   * 전체 휴양림 예약현황 수집
   */
  public void collectAllBooking() {
    List<Resort> resorts = dao.selectResortList();
    
    for(Resort resort : resorts) {
      collectBooking(resort);
    }
  }
  
  /**
   * 휴양림 예약현황 수집
   */
  public void collectBooking(Resort resort) {
    SpiderContext.setResort(resort);
    List<Booking> items = lookupBookingParser(resort.getResortId()).parse();
    for(Booking item : items) {
      dao.updateBooking(item);      
    }
  }
  
  /**
   * 휴양림 숙소정보 수집
   */
  public void collectRoom(Resort resort) {
    SpiderContext.setResort(resort);
    List<Room> items = lookupRoomParser(resort.getResortId()).parse();
    for(Room item : items) {
      dao.updateRoom(item);      
    }
  }

  private Parser<Booking> lookupBookingParser(String resortId) {
    Map<String, Object> beans = applicationContext.getBeansWithAnnotation(BookingParser.class);
    for(Object bean : beans.values()) {
      BookingParser annotation = bean.getClass().getAnnotation(BookingParser.class);
      if(resortId.equals(annotation.resortId())) {
        return (Parser<Booking>) bean;
      }
    }
    return null;
  }
  
  private Parser<Room> lookupRoomParser(String resortId) {
    Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RoomParser.class);
    for(Object bean : beans.values()) {
      RoomParser annotation = bean.getClass().getAnnotation(RoomParser.class);
      if(resortId.equals(annotation.resortId())) {
        return (Parser<Room>) bean;
      }
    }
    return null;
  }
}
