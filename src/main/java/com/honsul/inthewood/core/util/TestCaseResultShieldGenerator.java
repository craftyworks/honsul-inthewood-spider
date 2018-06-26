package com.honsul.inthewood.core.util;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.FileCopyUtils;

/**
 * 테스트 결과 리포트에서 결과를 추출하여 http://shields.io/ 서비스를 이용한 shilds 이미지를 생성함. 
 */
public class TestCaseResultShieldGenerator {

  public static void main(String[] args) throws IOException {
    File file = new File("target/site/surefire-report.html");
    Document doc = Jsoup.parse(file,"UTF-8");
    Elements tds = doc.select("div#contentBox > div.section > table.bodyTable > tbody > tr.b > td");
    int totalCase = Integer.valueOf(tds.get(0).text());
    int errors = Integer.valueOf(tds.get(1).text());
    int fails = Integer.valueOf(tds.get(2).text());
    String rate = tds.get(4).text() + "25";
    
    String url = String.format("https://img.shields.io/badge/%s-%s-%s.svg?style=flat-square", "JUnit_", String.format("total_%d,_errors_%d,_failures_%d", totalCase, errors, fails), "red");
    downloadFileURI(url, new File("target/site/test-result.svg"));    
  }
  
  private static void downloadFileURI(String uri, File outFile) throws IOException {
      try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
          HttpGet httpGet = new HttpGet(uri);

          try (CloseableHttpResponse response = httpClient.execute(httpGet)){
              
              FileCopyUtils.copy(response.getEntity().getContent(), FileUtils.openOutputStream(outFile));
              
          }
      } catch (IOException e) {
          throw e;
      }
  }  

}
