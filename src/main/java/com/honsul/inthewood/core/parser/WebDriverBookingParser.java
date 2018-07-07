package com.honsul.inthewood.core.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.util.WebDriverUtils;

public abstract class WebDriverBookingParser implements Parser<Booking> {
  
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  protected abstract Document thisMonth(WebDriver driver);
  
  protected abstract Document nextMonth(WebDriver driver);
  
  public abstract List<Booking> extract(Document doc);
  
  public List<Booking> parse() {
    
    WebDriver driver = WebDriverUtils.createDriver();
    
    List<Booking> bookingList = new ArrayList<>();
    
    bookingList.addAll(extract(thisMonth(driver)));
    
    bookingList.addAll(extract(nextMonth(driver)));

    logger.debug("parsed Booking List count : {}", bookingList.size());
    
    driver.quit();
    
    return bookingList;
  }

  @Override
  public boolean accept(String resortId) {
    BookingParser annotation = this.getClass().getAnnotation(BookingParser.class);
    return resortId.equals(annotation.resortId());
  }
}
