package com.honsul.inthewood.bot.slack.message;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.json.JacksonTester;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.domain.SlackSubscription;

public class SlackSubscriptionListMessageTest {
  
  private static final Logger logger = LoggerFactory.getLogger(SlackSubscriptionListMessageTest.class);

  private JacksonTester<SlackMessage> json;

  @Before
  public void setup() {
      ObjectMapper objectMapper = new ObjectMapper(); 
      JacksonTester.initFields(this, objectMapper);
  }
  
  @Test
  public void testBuild() throws Exception {
    List<SlackSubscription> subscriptions = new ArrayList<>();
    subscriptions.add(SlackSubscription.builder()
        .userId("U001")
        .userName("댐뽀리")
        .bookingDt("주말과 연휴")
        .homepage("http://honsul.kr")
        .address("충청북도 충주시 노은면 우성1길 191")
        .resortNm("전국 65개 휴양림")
        .subscriptionId("S001")
        .build());
    
    SlackMessage message = SlackSubscriptionListMessage.build(subscriptions);
    logger.debug("json: {}", json.write(message));
    assertThat(json.write(message)).hasJsonPathArrayValue("$.attachments");
  }

}
