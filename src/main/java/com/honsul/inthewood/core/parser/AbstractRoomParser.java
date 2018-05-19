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
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;

public abstract class AbstractRoomParser  implements Parser<Room> {
  
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private String pageURL;
  
  private Map<String, String> headers = new HashMap<>();
  
  public void header(String key, String value) {
    this.headers.put(key, value);
  }
  
  public AbstractRoomParser() {
    
  }
  
  public AbstractRoomParser(String url) {
    this.pageURL = url;
  }
  
  public Document getPage() {
    try {
      return Jsoup.connect(pageURL).headers(headers).get();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public List<Document> getPages() {
    List<Document> pages = new ArrayList<>();
    pages.add(getPage());
    return pages;
  }
  
  @Override
  public List<Room> parse() {
    return extract(getPages());
  }
  
  public List<Room> extract(List<Document> docs) {
    List<Room> roomList = new ArrayList<>();
    
    for(Document doc : docs) {
      roomList.addAll(extract(doc));
    }
    
    return roomList;
  }
  
  public abstract List<Room> extract(Document doc);
  
  protected abstract RoomType getRoomType(String roomNm);
    
}
