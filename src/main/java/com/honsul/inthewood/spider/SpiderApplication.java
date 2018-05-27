package com.honsul.inthewood.spider;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.honsul.inthewood")
@ComponentScan("com.honsul.inthewood")
public class SpiderApplication {
  private final static Logger logger = LoggerFactory.getLogger(SpiderApplication.class);
  
	public static void main(String[] args) {
	  ConfigurableApplicationContext context = new SpringApplicationBuilder(SpiderApplication.class).properties( 
	      "spring.config.location=classpath:config/application.yml,classpath:config/application-datasource.yml" 
	  ) .run(args);
	  
	  logger.info("phantomjs library path : {}", context.getEnvironment().getProperty("phantomjs.binary.path"));
	  logger.info("chrome driver path : {}", context.getEnvironment().getProperty("webdriver.chrome.driver"));
	  
	  System.setProperty("phantomjs.binary.path", context.getEnvironment().getProperty("phantomjs.binary.path"));
	  System.setProperty("webdriver.chrome.driver", context.getEnvironment().getProperty("webdriver.chrome.driver"));
	}
  
}
