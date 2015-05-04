package com.blogspot.denizstij.benchmark;

import com.blogspot.denizstij.benchmark.IBenchmarkTask.EBenchmarkTaskType;
/**
 * @author Deniz Turan, http://denizstij.blogspot.co.uk, denizstij AT gmail.com
 */
public class BenchmarkFactory {

	public static IBenchmarkTask create(EBenchmarkTaskType benchmarkTaskType) {
		IBenchmarkTask benchmarkTask = null;

		switch (benchmarkTaskType) {
		case GET:
			benchmarkTask = new GetBenchmarkTask();
			break;
		case PUT:
			benchmarkTask = new PutBenchmarkTask();
			break;
		default:
			break;
		}
		return benchmarkTask;
	}

}
