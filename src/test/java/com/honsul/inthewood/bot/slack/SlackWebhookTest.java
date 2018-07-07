package com.honsul.inthewood.bot.slack;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.HonsulInTheWoodApplication;
import com.honsul.inthewood.bot.slack.model.SlackMessageResponse;
import com.honsul.inthewood.bot.slack.model.domain.SlackUser;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class SlackWebhookTest {

  private static final Logger logger = LoggerFactory.getLogger(SlackWebhookTest.class);

  @Autowired
  private SlackWebhook slackWebHook;
  
  @Test
  public void testSendBookingNotificationMessage() throws Exception {
    Map<String, String> booking = new HashMap<>();
    
    booking.put("resortId", "R001");
    booking.put("resortNm", "테스트자연휴양림");
    booking.put("address",  "충청북도 충주시 노은면 우성1길 191");
    booking.put("homepage", "http://honsul.kr");
    booking.put("bookingDt", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    booking.put("bookingDtTxt", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 토요일" );
    booking.put("roomNm", "갈음달");
    booking.put("roomTypeNm",  "숲속의집");
    booking.put("price", "100,000");
    booking.put("peakPrice", "120,000");
    booking.put("occupancy",  "4");
    
    SlackUser user = SlackUser.builder()
        .accessToken("xoxp-5002703614-5002703624-381604515607-062c87d26854062a500ee746f987f2e7")
        .botAccessToken("xoxb-5002703614-380571762386-bnDCJQiB31mC2quzycuFGLdn")
        .botImChannel("DB60RERLH")
        .build();
    
    SlackMessageResponse response = slackWebHook.sendBookingNotificationMessage(user, booking);
    logger.debug("response : {}, {}, {}", response.getChannel(), response.getTs(), response.getMessage());
  }

}
