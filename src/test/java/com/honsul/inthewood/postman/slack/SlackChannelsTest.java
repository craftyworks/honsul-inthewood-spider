package com.honsul.inthewood.postman.slack;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.HonsulInTheWoodApplication;
import com.honsul.inthewood.bot.slack.SlackChannels;
import com.palantir.roboslack.webhook.api.model.WebHookToken;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class SlackChannelsTest {

  private static String DUMMY_URL = "https://hooks.slack.com/services/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";
      
  @Autowired
  SlackChannels slackChannels;
   
  @Test
  public void testGetWebhookURL() {
    assertThat(slackChannels.getWebhookURL("honsul-holiday"), is(DUMMY_URL));
    
  }
  @Test
  public void testGetWebhookToken() {
    assertThat(slackChannels.getWebhookToken("honsul-holiday"), is(WebHookToken.fromString(DUMMY_URL)));
  }

}
