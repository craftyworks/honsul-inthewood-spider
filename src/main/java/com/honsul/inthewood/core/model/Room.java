package com.honsul.inthewood.core.model;

import java.time.LocalDate;
import java.util.Objects;

import lombok.Data;

@Data
public class Room {
  private String resortId;
  private String resortNm;
  private String roomNm;
  private RoomType roomType;
  private String numberOfPeople;
  private String space;
  private String info;
  long price;
  long peakPrice;
  LocalDate insertDt;
  LocalDate updateDt;
  
  @Override
  public boolean equals(Object obj) {
    if(obj == this) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Room room = (Room) obj;
    return Objects.equals(resortId, room.getResortId()) && Objects.equals(roomNm, room.getRoomNm());
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(resortId, roomNm);
  }  
}
