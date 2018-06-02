package com.honsul.inthewood.api.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honsul.inthewood.api.service.ApiService;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.model.Room;

@RestController
@RequestMapping("/api")
public class ApiController {

  @Autowired
  private ApiService service;
  
  @GetMapping("resorts") 
  public List<Map> resortList() {
    return service.resortList();
  }
  
  @GetMapping("resort/{resortId}") 
  public Map getResort(@PathVariable("resortId") String resortId) {
    return service.getResort(resortId);
  }
  
  @GetMapping("resort/{resortId}/rooms") 
  public List<Room> resortRoomList(@PathVariable("resortId") String resortId) {
    return service.resortRoomList(resortId);
  }
  
  @GetMapping("resort/{resortId}/room/{roomNo}") 
  public Room getResortRoom(@PathVariable("resortId") String resortId, @PathVariable("roomNo") String roomNo) {
    return service.getResortRoom(resortId, roomNo);
  }
  
  @GetMapping("resort/{resortId}/bookings") 
  public List<Booking> resortBookingList(
      @PathVariable("resortId") String resortId, 
      @RequestParam(value="bookingDt", required=false) String bookingDt) {
    return service.resortBookingList(resortId, bookingDt);
  }
    
  @GetMapping("/bookings/{bookingDt}") 
  public List<Booking> bookingList(@PathVariable("bookingDt") String bookingDt) {
    return service.bookingList(bookingDt);
  }

}
