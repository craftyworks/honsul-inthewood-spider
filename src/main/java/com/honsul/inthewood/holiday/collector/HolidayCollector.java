package com.honsul.inthewood.holiday.collector;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honsul.inthewood.holiday.dao.HolidayDao;

@Service
public class HolidayCollector {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  HolidayDao dao;
  
  @Autowired
  HolidayParser holidayParser;
  
  /**
   * 공휴일 정보 집계.
   */
  public void collectHolidays() {
    //1. 휴일 정보 초기화
    dao.clearCalendarHoliday();
    
    //2. 공휴일 정보 반영
    List<String> offdays = holidayParser.parse();
    for(String offday : offdays) {
      dao.updateCalendarOffday(offday);      
    }
    
    //3. 연휴정보 설정
    List<Map> holidays = dao.selectHolidayList();
    for(Map holiday : holidays) {
      dao.updateCalendarHoliday(holiday);
    }
  }
}
