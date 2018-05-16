package com.honsul.inthewood.core.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.honsul.inthewood.core.model.Room;

public abstract class JsoupRoomParser extends AbstractRoomParser {
  
  protected String pageURL;
  
  public JsoupRoomParser(String url) {
    this.pageURL = url;
  }
  
  public Document getPage() {
    try {
      return Jsoup.connect(pageURL).get();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public List<Room> parse() {
    
    List<Room> roomList = new ArrayList<>();
    
    roomList.addAll(extract(getPage()));
    
    return roomList;
  }
  
  public abstract List<Room> extract(Document doc);
}
