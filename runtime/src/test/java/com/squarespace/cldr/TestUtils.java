package com.squarespace.cldr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.squarespace.cldr.utils.Utils;


public class TestUtils {

  public static List<String> loadTestFile(Class<?> cls, String path) throws IOException {
    List<String> res = new ArrayList<>();
    String raw = Utils.loadResource(cls, path);
    for (String line : raw.split("\n")) {
      int index = line.indexOf('#');
      if (index != -1) {
        line = line.substring(0, index);
      }
      line = line.trim();
      if (line.isEmpty()) {
        continue;
      }
      
      res.add(line);
    }
    return res;
  }
  

}
