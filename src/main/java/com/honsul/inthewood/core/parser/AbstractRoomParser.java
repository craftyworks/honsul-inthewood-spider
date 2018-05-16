package com.honsul.inthewood.core.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.model.Room;
import com.honsul.inthewood.core.model.RoomType;

public abstract class AbstractRoomParser  implements Parser<Room> {
  
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    protected abstract RoomType getRoomType(String roomNm);

  }
