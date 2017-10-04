package com.squarespace.cldr;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;


public class LanguageMatcherTest {

  private static final boolean DEBUG = false;
  
  @Test
  public void testCases() throws IOException {
    for (Case c : load()) {
      match(c.supported, c.desired, c.result);
    }
  }
  
  @Test
  public void testBasic() {
    match("en-US fr-FR de-DE es-ES", "zh ar de", "de-DE");
  }
  
  @Test
  public void testList() {
    match(asList("en", "ar", "zh", "es"), asList("no", "he", "zh", "es"), "zh");
    match(asList("en_US", "fr_FR", "und-DE"), asList("zh", "de"), "und-DE");
    
    // Test list where each element might contain multiple locales. These will
    // be parsed and flat-mapped.
    match(asList("en ar", "de es fr"), asList("no he", "es"), "es");
  }
  
  private void match(List<String> supported, List<String> desired, String result) {
    LanguageMatcher matcher = new LanguageMatcher(supported);
    String actual = matcher.match(desired);
    String message = supported + "  ;  " + desired + "  ;  " + result;
    Assert.assertEquals(actual, result, message);
  }
  
  private void match(String supported, String desired, String result) {
    LanguageMatcher matcher = new LanguageMatcher(supported);
    String actual = matcher.match(desired);
    String message = supported + "  ;  " + desired + "  ;  " + result;
    if (DEBUG) {
      System.err.println(message + "   #  actual: " + actual);
    }
    Assert.assertEquals(actual, result, "Failed test case: " + message + ".");
  }
  
  private static List<Case> load() throws IOException {
    String path = "locale-match-cases.txt";
    List<Case> cases = new ArrayList<>();
    for (String line : TestUtils.loadTestFile(DistanceTableTest.class, path)) {
      String[] cols = line.split(";");
      String reason = "Found line with invalid length in " + path;
      Assert.assertEquals(cols.length, 3, reason);

      Case c = new Case();
      c.supported = cols[0].trim();
      c.desired = cols[1].trim();
      c.result = cols[2].trim();
      cases.add(c);
    }
    return cases;
  }
  
  static class Case {
    String supported;
    String desired;
    String result;
  }

}
