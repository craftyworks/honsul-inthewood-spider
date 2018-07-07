package com.honsul.inthewood.core.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsul.inthewood.core.exception.LoginFailedException;
import com.honsul.inthewood.core.model.RoomType;

public class NationalResortUtils {
  private final static String DOMAIN_URL = "http://www.huyang.go.kr";
  private final static String MAIN_URL = DOMAIN_URL + "/main.action";
  private final static String LOGIN_URL = DOMAIN_URL + "/member/memberLogin.action";
  private final static String BOOKING_URL = DOMAIN_URL + "/reservation/reservationMonthSearchList.action";
  
  private static final Logger logger = LoggerFactory.getLogger(NationalResortUtils.class);

  public static Map<String, String> loginCookies() {
    
    try {
      
      Response resp = Jsoup.connect(LOGIN_URL)
          .data("dprtmId", "")
          .data("RSA", "")
          .data("referer", MAIN_URL) 
          .data("mmberId", "wallaroo") 
          .data("mmberPassword", "73cf4e30ea4b1bf51f85e820d667b5e142f61a858ceab1ba75287a86c90e12a2")
          .header("Referer", MAIN_URL)
          .followRedirects(true)
          .method(Method.POST)
          .execute();

      logger.debug("status : {} {}", resp.statusCode(), resp.statusMessage());
      logger.debug("headers : {}", resp.headers());
      logger.debug("cookies : {}", resp.cookies());
      
      Document doc = resp.parse();
      if(!doc.toString().contains("로그아웃")) {
        throw new LoginFailedException("로그인 계정 확인 필요.");
      }
      return resp.cookies();
      
    } catch (IOException e) {
      throw new LoginFailedException("국립휴양림 관리소 로그인 실패", e);
    }
  }
  
  public static Document bookinDocument(String departmentId) throws IOException {
    return Jsoup.connect(BOOKING_URL)
      .header("Referer", MAIN_URL)
      .cookies(loginCookies())
      .data("paramDprtmId", departmentId)
      .data("fcltMdcls", "03001,03002,03003")
      .get();
  }
  
  public static Document bookinDocument(String departmentId, Map<String, String> cookies) throws IOException {
    return Jsoup.connect(BOOKING_URL)
      .header("Referer", MAIN_URL)
      .cookies(cookies)
      .data("paramDprtmId", departmentId)
      .data("fcltMdcls", "03001,03002,03003")
      .get();
  }
  
  public static long price(RoomType roomType, String numberOfPeople) {
    Integer key = Objects.hash(roomType, numberOfPeople);
    //logger.debug("price({}, {}) => {}", roomType, numberOfPeople, PRICE_MAP.get(key));
    return PRICE_MAP.get(key)[0];
  }
  
  public static long peakPrice(RoomType roomType, String numberOfPeople) {
    Integer key = Objects.hash(roomType, numberOfPeople);
    return PRICE_MAP.get(key)[1];
  }
  
  private final static Map<Integer, long[]> PRICE_MAP = new HashMap<>();
  static {
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "3"), new long[] {35000,58000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "4"), new long[] {37000,67000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "5"), new long[] {46000,85000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "6"), new long[] {58000,104000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "7"), new long[] {58000,104000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "8"), new long[] {77000,134000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "9"), new long[] {77000,134000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "10"), new long[] {91000,151000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "11"), new long[] {91000,151000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "12"), new long[] {104000,184000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "13"), new long[] {104000,184000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "14"), new long[] {104000,184000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "15"), new long[] {104000,184000});
    PRICE_MAP.put(Objects.hash(RoomType.HUT, "16"), new long[] {104000,184000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "3"), new long[] {32000,53000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "4"), new long[] {39000,68000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "5"), new long[] {50000,91000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "6"), new long[] {67000,119000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "7"), new long[] {67000,119000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "8"), new long[] {85000,144000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "9"), new long[] {85000,144000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "10"), new long[] {100000,164000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "11"), new long[] {100000,164000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "12"), new long[] {107000,186000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "13"), new long[] {107000,186000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "14"), new long[] {107000,186000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "15"), new long[] {107000,186000});
    PRICE_MAP.put(Objects.hash(RoomType.CONDO, "16"), new long[] {107000,186000});
  }
}
