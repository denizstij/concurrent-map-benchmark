package com.blogspot.denizstij.benchmark;

import java.util.Map;

import com.blogspot.denizstij.benchmark.factory.KeyValuePair;

/**
 * @author Deniz Turan, http://denizstij.blogspot.co.uk, denizstij AT gmail.com
 */
@SuppressWarnings({"rawtypes"})
public interface IBenchmarkTask {
	enum EBenchmarkTaskType {PUT, GET}
	IBenchmarkTask setMap(Map map);		
	KeyValuePair run(KeyValuePair keyValue);
}
