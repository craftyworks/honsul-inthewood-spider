package com.honsul.inthewood.postman;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.HonsulInTheWoodApplication;
import com.honsul.inthewood.core.model.Resort;
import com.honsul.inthewood.core.model.Subscriber;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class PostmanServiceTest {

  @Autowired
  PostmanService postman;
  
  @Test
  public void testPublishBookingChanges() {
    Resort resort = new Resort();
    resort.setResortId("R014");
    
    postman.publishBookingChangesSync(resort);
  }
  
  @Test
  public void testPublishNotification() {
    List<Subscriber> subscribers = new ArrayList<>();
    Subscriber sub = new Subscriber();
    sub.setSubscriberId("honsul-holiday");
    subscribers.add(sub);
    
    Map<String, String> booking = new HashMap<>();
    
    booking.put("resortId", "R001");
    booking.put("resortNm", "좌구산자연휴양림");
    booking.put("address",  "충청북도 충주시 노은면 우성1길 191");
    booking.put("homepage", "http://honsul.kr");
    booking.put("bookingDt", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 토요일" );
    booking.put("roomNm", "갈음달");
    booking.put("roomTypeNm",  "숲속의집");
    booking.put("price", "100,000");
    booking.put("peakPrice", "120,000");
    booking.put("occupancy",  "4");
    postman.publishNotification(subscribers, booking);
  }

}
