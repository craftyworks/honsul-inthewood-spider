package com.honsul.inthewood.core.model;

import com.honsul.inthewood.core.Item;

import lombok.Data;

@Data
public class Room implements Item {
  private String resortId;
  private String resortNm;
  private String roomNo;
  private String roomNm;
  private RoomType roomType;
  private String numberOfPeople;
  private String space;
  private String info;
  long price;
  long peakPrice;
}
