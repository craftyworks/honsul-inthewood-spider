package com.honsul.inthewood.core.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;

public abstract class JsoupRoomParser implements Parser<Room> {
  
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
  
  public abstract List<Room> extract(Document doc);

  @Override
  public List<Room> parse() {
    
    List<Room> roomList = new ArrayList<>();
    
    try {
     for(Document doc : documents()) {
       roomList.addAll(extract(doc));
     }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
    
    if(logger.isDebugEnabled()) {
      logger.debug("parsed Room List count : {}", roomList.size());
      if(roomList.size() > 0) {
        logger.debug("parsed Room[0] : {}", roomList.get(0));
        logger.debug("parsed Room[{}] : {}", roomList.size()-1, roomList.get(roomList.size()-1));
      }      
    }
    
    return roomList;
  }
  
  
  protected abstract RoomType getRoomType(String roomNm);
    
}
