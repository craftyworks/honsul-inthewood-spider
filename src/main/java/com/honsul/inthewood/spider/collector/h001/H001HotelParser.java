package com.honsul.inthewood.spider.collector.h001;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.annotation.HotelParser;
import com.honsul.inthewood.core.model.Hotel;

@HotelParser(hotelId="H001")
public class H001HotelParser implements Parser<Hotel>{
  
  @Override
  public List<Hotel> parse() {
    return null;
  }
  
  public static void main(String[] args) throws IOException {

    Document doc = Jsoup.connect("https://terms.naver.com/entry.nhn?docId=1999034&amp;amp;&cid=42856&categoryId=42856").get();
    String hotelNm = doc.select("h2.headword").first().text();
    String homePage = doc.select("div#size_ct p>a").first().attr("href");
    
    System.out.println(hotelNm + ", " + homePage);   
  }
}
