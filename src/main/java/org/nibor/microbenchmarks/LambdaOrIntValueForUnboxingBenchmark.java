package org.nibor.microbenchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Is it faster to unbox using <code>Integer::intValue</code> or <code>i -&gt; i</code>?
 */
@State(Scope.Benchmark)
public class LambdaOrIntValueForUnboxingBenchmark {

    private Integer[] input;

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(LambdaOrIntValueForUnboxingBenchmark.class.getSimpleName()).build();
        new Runner(options).run();
    }

    @Setup
    public void setUp() throws Exception {
        input = IntStream.range(1, 100).boxed().toArray(Integer[]::new);
    }

    @Benchmark
    public int lambda() {
        int[] result = Arrays.stream(input).mapToInt(i -> i).toArray();
        return result.length;
    }

    @Benchmark
    public int intValue() {
        int[] result = Arrays.stream(input).mapToInt(Integer::intValue).toArray();
        return result.length;
    }
}
