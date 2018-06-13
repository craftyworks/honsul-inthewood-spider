package com.honsul.inthewood.spider.collector;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.HonsulInTheWoodApplication;
import com.honsul.inthewood.core.model.Resort;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class SpiderCollectorTest {

  @Autowired
  SpiderCollector collector;
  
  @Test
  public void testCollect() {
    fail("Not yet implemented");
  }

  @Test
  public void testSelectAllResort() {
    List<Resort> list = collector.selectAllResort();
    assertThat(collector.selectAllResort().size(), is(not(0)));
  }

  @Test
  public void testCollectBooking() {
    fail("Not yet implemented");
  }

  @Test
  public void testCollectRoom() {
    fail("Not yet implemented");
  }

}
