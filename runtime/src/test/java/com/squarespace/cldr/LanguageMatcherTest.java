package com.squarespace.cldr;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

import com.squarespace.cldr.LanguageMatcher.Match;


public class LanguageMatcherTest {

  private static final boolean DEBUG = false;
  private static final Pattern SPACES = Pattern.compile("\\s+");
  
  @Test
  public void testCases() throws IOException {
    for (Case c : load()) {
      match(c.supported, c.desired, c.result);
    }
  }
  
// TODO: convert this into a file-based test case
//  @Test
//  public void testDebug() throws IOException {
//    for (Case c : load() ) {
//      LanguageMatcher matcher = new LanguageMatcher(c.supported);
//      List<Match> matches = matcher.matches(c.desired);
//      System.out.println("supported: " + c.supported);
//      System.out.println("  desired: " + c.desired);
//      System.out.println(matches);
//      System.out.println(c.result);
//      System.out.println();
//    }
//  }
  
  @Test
  public void testMatches() {
    matchlist("es-419, es-ES", "es-AR", "es-419:4  es-ES:5");
    matchlist("es-ES, es-419", "es-AR", "es-419:4  es-ES:5");
    matchlist("es-419, es", "es-AR", "es-419:4  es:5");
    matchlist("es, es-419", "es-AR", "es-419:4  es:5");
    matchlist("es-MX, es", "es-AR", "es-MX:4  es:5");
    matchlist("es, es-MX", "es-AR", "es-MX:4  es:5");
    matchlist("es-ES, es-419, es-PT", "es-MX", "es-419:4  es-ES:5  es-PT:5");

    // When distances are equal, paradigm locale is preferred.
    matchlist("es-MX, es-419, es-ES", "es-AR", "es-419:4  es-MX:4  es-ES:5");
    
    matchlist("zh-HK, zh-TW, zh", "zh-MO", "zh-HK:4  zh-TW:5  zh:23");
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
    Match actual = matcher.match(desired);
    String message = supported + "  ;  " + desired + "  ;  " + result;
    assertEquals(actual.bundleId(), result, message);
  }
  
  private void match(String supported, String desired, String result) {
    LanguageMatcher matcher = new LanguageMatcher(supported);
    Match actual = matcher.match(desired);
    String message = supported + "  ;  " + desired + "  ;  " + result;
    if (DEBUG) {
      System.err.println(message + "   #  actual: " + actual);
    }
    assertEquals(actual.bundleId(), result, "Failed test case: " + message + ".");
  }
  
  private void matchlist(String supported, String desired, String result) {
    LanguageMatcher matcher = new LanguageMatcher(supported);
    List<Match> actual = matcher.matches(desired);
    String[] parts = SPACES.split(result);
    assertEquals(actual.size(), parts.length);
    for (int i = 0; i < parts.length; i++) {
      String[] expected = parts[i].split(":");
      String bundleId = expected[0];
      int distance = Integer.parseInt(expected[1]);
      assertEquals(actual.get(i).bundleId(), bundleId);
      assertEquals(actual.get(i).distance(), distance);
    }
  }
  
  private static List<Case> load() throws IOException {
    String path = "locale-match-cases.txt";
    List<Case> cases = new ArrayList<>();
    for (String line : TestUtils.loadTestFile(DistanceTableTest.class, path)) {
      String[] cols = line.split(";");
      String reason = "Found line with invalid length in " + path;
      assertEquals(cols.length, 3, reason);

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
