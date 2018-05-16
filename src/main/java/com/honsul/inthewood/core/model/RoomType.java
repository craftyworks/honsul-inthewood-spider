package com.honsul.inthewood.core.model;

public enum RoomType {
  HUT("HUT", "숲속의집"),
  CONDO("CONDO", "휴양관");
  
  private String name;

  private String code;
  
  public String getName() {
    return name;
  }
  
  public String getCode() {
    return code;
  }
  
  RoomType(String code, String name) {
      this.code = code;
      this.name = name;
  }
  
  public static RoomType getRoomType(String name) {
    for(RoomType r : RoomType.values()) {
      if(r.getName().equals(name)) {
        return r;
      }
    }
    return RoomType.CONDO;
  }
}
