package com.honsul.inthewood.bot.slack;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackMessageResponse;
import com.honsul.inthewood.bot.slack.model.api.ImListResponse;

public class SlackWebClientTest {

  final static String BOT_TOKEN = "xoxb-5002703614-380571762386-bnDCJQiB31mC2quzycuFGLdn";
  final static String OAUTH_TOKEN = "xoxp-5002703614-5002703624-381604515607-062c87d26854062a500ee746f987f2e7";
  
  static SlackWebClient slackWebClient;
  
  @BeforeClass
  public static void before() {
    slackWebClient = new SlackWebClient();
  }
  
  @Test 
  public void testChatMeMessage() {
    //DB60RERLH
    //UB6GTNEBC 
    
    //"bot_id": "BB6GTNEAW",
    //"api_app_id": "AB69RBN8L"
      
    slackWebClient.chatPostMessage(SlackMessage.of(BOT_TOKEN, "DB60RERLH", "Direct Message 테스트1 by Bot Token"));
    
    slackWebClient.chatPostMessage(SlackMessage.of(BOT_TOKEN, "BB6GTNEAW", "Direct Message 테스트2 by Bot Token"));
    
    slackWebClient.chatPostMessage(SlackMessage.of(OAUTH_TOKEN, "DB60RERLH", "Direct Message 테스트3 by Bot Token"));
    
    slackWebClient.chatPostMessage(SlackMessage.of(OAUTH_TOKEN, "BB6GTNEAW", "Direct Message 테스트4 by Bot Token"));    
  }
  /**
   * BOT Token 으로 DM 이나 Public Channel 로 post message 가능.
   * OAuth TOken 으로 Private Channel post message 가능.
   */
  @Test
  public void testChatPostMessage() {
    //Direct Message
    SlackMessageResponse response = slackWebClient.chatPostMessage(SlackMessage.of(BOT_TOKEN, "@ddam40", "Direct Message 테스트 by Bot Token"));
    assertThat("Bot Token 으로 chat.postMessage", response.isOk(), is(true));
    
    response = slackWebClient.chatPostMessage(SlackMessage.of(OAUTH_TOKEN, "@ddam40", "Direct Message 테스트 by OAuth Token"));
    assertThat("OAuth Token 으로 chat.postMessage", response.isOk(), is(true));
    
    //Direct Message using USER_ID
    response = slackWebClient.chatPostMessage(SlackMessage.of(BOT_TOKEN, "U0502LPJC", "Direct Message 테스트 by Bot Token"));
    assertThat(response.isOk(), is(true));
    
    response = slackWebClient.chatPostMessage(SlackMessage.of(OAUTH_TOKEN, "U0502LPJC", "Direct Message 테스트 by OAuth Token"));
    assertThat(response.isOk(), is(true));
    
    //Public Channel
    response = slackWebClient.chatPostMessage(SlackMessage.of(BOT_TOKEN, "general", "Public Channel 테스트 by Bot Token"));
    assertThat(response.isOk(), is(true));
    
    response = slackWebClient.chatPostMessage(SlackMessage.of(OAUTH_TOKEN, "general", "Public Channel 테스트 by OAuth Token"));
    assertThat(response.isOk(), is(true));
    
    //Private Channel
    response = slackWebClient.chatPostMessage(SlackMessage.of(BOT_TOKEN, "gitlab", "Private Channel 테스트 by Bot Token"));
    assertThat(response.isOk(), is(false));
    
    response = slackWebClient.chatPostMessage(SlackMessage.of(OAUTH_TOKEN, "gitlab", "Private Channel 테스트 by User Token"));
    assertThat(response.isOk(), is(true)); 
    
  }
  
  @Test
  public void testChatDelete() {
    // public channel
    SlackMessageResponse response = slackWebClient.chatPostMessage(SlackMessage.of(BOT_TOKEN, "general", "메시지 삭제 테스트"));
    assertThat(response.isOk(), is(true));
    
    response = slackWebClient.chatDelete(SlackMessage.builder().token(BOT_TOKEN).channel(response.getChannel()).ts(response.getTs()).build());
    assertThat(response.isOk(), is(true)); 
    
    // Direct message
    response = slackWebClient.chatPostMessage(SlackMessage.of(OAUTH_TOKEN, "U0502LPJC", "메시지 삭제 테스트"));
    assertThat(response.isOk(), is(true));
    
    response = slackWebClient.chatDelete(SlackMessage.builder().token(OAUTH_TOKEN).channel(response.getChannel()).ts(response.getTs()).build());
    assertThat(response.isOk(), is(true));
    
    // Private 
    response = slackWebClient.chatPostMessage(SlackMessage.of(OAUTH_TOKEN, "gitlab", "메시지 삭제 테스트"));
    assertThat(response.isOk(), is(true));
    
    response = slackWebClient.chatDelete(SlackMessage.builder().token(OAUTH_TOKEN).channel(response.getChannel()).ts(response.getTs()).build());
    assertThat(response.isOk(), is(true));
    
  }
  
  @Test
  public void testChatUpdate() {
    // public channel
    SlackMessageResponse response = slackWebClient.chatPostMessage(SlackMessage.of(BOT_TOKEN, "general", "메시지 변경전"));
    assertThat(response.isOk(), is(true));
    
    SlackMessage updateMessage = SlackMessage.builder().token(BOT_TOKEN).channel(response.getChannel()).ts(response.getTs()).text("메시지 변경").build();
    response = slackWebClient.chatUpdate(updateMessage);
    assertThat(response.isOk(), is(true)); 
  }
  
  /**
   * Public Channel 만 리스팅
   */
  @Test
  public void testChannelsList() {
    Map<String, Object> response = slackWebClient.channelsList(BOT_TOKEN);
    assertThat(response.get("ok"), is(true));
    
    List<Map> channels = (List<Map>) response.get("channels");
    assertThat(channels.size(), greaterThan(0));
    
    response = slackWebClient.channelsList(OAUTH_TOKEN);
    assertThat(response.get("ok"), is(true));
    
    channels = (List<Map>) response.get("channels");
    assertThat(channels.size(), greaterThan(0));
    
  }
  
  @Test
  public void testImList() {
    // BOT 기준
    ImListResponse response = slackWebClient.imList(BOT_TOKEN);
    assertThat(response.isOk(), is(true));
    
    long count = response.getIms().stream().filter(el -> "U0502LPJC".equals(el.getUser())).count();
    assertThat(count, greaterThan(0L));
    
    // USER 기준
    response = slackWebClient.imList(OAUTH_TOKEN);
    assertThat(response.isOk(), is(true));
    count = response.getIms().stream().filter(el -> "UB6GTNEBC".equals(el.getUser())).count();
    assertThat(count, greaterThan(0L));
  }
  
  @Test
  public void testUsersInfo() {
    Map<String, Object> response = slackWebClient.usersInfo(BOT_TOKEN, "UB6GTNEBC");
    assertThat(response.get("ok"), is(true));
    
    response = slackWebClient.usersInfo(BOT_TOKEN, "U0502LPJC");
    assertThat(response.get("ok"), is(true));
  }
  
  /**
   * 사용자 인증은 OAuth Token 으로만 가능
   */
  @Test
  public void testUsersIdentity() {
    Map<String, Object> response = slackWebClient.usersIdentity(BOT_TOKEN);
    assertThat(response.get("ok"), is(false));
    
    response = slackWebClient.usersIdentity(OAUTH_TOKEN);
    assertThat(response.get("ok"), is(true));
  }
  

}
