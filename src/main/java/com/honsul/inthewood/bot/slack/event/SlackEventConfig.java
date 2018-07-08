package com.honsul.inthewood.bot.slack.event;

import java.util.List;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

@Configuration
public class SlackEventConfig { 
  private final static Logger logger = LoggerFactory.getLogger(SlackEventConfig.class);
  
  @Autowired 
  List<EventBusListener> eventBusListeners;
  
  @Bean
  public EventBus eventBus(SlackSlashCommandListener listener) {
    EventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(10));
    eventBusListeners.forEach(i -> eventBus.register(i));
    return eventBus;
  }
  
  @Bean 
  public EventBusListener deadEventListener() {
    return new EventBusListener() {
      @Subscribe
      public void handleDeadEvent(DeadEvent deadEvent) {
        logger.info("DeadEvent detected : {}", deadEvent);
      }
    };
  }

}
