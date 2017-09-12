package com.squarespace.cldr;

import java.time.ZoneId;
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

  private static final String FULL = "The total for the {0, plural, one{product} other{# products}} "
      + "you ordered is {1 currency style:name minfrac:2} on {2 datetime wrap:full}. "
      + "The transfer of {3 unit compact:bytes} took "
      + "{4 unit in:second sequence:hour,minute,second format:long}.";

  private static final String PLURAL = "{0, plural, one{product} other{# products}}";
  private static final String CURRENCY = "{1 currency style:name minfrac:2}";
  private static final String DATETIME = "{2 datetime wrap:full}";
  private static final String UNIT = "{3 unit compact:bytes}";
  private static final String UNIT_SEQ = "{4 unit in:second sequence:hour,minute,second format:long}";
  
  private static final ZoneId ZONEID = ZoneId.of("America/New_York");

  private static final MessageArgs ARGS1 = MessageArgs.newBuilder()
      .add("1")
      .addMoney("50", "USD")
      .add("1498584124000")
      .add("1234567890")
      .add("123456")
      .build();

  private static final MessageArgs ARGS2 = MessageArgs.newBuilder()
      .add("14")
      .addMoney("125.50", "USD")
      .add("1498584124000")
      .add("1234567890")
      .add("123456")
      .build();

  private static final StringBuilder BUF = new StringBuilder();

  @Benchmark
  public void formatFull(BenchmarkState state) {
    BUF.setLength(0);
    state.full.format(ARGS1, BUF);
  }
  
  @Benchmark
  public void formatSingular(BenchmarkState state) {
    BUF.setLength(0);
    state.plural.format(ARGS1, BUF);
  }

  @Benchmark
  public void formatPlural(BenchmarkState state) {
    BUF.setLength(0);
    state.plural.format(ARGS2, BUF);
  }

  @Benchmark
  public void formatCurrency(BenchmarkState state) {
    BUF.setLength(0);
    state.currency.format(ARGS2, BUF);
  }

  @Benchmark
  public void formatDatetime(BenchmarkState state) {
    BUF.setLength(0);
    state.datetime.format(ARGS2, BUF);
  }

  @Benchmark
  public void formatUnit(BenchmarkState state) {
    BUF.setLength(0);
    state.unit.format(ARGS2, BUF);
  }

  @Benchmark
  public void formatUnitSequence(BenchmarkState state) {
    BUF.setLength(0);
    state.unitSeq.format(ARGS2, BUF);
  }

  @State(Scope.Benchmark)
  public static class BenchmarkState {

    private MessageFormat full;
    private MessageFormat plural;
    private MessageFormat currency;
    private MessageFormat datetime;
    private MessageFormat unit;
    private MessageFormat unitSeq;

    @Setup
    public void setup() throws RunnerException {
      this.full = new MessageFormat(CLDR.Locale.en_US, ZONEID, FULL);
      this.plural = new MessageFormat(CLDR.Locale.en_US, ZONEID, PLURAL);
      this.currency = new MessageFormat(CLDR.Locale.en_US, ZONEID, CURRENCY);
      this.datetime = new MessageFormat(CLDR.Locale.en_US, ZONEID, DATETIME);
      this.unit = new MessageFormat(CLDR.Locale.en_US, ZONEID, UNIT);
      this.unitSeq = new MessageFormat(CLDR.Locale.en_US, ZONEID, UNIT_SEQ);
    }

  }

  public static void main(String[] args) throws Exception {
    BenchmarkState state = new BenchmarkState();
    state.setup();

    state.full.format(ARGS1, BUF);
    System.out.println(BUF.toString());

    BUF.setLength(0);
    state.full.format(ARGS2, BUF);
    System.out.println(BUF.toString());
  }

}
