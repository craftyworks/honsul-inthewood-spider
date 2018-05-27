package com.honsul.inthewood.holiday.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class HolidayScheduller {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  HolidayCollector collector;
  
  @Autowired
  Environment env;
  
  
}
