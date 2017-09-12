package com.squarespace.cldr.codegen.reader;

import java.util.ArrayList;
import java.util.List;

import com.squarespace.cldr.LanguageMatch;
import com.squarespace.compiler.parse.Pair;


public class LanguageMatchingData {
  
  public final List<String> paradigmLocales = new ArrayList<>();
  public final List<LanguageMatch> languageMatches = new ArrayList<>();
  public final List<Pair<String, String>> matchVariables = new ArrayList<>();
  
}
