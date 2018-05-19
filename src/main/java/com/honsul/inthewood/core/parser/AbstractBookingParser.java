package com.honsul.inthewood.core.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.model.Booking;

public abstract class AbstractBookingParser implements Parser<Booking> {
  
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private String pageURL;
  
  private Map<String, String> headers = new HashMap<>();
  
  public void header(String key, String value) {
    this.headers.put(key, value);
  }
  
  public AbstractBookingParser() {    
  }
  
  public AbstractBookingParser(String url) {
    this.pageURL = url;
  }
  
  protected Document thisMonth() {
    try {
      return Jsoup.connect(pageURL).headers(headers).get();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  protected abstract Document nextMonth(Document doc);
  
  public abstract List<Booking> extract(Document doc);
  
  public List<Booking> parse() {
    
    List<Booking> bookingList = new ArrayList<>();
    
    Document doc = thisMonth();
    
    bookingList.addAll(extract(doc));
    
    bookingList.addAll(extract(nextMonth(doc)));

    logger.debug("parsed Booking List count : {}", bookingList.size());
    
    return bookingList;
  }

}
