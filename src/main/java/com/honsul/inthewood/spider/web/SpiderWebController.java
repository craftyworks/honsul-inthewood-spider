package com.honsul.inthewood.spider.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsul.inthewood.core.model.Resort;
import com.honsul.inthewood.spider.collector.SpiderCollector;

@RestController
@RequestMapping("/")
public class SpiderWebController {

  @Autowired
  private SpiderCollector collector;
  
  @GetMapping("collect/{resortId}") 
  public void collect(@PathVariable("resortId") String resortId) {
    Resort resort = new Resort();
    resort.setResortId(resortId);
    collector.collect(resort);
  }
  
  @GetMapping("collect/booking/{resortId}") 
  public void collectBooking(@PathVariable("resortId") String resortId) {
    Resort resort = new Resort();
    resort.setResortId(resortId);
    collector.collectBooking(resort);
  }
  
  @GetMapping("collect/room/{resortId}") 
  public void collectRoom(@PathVariable("resortId") String resortId) {
    Resort resort = new Resort();
    resort.setResortId(resortId);
    collector.collectRoom(resort);
  }
  

}
