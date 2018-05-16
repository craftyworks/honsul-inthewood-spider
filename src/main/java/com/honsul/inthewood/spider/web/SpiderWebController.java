package com.honsul.inthewood.spider.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsul.inthewood.core.model.Hotel;
import com.honsul.inthewood.spider.collector.SpiderCollector;

@RestController
@RequestMapping("/")
public class SpiderWebController {

  @Autowired
  private SpiderCollector collector;
  
  @GetMapping("collect/{hotelId}") 
  public void collect(@PathVariable("hotelId") String hotelId) {
    Hotel hotel = new Hotel();
    hotel.setHotelId(hotelId);
    collector.collect(hotel);
  }
  
  @GetMapping("collect/booking/{hotelId}") 
  public void collectBooking(@PathVariable("hotelId") String hotelId) {
    Hotel hotel = new Hotel();
    hotel.setHotelId(hotelId);
    collector.collectBooking(hotel);
  }
  
  @GetMapping("collect/room/{hotelId}") 
  public void collectRoom(@PathVariable("hotelId") String hotelId) {
    Hotel hotel = new Hotel();
    hotel.setHotelId(hotelId);
    collector.collectRoom(hotel);
  }
  

}
