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

  private final DistanceTable distanceTable = DistanceTable.get();
  
  @Test
  public void testCases() throws IOException {
    StringBuilder buf = new StringBuilder();
    for (Case c : load()) {
      buf.append(run(c.desired, c.supported, c.distanceD_S));
      buf.append(run(c.supported, c.desired, c.distanceS_D));
    }

    if (buf.length() > 0) {
      Assert.fail("Some distance test cases failed:\n\n" + buf);
    }
 }
  
  private String run(String desiredTag, String supportedTag, int distance) {
    CLDR.Locale desired = CLDR.get().resolve(desiredTag);
    CLDR.Locale supported = CLDR.get().resolve(supportedTag);
    int actual = distanceTable.distance(desired, supported);
    
    if (actual != distance) {
      return supported + "  ;  " + desired + "  ;  " + distance + " # FAIL: actual " + actual + "\n";
    }
    return "";
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
