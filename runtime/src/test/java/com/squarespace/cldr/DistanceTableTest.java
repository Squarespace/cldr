package com.squarespace.cldr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;


public class DistanceTableTest {

  private static final boolean DEBUG = false;
  
  private final DistanceTable distanceTable = new DistanceTable();
  
  @Test
  public void testCases() throws IOException {
    for (Case c : load()) {
      run(c.desired, c.supported, c.distanceD_S);
      run(c.supported, c.desired, c.distanceS_D);
    }
  }
  
  private void run(String desiredTag, String supportedTag, int distance) {
    CLDR.Locale desired = CLDR.get().resolve(desiredTag);
    CLDR.Locale supported = CLDR.get().resolve(supportedTag);
    int actual = distanceTable.distance(desired, supported);
    String message = supported + "  ;  " + desired + "  ;  " + distance;
    if (DEBUG) {
      System.err.println(message + "   #  actual: " + actual);
    }
    Assert.assertEquals(actual, distance, 
        "For distance(desired=" + desired + ", supported=" + supported + ")");
  }

  private static List<Case> load() throws IOException {
    String path = "locale-distance-cases.txt";
    List<Case> cases = new ArrayList<>();
    for (String line : TestUtils.loadTestFile(DistanceTableTest.class, path)) {
      String[] cols = line.split(";");
      String reason = "Found line with invalid length in " + path;
      assertThat(reason, cols.length, anyOf(is(3), is(4)));

      Case c = new Case();
      c.supported = cols[0].trim();
      c.desired = cols[1].trim();
      c.distanceD_S = Integer.parseInt(cols[2].trim());
      c.distanceS_D = cols.length == 3 ? c.distanceD_S : Integer.parseInt(cols[3].trim());
      cases.add(c);
    }
    return cases;
  }
  
  static class Case {
    String supported;
    String desired;
    int distanceD_S;
    int distanceS_D;
  }

}
