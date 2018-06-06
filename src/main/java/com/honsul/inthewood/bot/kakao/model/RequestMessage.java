package com.honsul.inthewood.bot.kakao.model;

import lombok.Data;

@Data
public class RequestMessage {

  private String userKey;
  private String type;
  private String content;
  
}
