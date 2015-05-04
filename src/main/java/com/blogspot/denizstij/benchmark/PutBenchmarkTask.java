package com.blogspot.denizstij.benchmark;

import java.util.Map;

import com.blogspot.denizstij.benchmark.factory.KeyValuePair;

/**
 * @author Deniz Turan, http://denizstij.blogspot.co.uk, denizstij AT gmail.com
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class PutBenchmarkTask implements IBenchmarkTask {
	private Map map;
	
	public IBenchmarkTask setMap(Map map) {
		this.map=map;
		return this;
	}

	@Override
	public String toString() {
		return "Put "+map.getClass().getSimpleName();
	}

	public KeyValuePair run(KeyValuePair keyValue) {		
		map.put(keyValue.getKey(), keyValue);		
		return keyValue;
	}

}
