package com.squarespace.cldr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;


@Fork(1)
@Measurement(iterations = 5, time = 5)
@Warmup(iterations = 3, time = 2)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class LanguageMatcherBenchmark {

  private static final List<String> SUPPORTED = Arrays.asList(
      "es-MX", "es-419", "es-ES", "zh-HK", "zh-TW", "zh", "en-GB", "en-AU", "en-CA"
  );
  
  public static final List<String> SUPPORTED3 = select(3);
  public static final List<String> SUPPORTED6 = select(6);
  public static final List<String> SUPPORTED9 = select(9);
  
  @Benchmark
  public void build3(BenchmarkState state, Blackhole blackhole) {
    blackhole.consume(new LanguageMatcher(SUPPORTED3));
  }
  
  @Benchmark
  public void build6(BenchmarkState state, Blackhole blackhole) {
    blackhole.consume(new LanguageMatcher(SUPPORTED6));
  }
  
  @Benchmark
  public void build9(BenchmarkState state, Blackhole blackhole) {
    blackhole.consume(new LanguageMatcher(SUPPORTED9));
  }

  @Benchmark
  public void match3(BenchmarkState state, Blackhole blackhole) {
    blackhole.consume(state.matcher3.match("en-AR"));
  }
  
  @Benchmark
  public void match3Miss(BenchmarkState state, Blackhole blackhole) {
    blackhole.consume(state.matcher3.match("de-DE"));
  }

  @Benchmark
  public void match6(BenchmarkState state, Blackhole blackhole) {
    blackhole.consume(state.matcher6.match("zh-MO"));
  }

  @Benchmark
  public void match6Miss(BenchmarkState state, Blackhole blackhole) {
    blackhole.consume(state.matcher6.match("de-DE"));
  }
  
  @Benchmark
  public void match9(BenchmarkState state, Blackhole blackhole) {
    blackhole.consume(state.matcher9.match("en-ZA"));
  }

  @Benchmark
  public void match9Miss(BenchmarkState state, Blackhole blackhole) {
    blackhole.consume(state.matcher9.match("de-DE"));
  }
  
  @State(Scope.Benchmark)
  public static class BenchmarkState {
    
    private final LanguageMatcher matcher3 = new LanguageMatcher(SUPPORTED3);
    private final LanguageMatcher matcher6 = new LanguageMatcher(SUPPORTED6);
    private final LanguageMatcher matcher9 = new LanguageMatcher(SUPPORTED9);
    
  }

  private static List<String> select(int count) {
    List<String> result = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      result.add(SUPPORTED.get(i));
    }
    return result;
  }
}
