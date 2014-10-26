package org.nibor.microbenchmarks;

import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;

public class StringFormatOrConcatBenchmark extends Benchmark {

	public int timeFormat(int reps) {
		int count = 0;
		for (int i = 0; i < reps; i++) {
			String formatted = String.format("%s-%s", i, i + 1);
			count += formatted.length();
		}
		return count;
	}

	public int timeConcat(int reps) {
		int count = 0;
		for (int i = 0; i < reps; i++) {
			String formatted = (i) + "-" + (i + 1);
			count += formatted.length();
		}
		return count;
	}

	public static void main(String[] args) {
		CaliperMain.main(StringFormatOrConcatBenchmark.class, new String[] {});
	}
}
