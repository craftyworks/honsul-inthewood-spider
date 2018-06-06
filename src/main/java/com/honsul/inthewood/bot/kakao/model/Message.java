package com.honsul.inthewood.bot.kakao.model;

import lombok.Data;

@Data
public class Message {
  String text;
  Photo photo;
  MessageButton message_button;
}