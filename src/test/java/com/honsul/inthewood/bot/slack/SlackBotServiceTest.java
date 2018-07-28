package com.honsul.inthewood.bot.slack;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.honsul.inthewood.HonsulInTheWoodApplication;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand.CallbackId;
import com.honsul.inthewood.bot.slack.model.SlackDialogOptionHolder;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class SlackBotServiceTest {

  private static final Logger logger = LoggerFactory.getLogger(SlackBotServiceTest.class);

  private JacksonTester<SlackDialogOptionHolder> json;

  @Before
  public void setup() {
      ObjectMapper objectMapper = new ObjectMapper(); 
      JacksonTester.initFields(this, objectMapper);
  }
  
  @Autowired
  SlackBotService service;
  
  @Test
  public void testGetSlackBotAccessToken() throws Exception {
    String token = service.getSlackBotAccessToken("U0502LPJC");
    assertThat(token).contains("xoxb");
  }

  @Test
  public void testGetSlackUserAccessToken() throws Exception {
    String token = service.getSlackUserAccessToken("U0502LPJC");
    assertThat(token).contains("xoxp");
  }

  @Test
  public void testSelectSlackSubscription() throws Exception {
    SlackSlashCommand slashCommand = mock(SlackSlashCommand.class);
    when(slashCommand.getUserId()).thenReturn("U0502LPJC");
    when(slashCommand.getChannelId()).thenReturn("GB1NY12UX");
    
    assertThat(service.selectSlackSubscription(slashCommand.getUserId(), slashCommand.getChannelId())).isNotEmpty();
  }

  @Test
    public void testLoadDialogOptions() throws Exception {
      SlackActionCommand command = new SlackActionCommand();
      command.setCallbackId(CallbackId.add_subscription);
      command.setName("resort_nm");
      SlackDialogOptionHolder options = service.loadDialogOptions(command);
      logger.debug("json:{}", json.write(options));
      assertThat(options.getOptions()).isNull();
      assertThat(options.getOptionGroups()).isNotEmpty();
  
      command.setName("booking_dt");
      options = service.loadDialogOptions(command);
      logger.debug("json:{}", json.write(options));
      assertThat(options.getOptions()).isNotEmpty();
      
      command.setValue("2");
      options = service.loadDialogOptions(command);
      logger.debug("json:{}", json.write(options));
      assertThat(options.getOptions()).isEmpty();
      
      command.setValue("20180901");
      options = service.loadDialogOptions(command);
      logger.debug("json:{}", json.write(options));
      assertThat(options.getOptions()).isNotEmpty();
      
      command.setValue("2018-09-01");
      options = service.loadDialogOptions(command);
      logger.debug("json:{}", json.write(options));
      assertThat(options.getOptions()).isNotEmpty();
    }

  @Test
  public void testLoadResortDialogOptions() throws Exception {
    throw new RuntimeException("not yet implemented");
  }

  
}
