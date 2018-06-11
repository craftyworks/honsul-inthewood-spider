package com.honsul.inthewood.spider.collector.r001;

import java.util.List;

import com.honsul.inthewood.core.Parser;
import com.honsul.inthewood.core.annotation.ResortParser;
import com.honsul.inthewood.core.model.Resort;

@ResortParser(resortId="R001")
public class R001ResortParser implements Parser<Resort>{
  
  @Override
  public List<Resort> parse() {
    return null;
  }
}
