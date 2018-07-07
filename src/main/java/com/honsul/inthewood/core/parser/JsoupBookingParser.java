package com.honsul.inthewood.core.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;

public abstract class JsoupBookingParser implements Parser<Booking> {
  
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  protected Document document() throws IOException {
    return null;
  }
  
  protected List<Document> documents() throws IOException {
    List<Document> documentList = new ArrayList<>();
    Document doc = document();
    if(doc != null) {
      documentList.add(doc);
    }
    return documentList;
  }
  
  public abstract List<Booking> extract(Document doc);
  
  public List<Booking> parse() {
    
    List<Booking> bookingList = new ArrayList<>();
    
    try {
     List<Document> docs = documents();
     logger.debug("collected document count : {}", docs.size());
     for(Document doc : docs) {
       if (logger.isDebugEnabled()) {
         logger.debug("extracting document : {}", doc.location());
       }       
       bookingList.addAll(extract(doc));
     }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
    
    if(logger.isDebugEnabled()) {
      logger.debug("parsed Booking List count : {}", bookingList.size());
      if(bookingList.size() > 0) {
        logger.debug("parsed Booking[0] : {}", bookingList.get(0));
        logger.debug("parsed Booking[{}] : {}", bookingList.size()-1, bookingList.get(bookingList.size()-1));
      }
    }
    
    return bookingList;
  }

  @Override
  public boolean accept(String resortId) {
    BookingParser annotation = this.getClass().getAnnotation(BookingParser.class);
    return resortId.equals(annotation.resortId());
  }
  
}
