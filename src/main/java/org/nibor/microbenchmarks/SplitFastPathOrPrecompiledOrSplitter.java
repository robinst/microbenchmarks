package org.nibor.microbenchmarks;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * {@link String#split(String, int)} contains a fast path for patterns that don't need a regexp and splits it directly.
 * This benchmark measures the performance of fast path, a pre-compiled Pattern, and Guava's splitter.
 */
@State(Scope.Benchmark)
public class SplitFastPathOrPrecompiledOrSplitter {

	private static final Pattern PATTERN = Pattern.compile("\\.");
	private static final Splitter SPLITTER = Splitter.on('.');

	@Param({"2", "3", "5", "8", "10", "100", "10000"})
	public int size;

	private String input;

	public static void main(String[] args) throws Exception {
		Options options = new OptionsBuilder().include(SplitFastPathOrPrecompiledOrSplitter.class.getSimpleName()).build();
		new Runner(options).run();
	}

	@Setup
	public void setUp() throws Exception {
		List<String> parts = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			int mod = i % 3;
			if (mod == 0) {
				parts.add("foo");
			} else if (mod == 1) {
				parts.add("barr");
			} else {
				parts.add("bazzz");
			}
		}
		input = String.join(".", parts);
	}

	@Benchmark
	public int timeSplitFastPath() {
		int count = 0;
		String[] split = input.split("\\.");
		count += split.length;
		return count;
	}

	@Benchmark
	public int timeSplitPrecompiled() {
		int count = 0;
		String[] split = PATTERN.split(input);
		count += split.length;
		return count;
	}

	@Benchmark
	public int timeSplitter() {
		int count = 0;
		Iterable<String> split = SPLITTER.split(input);
		// Important that iteration is forced, as Splitter is lazy
		count += Iterables.size(split);
		return count;
	}

}
