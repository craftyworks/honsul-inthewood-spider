package com.honsul.inthewood.spider;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.honsul.inthewood.bot.slack.SlackBot;
import com.honsul.inthewood.core.model.Resort;
import com.honsul.inthewood.core.model.Subscriber;
import com.honsul.inthewood.core.util.TextUtils;
import com.honsul.inthewood.spider.dao.PublisherDao;
import com.palantir.roboslack.api.MessageRequest;
import com.palantir.roboslack.api.attachments.Attachment;
import com.palantir.roboslack.api.attachments.components.Color;
import com.palantir.roboslack.api.attachments.components.Field;
import com.palantir.roboslack.api.attachments.components.Footer;
import com.palantir.roboslack.api.attachments.components.Title;
import com.palantir.roboslack.api.markdown.SlackMarkdown;

@Service
public class PostmanService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  PublisherDao dao;

  @Autowired
  SlackBot slackBot;
  
  /**
   * 휴양림 예약변경현황 알림 발송
   */
  @Async
  public void publishBookingChanges(Resort resort) {
    for(Map booking : dao.selectNewEntryBookings(resort)) {
      List<Subscriber> subscribers = dao.selectBookingSubscriber(booking);
      if(!CollectionUtils.isEmpty(subscribers)) {
        publishNotification(subscribers, booking);
      }
    }
  }
  
  public void publishBookingChangesSync(Resort resort) {
    publishBookingChanges(resort);
  }

  public void publishNotification(List<Subscriber> subscribers, Map booking) {
    logger.info("booking notifications");
    for(Subscriber target : subscribers) {
      logger.info("sending {} : {}", target.getSubscriberId(), booking);
      
      slackBot.sendMessage(target.getSubscriberId(), buildMessage(booking));
    }
  }

  private MessageRequest buildMessage(Map<String, String> booking) {
    MessageRequest.Builder builder = MessageRequest.builder()
        .username("SnapBot")
        .text("Room Available!");
      
    String fallback = booking.get("resortNm") + " " + booking.get("bookingDt") + " " + booking.get("roomNm") + " (" + booking.get("roomTypeNm") + ")";
    Title title = Title.builder().text(booking.get("resortNm")).link(TextUtils.toURL(booking.get("homepage"))).build();
    String text = SlackMarkdown.BOLD.decorate(booking.get("roomNm")) + " (" + booking.get("roomTypeNm") + ")"; 
        
      builder.addAttachments(Attachment.builder()
          .fallback(fallback)
          .title(title)
          .text(text)
          .color(Color.good())
          .addFields(Field.builder().title("").value(booking.get("bookingDt")).isShort(false).build())
          .addFields(Field.of("Room", booking.get("occupancy") + " 명"), Field.of("Price", booking.get("price") + " / " + booking.get("peakPrice") + "원"))
          .footer(
              Footer.builder()
                .text(booking.get("address"))
                .icon(TextUtils.toURL("https://platform.slack-edge.com/img/default_application_icon.png"))
                .build()
           )
          .build());
    return builder.build();
  }
  
}
