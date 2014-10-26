package org.nibor.microbenchmarks;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;

public class StringPlusOrBuilderBenchmark extends Benchmark {

	@Param
	int size;

	public int timePlus(int reps) {
		int count = 0;
		for (int i = 0; i < reps; i++) {
			String s = "";
			for (int j = 0; j < size; j++) {
				s += "na";
			}
			count += s.length();
		}
		return count;
	}

	public int timeBuilder(int reps) {
		int count = 0;
		for (int i = 0; i < reps; i++) {
			StringBuilder s = new StringBuilder();
			for (int j = 0; j < size; j++) {
				s.append("na");
			}
			count += s.length();
		}
		return count;
	}

	public static void main(String[] args) {
		CaliperMain.main(StringPlusOrBuilderBenchmark.class, new String[] { "-Dsize=5,10,100,1000,10000" });
	}
}
