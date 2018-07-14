package com.honsul.inthewood.bot.slack.message;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.domain.SlackSubscription;

public class SlackSubscriptionListMessageTest {
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
        .bookingDt("holiday")
        .homepage("http://honsul.kr")
        .resortNm("테스트휴양림")
        .subscriptionId("S001")
        .build());
    
    SlackMessage message = SlackSubscriptionListMessage.build(subscriptions);
    
    assertThat(json.write(message)).hasJsonPathArrayValue("$.attachments");
  }

}
