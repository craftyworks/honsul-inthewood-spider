package com.honsul.inthewood.core;

import java.util.List;

public interface Parser<T extends Item> {
  public List<T> parse();
}
