package com.honsul.inthewood.holiday.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsul.inthewood.holiday.collector.HolidayCollector;

@RestController
@RequestMapping("/holidays")
public class HolidayWebController {

  @Autowired
  private HolidayCollector collector;
  
  @GetMapping("collect") 
  public void collectHolidays() {
    collector.collectHolidays();
  }
}
