package com.honsul.inthewood.bot.slack.event;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.eventbus.EventBus;
import com.honsul.inthewood.HonsulInTheWoodApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class SlackEventConfigTest {
  
  private static final Logger logger = LoggerFactory.getLogger(SlackEventConfigTest.class);

  @Autowired
  EventBus eventBus;
  
  @Autowired 
  List<EventBusListener> eventBusListeners;
  
  @Test
  public void testEventBus() {
    assertThat(eventBus, is(notNullValue()));
    
    assertThat(eventBusListeners, is(notNullValue()));
    
    assertThat(eventBusListeners.size(), greaterThan(0));

  }
}
