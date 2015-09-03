package org.nibor.microbenchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
public class StringPlusOrBuilderBenchmark {

	@Param({"5", "10", "100", "1000", "10000"})
	public int size;

	public static void main(String[] args) throws Exception {
		Options options = new OptionsBuilder().include(StringPlusOrBuilderBenchmark.class.getSimpleName()).build();
		new Runner(options).run();
	}

	@Benchmark
	public int timePlus() {
		int count = 0;
		String s = "";
		for (int j = 0; j < size; j++) {
			s += "na";
		}
		count += s.length();
		return count;
	}

	@Benchmark
	public int timeBuilder() {
		int count = 0;
		StringBuilder s = new StringBuilder();
		for (int j = 0; j < size; j++) {
			s.append("na");
		}
		count += s.length();
		return count;
	}

}
