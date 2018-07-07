package com.honsul.inthewood.bot.slack.message;

import java.util.Arrays;
import java.util.Map;

import com.honsul.inthewood.bot.slack.model.SlackAttachment;
import com.honsul.inthewood.bot.slack.model.SlackField;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage.SlackMessageBuilder;

public class BookingNotificationMessage {
  
  public static SlackMessage build(Map<String, String> booking) {
    String fallback = booking.get("resortNm") + " / " + booking.get("bookingDt") + " / " + booking.get("roomNm") + " (" + booking.get("roomTypeNm") + ")";
    String text = "*" + booking.get("roomNm") + "* (" + booking.get("roomTypeNm") + ")"; 
    
    SlackMessageBuilder builder = SlackMessage.builder();
    
    builder.username("SnapBot")
      //text("Room Available!")
      .text(fallback)
      .attachments(
          new SlackAttachment[] {
              SlackAttachment.builder()
                .fallback(fallback)
                .title(booking.get("resortNm"))
                .titleLink(booking.get("homepage"))
                .text(text)
                .markdownIn(Arrays.asList(new String[] {"text"}))
                .color("good")
                .fields(new SlackField[] {
                    SlackField.of("", booking.get("bookingDtTxt"), false),
                    SlackField.of("Room", booking.get("occupancy") + " 명"),
                    SlackField.of("Price", booking.get("price") + " / " + booking.get("peakPrice") + "원")
                })
                .footer(booking.get("address"))
                .footerIcon("https://platform.slack-edge.com/img/default_application_icon.png")
                .build()
          }
      );
    return builder.build();
  }  
  
}
