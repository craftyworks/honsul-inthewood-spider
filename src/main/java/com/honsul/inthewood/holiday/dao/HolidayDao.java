package com.honsul.inthewood.holiday.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HolidayDao {

  void updateCalendarOffday(String holiday);
  
  void clearCalendarHoliday();
  
  List<Map> selectHolidayList();

  void updateCalendarHoliday(Map holiday);

}
