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

import com.squarespace.cldr.numbers.NumberOperands;


@Fork(1)
@Measurement(iterations = 5, time = 5)
@Warmup(iterations = 3, time = 2)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class NumberOperandsBenchmark {

  private static final String N1 = "123456789.01234567800";

  @Benchmark
  public void parseOperands(BenchmarkState state) {
    state.operands.set(N1);
  }

  @State(Scope.Benchmark)
  public static class BenchmarkState {
    
    private NumberOperands operands;
    
    @Setup
    public void setup() throws RunnerException {
      operands = new NumberOperands();
    }
  }

}
