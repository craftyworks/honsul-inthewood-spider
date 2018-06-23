package com.honsul.inthewood.bot.slack.web;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/bot/slack/hugo")
public class HugoSlackBotOAuth2Handler {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  private final RestTemplate restTemplate = new RestTemplate();
  
  @GetMapping("/oauth")
  public String oauth(@RequestParam("code") String code, @RequestParam("state") String state) throws IOException {
      UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://slack.com/api/oauth.access")
          .queryParam("code", code)
          .queryParam("client_id", "5002703614.380331396292")
          .queryParam("client_secret", "0676487d4a0d4e00b50ec35b8a202a23");
          
      logger.debug("request uri : {}", builder.toUriString());
      ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);
      HashMap<String, Object> jsonMap = new ObjectMapper().readValue(response.getBody(), HashMap.class);
      logger.debug("message response : {}, {}, {}, {}", response.getStatusCode(), jsonMap.get("ok"), jsonMap.get("ok").getClass(), jsonMap);
      
      if((boolean) jsonMap.get("ok")) {
        String accessToken = (String) jsonMap.get("access_token");
        String url = UriComponentsBuilder.fromHttpUrl("https://slack.com/api/users.identity").queryParam("token", accessToken).toUriString();
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        logger.debug("message response : {}, {}", response.getStatusCode(), resp.getBody());
      }
      
    return "bot/slack/result";
  }
  
  @GetMapping("/install")
  public String install(Model model) {
    System.out.println(">>>>>>>>>>>>>");
    model.addAttribute("name", "SpringBlog from Millky");
    return "bot/slack/install";
  }
}
