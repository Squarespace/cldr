package com.squarespace.cldr.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;



public class Utils {

  private static final int COPY_BUFFER_SIZE = 8192;
  
  public static String loadResource(Class<?> cls, String path) throws IOException {
    try (InputStream stream = cls.getResourceAsStream(path)) {
      if (stream == null) {
        throw new IOException(path + " resource not found");
      }
      return readStream(stream);
    }
  }

  public static String readStream(InputStream stream) throws IOException {
    try (InputStreamReader input = new InputStreamReader(stream, "UTF-8");
        BufferedReader reader = new BufferedReader(input)) {
      return readToString(reader);
    }
  }
  
  public static String readToString(Reader reader) throws IOException {
    StringBuilder output = new StringBuilder();
    char[] temp = new char[COPY_BUFFER_SIZE];
    for (;;) {
      int n = reader.read(temp);
      if (n == -1) {
        break;
      }
      output.append(temp, 0, n);
    }
    return output.toString();    
  }
  
}
