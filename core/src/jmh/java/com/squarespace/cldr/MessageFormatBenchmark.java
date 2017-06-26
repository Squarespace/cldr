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

  private static final String FORMAT1 = "The total for the {0 cardinal one{product} other{# products}} "
      + "you ordered is {1 currency style:name min-fractional-digits:2}.";

  private static final MessageArg[] ARGS1 = new MessageArg[] {
    new MessageArg("1"),
    new MessageArg("50")
  };

  private static final MessageArg[] ARGS2 = new MessageArg[] {
    new MessageArg("14"),
    new MessageArg("125.50")
  };

  private static final StringBuilder BUF = new StringBuilder();

  @Benchmark
  public void formatSingular(BenchmarkState state) {
    BUF.setLength(0);
    state.format1.format(BUF, ARGS1);
  }

  @Benchmark
  public void formatPlural(BenchmarkState state) {
    BUF.setLength(0);
    state.format1.format(BUF, ARGS2);
  }

  @State(Scope.Benchmark)
  public static class BenchmarkState {

    private MessageFormat format1;

    @Setup
    public void setup() throws RunnerException {
      this.format1 = new MessageFormat(FORMAT1, "en", "USD");
    }

  }

  public static void main(String[] args) throws Exception {
    BenchmarkState state = new BenchmarkState();
    state.setup();
    state.format1.format(BUF, ARGS1);
    System.out.println(BUF.toString());
  }

}
