package com.honsul.inthewood.postman;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.model.Resort;
import com.honsul.inthewood.core.model.Subscriber;
import com.honsul.inthewood.spider.SpiderApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {SpiderApplication.class})
public class PostmanServiceTest {

  @Autowired
  PostmanService postman;
  
  @Test
  public void testPublishBookingChanges() {
    Resort resort = new Resort();
    resort.setResortId("R014");
    
    postman.publishBookingChangesSync(resort);
  }
  
  //@Test
  public void test() {
    List<Subscriber> subscribers = new ArrayList<>();
    Subscriber sub = new Subscriber();
    sub.setSubscriberId("honsul-holiday");
    subscribers.add(sub);
    
    Booking booking = new Booking();
    booking.setBookingDt(LocalDate.now());
    booking.setResortId("R001");
    booking.setResortNm("좌구산자연휴양림");
    booking.setRoomNm("갈음달");
    postman.publishNotification(subscribers, booking);
  }

}
