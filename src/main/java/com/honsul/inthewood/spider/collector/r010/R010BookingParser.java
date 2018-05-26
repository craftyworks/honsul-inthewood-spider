package com.honsul.inthewood.spider.collector.r010;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.honsul.inthewood.core.SpiderContext;
import com.honsul.inthewood.core.annotation.BookingParser;
import com.honsul.inthewood.core.model.Booking;
import com.honsul.inthewood.core.parser.AbstractBookingParser;
import com.honsul.inthewood.core.util.TextUtils;

/**
 * 군위장곡자연휴양림 예약현황 파서.
 */
//FIXME
@BookingParser(resortId="R010")
public class R010BookingParser extends AbstractBookingParser {

  private static final String CONNECT_URL = "https://janggok.gunwi.go.kr:6449/new/reservation/reserve_status.html?todayed=";
  
  protected Document nextMonth(Document doc) throws IOException {
    LocalDate next = LocalDate.now().plusMonths(1);
    String url = CONNECT_URL + "&yy=" + next.getYear() + "&mm=" + next.getMonthValue();
    return Jsoup.connect(url).get();
    
  }
  
  public List<Booking> extract(Document doc) {
    List<Booking> bookingList = new ArrayList<>();

    String year = doc.selectFirst("#yy>option[selected]").val();
    String month = StringUtils.leftPad(doc.selectFirst("#mm>option[selected]").val(), 2, '0');
    
    for(Element row : doc.select("a[href^=javascript:reserve(]")) {
      String title = row.text();
      String roomTypeNm = TextUtils.substringBefore(title, "(");
      String roomNm = TextUtils.substringAfter(title, ")");
      String attr = row.attr("href");
      //javascript:reserve('56', '1', '1', '2018-05-20')
      Pattern p = Pattern.compile("javascript:reserve\\('[0-9]+', '[0-9]+', '([0-9]+)',");
      Matcher matcher = p.matcher(attr);
      if(matcher.find()) {
        String bookingDt = year + month + StringUtils.leftPad(matcher.group(1), 2, '0');
        Booking booking = new Booking();
        booking.setResortId(SpiderContext.getResortId());
        booking.setBookingDt(LocalDate.parse(bookingDt, DateTimeFormatter.ofPattern("yyyyMMdd")));
        booking.setRoomNo(roomNm);
        booking.setRoomNm(roomNm);
        if(LocalDate.now().isAfter(booking.getBookingDt())) {
          continue;
        }
        bookingList.add(booking);
      }
    }
    
    return bookingList;
  }
  private static void trustAllCertificate() throws Exception {
    TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
      public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
      public void checkClientTrusted(X509Certificate[] certs, String authType) {}
      public void checkServerTrusted(X509Certificate[] certs, String authType) {}
    }};

    SSLContext sc = SSLContext.getInstance("TLS");
    sc.init(null, trustAllCerts, new SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());    
  }
  
  public static void main(String[] args) throws Exception {
    trustAllCertificate();
    System.out.println(new Date(1525878000));
    System.out.println(LocalDate.now().atStartOfDay(ZoneOffset.ofHours(9)).toInstant().getEpochSecond() + ", " + System.currentTimeMillis());
    
    String url = CONNECT_URL + LocalDate.now().atStartOfDay(ZoneOffset.ofHours(9)).toInstant().getEpochSecond();
    System.out.println(Jsoup.connect(url).get());
  }
}
