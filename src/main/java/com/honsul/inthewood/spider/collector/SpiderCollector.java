package com.honsul.inthewood.spider.collector;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.annotation.RoomParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.model.Hotel;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.spider.dao.SpiderDao;

@Service
public class SpiderCollector {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  ApplicationContext applicationContext;
  
  @Autowired
  SpiderDao dao;
  
  @Scheduled(fixedDelay=3000000)
  private void collectAllBooking() {
    List<Hotel> hotels = dao.selectHotels();
    for(Hotel hotel : hotels) {
      collectBooking(hotel);
    }
  }

  public void collect(Hotel hotel) {
    collectRoom(hotel);
    collectBooking(hotel);
  }
  
  /**
   * 휴양림 예약현황 수집
   */
  public void collectBooking(Hotel hotel) {
    SpiderContext.setHotel(hotel);
    List<Booking> items = lookupBookingParser(hotel.getHotelId()).parse();
    for(Booking item : items) {
      dao.updateBooking(item);      
    }
  }
  
  /**
   * 휴양림 숙소정보 수집
   */
  public void collectRoom(Hotel hotel) {
    SpiderContext.setHotel(hotel);
    List<Room> items = lookupRoomParser(hotel.getHotelId()).parse();
    for(Room item : items) {
      dao.updateRoom(item);      
    }
  }

  private Parser<Booking> lookupBookingParser(String hotelId) {
    Map<String, Object> beans = applicationContext.getBeansWithAnnotation(BookingParser.class);
    for(Object bean : beans.values()) {
      BookingParser annotation = bean.getClass().getAnnotation(BookingParser.class);
      if(hotelId.equals(annotation.hotelId())) {
        return (Parser<Booking>) bean;
      }
    }
    return null;
  }
  
  private Parser<Room> lookupRoomParser(String hotelId) {
    Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RoomParser.class);
    for(Object bean : beans.values()) {
      RoomParser annotation = bean.getClass().getAnnotation(RoomParser.class);
      if(hotelId.equals(annotation.hotelId())) {
        return (Parser<Room>) bean;
      }
    }
    return null;
  }
}
