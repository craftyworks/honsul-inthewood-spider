package com.honsul.inthewood.core.model;

import com.honsul.inthewood.core.Item;

import lombok.Data;

@Data
public class Room implements Item {
  private String hotelId;
  private String roomNo;
  private String roomNm;
  private String roomType;
  private String roomTypeNm;
  private String occupancy;
  private String space;
  private String info;
  long price;
  long peakPrice;
}
