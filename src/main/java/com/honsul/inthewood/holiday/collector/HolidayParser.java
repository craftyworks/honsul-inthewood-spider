package com.honsul.inthewood.holiday.collector;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.honsul.inthewood.core.Parser;

@Component
public class HolidayParser implements Parser<String>{
  
  private static final String API_KEY = "PEDG2HE+FFiDHx80jZdRvN68wNc+Mi4LewCCteFGCQEBrgowIzj4BgF9VX8EEoQ51tBEDEsCDs3zJS13kJUBkA==";
  private static final String ENTRY_POINT_URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo";
 
  
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private Document getHolidays(int year, int month) throws IOException {
    logger.debug("getHolidays({}, {})", year, month);
    
    Document doc = Jsoup.connect(ENTRY_POINT_URL)
        .data("solYear", ""+year, "solMonth", StringUtils.leftPad(""+month, 2, "0"),"ServiceKey", API_KEY)
        .parser(org.jsoup.parser.Parser.xmlParser())
        .get();
    
    return doc;
  }
  
  private List<String> extract(Document doc) {
    List<String> holidays = new ArrayList<>();

    for(Element item : doc.select("items>item")) {
      holidays.add(item.select("locdate").text());
    }
    
    return holidays;
  }
  
  @Override
  public List<String> parse() {
    List<String> holidays = new ArrayList<>();
    
    try {
      for(int year = LocalDate.now().getYear(); year <= LocalDate.now().getYear() + 1; year++) {
        for(int month = 1; month <= 12; month++) {
          Document doc = getHolidays(year, month);
          
          holidays.addAll(extract(doc));
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    logger.debug("Holidays : {}", holidays);
    
    return holidays;
  }


  
}
