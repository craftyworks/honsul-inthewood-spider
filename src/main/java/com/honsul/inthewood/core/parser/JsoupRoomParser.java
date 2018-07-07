package com.honsul.inthewood.core.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.annotation.RoomParser;
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
  
  protected List<Room> extracts(List<Document> docs) {
    List<Room> roomList = new ArrayList<>();
    for (Document doc : docs) {
      if (logger.isDebugEnabled()) {
        logger.debug("extracting document : {}", doc.location());
      }
      roomList.addAll(extract(doc));
    }
    return roomList;
  }

  @Override
  public List<Room> parse() {
    try {
      List<Document> docs = documents();
      logger.debug("collected document count : {}", docs.size());
      
      List<Room> roomList  = extracts(docs);

      if(logger.isDebugEnabled()) {
        logger.debug("parsed Room List count : {}", roomList.size());

        if(roomList.size() > 0) {
          logger.debug("parsed Room[0] : {}", roomList.get(0));
          logger.debug("parsed Room[{}] : {}", roomList.size()-1, roomList.get(roomList.size()-1));
        }      
      }
      
      return roomList;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected abstract RoomType getRoomType(String roomNm);

  public abstract List<Room> extract(Document doc);
  
  @Override
  public boolean accept(String resortId) {
    RoomParser annotation = this.getClass().getAnnotation(RoomParser.class);
    return resortId.equals(annotation.resortId());
  }  
}
