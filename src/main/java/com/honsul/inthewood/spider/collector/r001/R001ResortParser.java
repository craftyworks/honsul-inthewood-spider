package com.honsul.inthewood.spider.collector.r001;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.annotation.ResortParser;
import com.honsul.inthewood.core.model.Resort;

@ResortParser(resortId="R001")
public class R001ResortParser implements Parser<Resort>{
  
  @Override
  public List<Resort> parse() {
    return null;
  }
  
  public static void main(String[] args) throws IOException {

    Document doc = Jsoup.connect("https://terms.naver.com/entry.nhn?docId=1999034&amp;amp;&cid=42856&categoryId=42856").get();
    String resortNm = doc.select("h2.headword").first().text();
    String homePage = doc.select("div#size_ct p>a").first().attr("href");
    
    System.out.println(resortNm + ", " + homePage);   
  }
}
