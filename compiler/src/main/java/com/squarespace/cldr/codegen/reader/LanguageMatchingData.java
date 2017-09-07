package com.squarespace.cldr.codegen.reader;

import java.util.ArrayList;
import java.util.List;

import com.squarespace.compiler.parse.Pair;


public class LanguageMatchingData {
  
  public final List<String> paradigmLocales = new ArrayList<>();
  public final List<LanguageMatch> languageMatches = new ArrayList<>();
  public final List<Pair<String, String>> matchVariables = new ArrayList<>();

  public static class LanguageMatch {
    public final String desired;
    public final String supported;
    public final int distance;
    public final boolean oneway;
    
    public LanguageMatch(String desired, String supported, int distance, boolean oneway) {
      this.desired = desired;
      this.supported = supported;
      this.distance = distance;
      this.oneway = oneway;
    }
    
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("LanguageMatch(desired=").append(desired);
      buf.append(", supported=").append(supported);
      buf.append(", distance=").append(distance);
      buf.append(", oneway=").append(oneway).append(')');
      return buf.toString();
    }
  }
  
}
