package com.honsul.inthewood.core.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.annotation.BookingParser;
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
  
  protected Document thisMonth() throws IOException {
    return Jsoup.connect(pageURL).headers(headers).get();
  }
  
  protected abstract Document nextMonth(Document doc) throws IOException;
  
  public abstract List<Booking> extract(Document doc);
  
  protected List<Booking> extractCustom() throws IOException {
    return Collections.emptyList();
  }
  
  public List<Booking> parse() {
    
    List<Booking> bookingList = new ArrayList<>();
    
    try {
      Document doc = thisMonth();
  
      if(doc != null) {
        bookingList.addAll(extract(doc));
        
        Document nextMonth = nextMonth(doc);
        if(nextMonth != null) {
          bookingList.addAll(extract(nextMonth));
        }
      }
      
      bookingList.addAll(extractCustom());
      
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
    
    if(logger.isDebugEnabled()) {
      logger.debug("parsed Booking List count : {} [{}]", bookingList.size(), bookingList.get(0));
    }
    
    return bookingList;
  }
  
  @Override
  public boolean accept(String resortId) {
    BookingParser annotation = this.getClass().getAnnotation(BookingParser.class);
    return resortId.equals(annotation.resortId());
  }
}
