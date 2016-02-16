package org.nibor.microbenchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Is it faster to match text inside parens using <code>.*?</code> or <code>[^)]*</code>?
 */
@State(Scope.Benchmark)
public class PatternMatchParensBenchmark {

    @Param({"10", "100", "1000", "10000"})
    public int size;
    private String input;

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(PatternMatchParensBenchmark.class.getSimpleName()).build();
        new Runner(options).run();
    }

    @Setup
    public void setUp() throws Exception {
        input = String.join(" ", Collections.nCopies(size, "$(test) text"));
    }

    @Benchmark
    public int timeLazy() {
        final Pattern pattern = Pattern.compile("\\$\\((.*?)\\)");
        int count = 0;
        final Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    @Benchmark
    public int timeDirect() {
        final Pattern pattern = Pattern.compile("\\$\\(([^)]*)\\)");
        int count = 0;
        final Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

}
