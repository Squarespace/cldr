package com.squarespace.cldr;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.RunnerException;


@Fork(1)
@Measurement(iterations = 5, time = 5)
@Warmup(iterations = 3, time = 2)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class MessageFormatBenchmark {

  private static final String FORMAT1 = "The total for the {0, plural, one{product} other{# products}} "
      + "you ordered is {1 currency style:name min-fractional-digits:2} at {2 datetime time-medium}.";

  private static final CLDRLocale EN_US = new CLDRLocale("en", "", "US", "POSIX");

  private static final CLDRCurrency USD = new CLDRCurrency("USD");

  private static final MessageArgs ARGS1 = MessageArgs.newBuilder()
      .add("1")
      .add("50")
      .add("1498584124000")
      .build();

  private static final MessageArgs ARGS2 = MessageArgs.newBuilder()
      .add("14")
      .add("125.50")
      .add("1498584124000")
      .build();

  private static final StringBuilder BUF = new StringBuilder();

  @Benchmark
  public void formatSingular(BenchmarkState state) {
    BUF.setLength(0);
    state.format1.format(FORMAT1, ARGS1, BUF);
  }

  @Benchmark
  public void formatPlural(BenchmarkState state) {
    BUF.setLength(0);
    state.format1.format(FORMAT1, ARGS2, BUF);
  }

  @State(Scope.Benchmark)
  public static class BenchmarkState {

    private MessageFormat format1;

    @Setup
    public void setup() throws RunnerException {
      this.format1 = new MessageFormat(EN_US, USD);
    }

  }

  public static void main(String[] args) throws Exception {
    BenchmarkState state = new BenchmarkState();
    state.setup();

    state.format1.format(FORMAT1, ARGS2, BUF);
    System.out.println(BUF.toString());

    BUF.setLength(0);
    state.format1.format(FORMAT1, ARGS2, BUF);
    System.out.println(BUF.toString());
  }

}
