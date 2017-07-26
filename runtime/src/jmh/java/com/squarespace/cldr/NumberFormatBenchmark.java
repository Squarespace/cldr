package com.squarespace.cldr;

import java.math.BigDecimal;
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

import com.squarespace.cldr.numbers.CurrencyFormatOptions;
import com.squarespace.cldr.numbers.CurrencyFormatStyle;
import com.squarespace.cldr.numbers.DecimalFormatOptions;
import com.squarespace.cldr.numbers.NumberFormatMode;
import com.squarespace.cldr.numbers.DecimalFormatStyle;
import com.squarespace.cldr.numbers.NumberFormatter;


@Fork(1)
@Measurement(iterations = 5, time = 5)
@Warmup(iterations = 3, time = 2)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class NumberFormatBenchmark {

  private static final StringBuilder BUF = new StringBuilder();

  private static final BigDecimal DECIMAL_N1 = new BigDecimal("123456.123456");
  
  private static final BigDecimal CURRENCY_N1 = new BigDecimal("15161789.19");
  
  private static final DecimalFormatOptions DECIMAL_OPTS1 = new DecimalFormatOptions(DecimalFormatStyle.DECIMAL)
      .setGrouping(true)
      .setFormatMode(NumberFormatMode.SIGNIFICANT)
      .setMinimumSignificantDigits(3)
      .setMaximumSignificantDigits(100);

  private static final CurrencyFormatOptions CURRENCY_OPTS1 = new CurrencyFormatOptions(CurrencyFormatStyle.SHORT)
      .setGrouping(true)
      .setFormatMode(NumberFormatMode.SIGNIFICANT)
      .setMinimumSignificantDigits(3)
      .setMaximumSignificantDigits(3);
  
  @Benchmark
  public void formatDecimal(BenchmarkState state) {
   state.formatDecimal(DECIMAL_N1, DECIMAL_OPTS1);
  }

  @Benchmark
  public void formatCurrency(BenchmarkState state) {
    state.formatCurrency(CURRENCY_N1, CURRENCY_OPTS1, CLDR.Currency.USD);
  }

  @State(Scope.Benchmark)
  public static class BenchmarkState {

    private NumberFormatter en_US;
    
    @Setup
    public void setup() throws RunnerException {
      en_US = CLDR.get().getNumberFormatter(CLDR.EN_US);
    }
   
    public void formatDecimal(BigDecimal n, DecimalFormatOptions opts) {
      BUF.setLength(0);
      en_US.formatDecimal(n, BUF, opts);
    }
    
    public void formatCurrency(BigDecimal n, CurrencyFormatOptions opts, String currencyCode) {
      BUF.setLength(0);
      en_US.formatCurrency(n, currencyCode, BUF, opts);
    }
  }
  
  public static void main(String[] args) throws Exception {
    BenchmarkState state = new BenchmarkState();
    state.setup();
    state.formatDecimal(DECIMAL_N1, DECIMAL_OPTS1);
    System.out.println(BUF.toString());
    state.formatCurrency(CURRENCY_N1, CURRENCY_OPTS1, CLDR.Currency.USD);
    System.out.println(BUF.toString());
  }
}
