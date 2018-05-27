package com.honsul.inthewood.holiday.collector;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

public class HolidayParserTest {
  static HolidayParser parser;
  
  @BeforeClass
  public static void setup() {
   parser = new HolidayParser();
  }
  
  @Test
  public void testParse() {
    List<String> holidays = parser.parse();
    
    assertTrue(!CollectionUtils.isEmpty(holidays));
  }

}
