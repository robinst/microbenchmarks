package org.nibor.microbenchmarks;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;

/**
 * Is it faster to match text inside parens using <code>.*?</code> or <code>[^)]*</code>?
 */
public class PatternMatchParensBenchmark extends Benchmark {

	@Param
	int size;

	private String input;

	@Override
	protected void setUp() throws Exception {
		input = String.join(" ", Collections.nCopies(size, "$(test) text"));
	}

	public int timeLazy(int reps) {
		final Pattern pattern = Pattern.compile("\\$\\((.*?)\\)");
		int count = 0;
		for (int i = 0; i < reps; i++) {
			final Matcher matcher = pattern.matcher(input);
			while (matcher.find()) {
				count++;
			}
		}
		return count;
	}

	public int timeDirect(int reps) {
		final Pattern pattern = Pattern.compile("\\$\\(([^)]*)\\)");
		int count = 0;
		for (int i = 0; i < reps; i++) {
			final Matcher matcher = pattern.matcher(input);
			while (matcher.find()) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		// CaliperMain.main(PatternMatchParensBenchmark.class, new String[] { "-Dsize=10,100,1000,10000" });
		CaliperMain.main(PatternMatchParensBenchmark.class, new String[] { "-p" });
	}
}
