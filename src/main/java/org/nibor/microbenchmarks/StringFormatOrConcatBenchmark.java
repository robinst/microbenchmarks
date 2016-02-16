package org.nibor.microbenchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class StringFormatOrConcatBenchmark {

    private static final int i = 1;

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(StringFormatOrConcatBenchmark.class.getSimpleName()).build();
        new Runner(options).run();
    }

    @Benchmark
    public int timeFormat() {
        int count = 0;
        String formatted = String.format("%s-%s", i, i + 1);
        count += formatted.length();
        return count;
    }

    @Benchmark
    public int timeConcat() {
        int count = 0;
        String formatted = (i) + "-" + (i + 1);
        count += formatted.length();
        return count;
    }

}
