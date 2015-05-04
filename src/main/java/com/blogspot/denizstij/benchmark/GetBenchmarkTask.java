package com.blogspot.denizstij.benchmark;

/**
 * @author Deniz Turan, http://denizstij.blogspot.co.uk, denizstij AT gmail.com
 */
import java.util.Map;

import com.blogspot.denizstij.benchmark.factory.KeyValuePair;

@SuppressWarnings("rawtypes")
public class GetBenchmarkTask implements IBenchmarkTask {
	private Map map;

	public IBenchmarkTask setMap(Map map) {
		this.map=map;
		return this;
	}
	
	public KeyValuePair run(KeyValuePair keyValue) {			
		KeyValuePair res = (KeyValuePair) map.get(keyValue.getKey());
		return res;
	}
	
	@Override
	public String toString() {
		return "Get "+map.getClass().getSimpleName();
	}
}
