package com.honsul.inthewood.bot.slack.core;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.HonsulInTheWoodApplication;
import com.palantir.roboslack.webhook.api.model.WebHookToken;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class SlackConfigTest {
  
  @Autowired
  SlackConfig slackConfig;
  
  @Value("${slack.bot.client-id}")
  private String clientId;
  
  @Value("${slack.bot.client-secret}")
  private String clientSecret;
  
  @Value("${slack.channels.honsul-holiday}")
  private String honsulHolidayChannel;
    
  @Test
  public void testGetBot() {

    assertThat(slackConfig.getBot(), is(not(nullValue())));
    
    assertThat(slackConfig.getBot().getClientId(), equalTo(clientId));
    
    assertThat(slackConfig.getBot().getClientSecret(), equalTo(clientSecret));
  }

  @Test
  public void testGetWebhookURL() {
    assertThat(slackConfig.getWebhookURL("honsul-holiday"), is(honsulHolidayChannel));
  }
  
  @Test
  public void testGetWebhookToken() {
    assertThat(slackConfig.getWebhookToken("honsul-holiday"), is(WebHookToken.fromString(honsulHolidayChannel)));
  }  
}
