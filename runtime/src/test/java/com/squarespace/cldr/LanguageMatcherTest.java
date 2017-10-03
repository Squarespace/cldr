package com.squarespace.cldr;

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
      run(c.supported, c.desired, c.result);
    }
  }
  
  private void run(String supported, String desired, String result) {
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
