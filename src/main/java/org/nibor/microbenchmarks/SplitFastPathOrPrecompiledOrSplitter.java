package org.nibor.microbenchmarks;

import static com.google.common.collect.Lists.*;

import java.util.List;
import java.util.regex.Pattern;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

/**
 * {@link String#split(String, int)} contains a fast path for patterns that don't need a regexp and splits it directly.
 * This benchmark measures the performance of fast path, a pre-compiled Pattern, and Guava's splitter.
 */
public class SplitFastPathOrPrecompiledOrSplitter extends Benchmark {

	private static final Pattern PATTERN = Pattern.compile("\\.");
	private static final Splitter SPLITTER = Splitter.on('.');

	@Param
	int size;

	private String input;

	@Override
	protected void setUp() throws Exception {
		List<String> parts = newArrayList();
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

	public int timeSplitFastPath(int reps) {
		int count = 0;
		for (int i = 0; i < reps; i++) {
			String[] split = input.split("\\.");
			count += split.length;
		}
		return count;
	}

	public int timeSplitPrecompiled(int reps) {
		int count = 0;
		for (int i = 0; i < reps; i++) {
			String[] split = PATTERN.split(input);
			count += split.length;
		}
		return count;
	}

	public int timeSplitter(int reps) {
		int count = 0;
		for (int i = 0; i < reps; i++) {
			Iterable<String> split = SPLITTER.split(input);
			// Important that iteration is forced, as Splitter is lazy
			count += Iterables.size(split);
		}
		return count;
	}

	public static void main(String[] args) {
		CaliperMain.main(SplitFastPathOrPrecompiledOrSplitter.class, new String[] { "-Dsize=2,3,5,8,10,100,10000" });
	}

}
