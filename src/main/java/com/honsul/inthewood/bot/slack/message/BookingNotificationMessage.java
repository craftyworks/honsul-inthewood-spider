package com.honsul.inthewood.bot.slack.message;

import java.util.Map;

import com.honsul.inthewood.bot.slack.model.SlackAttachment;
import com.honsul.inthewood.bot.slack.model.SlackField;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessage.SlackMessageBuilder;
import com.palantir.roboslack.api.markdown.SlackMarkdown;

public class BookingNotificationMessage {
  
  public static SlackMessage build(Map<String, String> booking) {
    String fallback = booking.get("resortNm") + " " + booking.get("bookingDt") + " " + booking.get("roomNm") + " (" + booking.get("roomTypeNm") + ")";
    String text = SlackMarkdown.BOLD.decorate(booking.get("roomNm")) + " (" + booking.get("roomTypeNm") + ")"; 
    
    SlackMessageBuilder builder = SlackMessage.builder();
    
    builder.username("SnapBot")
      .text("Room Available!")
      .attachments(
          new SlackAttachment[] {
              SlackAttachment.builder()
                .fallback(fallback)
                .title(booking.get("resortNm"))
                .titleLink(booking.get("homepage"))
                .text(text)
                .color("good")
                .fields(new SlackField[] {
                    SlackField.of("", booking.get("bookingDt"), false),
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
