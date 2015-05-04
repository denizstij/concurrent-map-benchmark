package com.blogspot.denizstij.benchmark.factory;

/**
 * @author Deniz Turan, http://denizstij.blogspot.co.uk, denizstij AT gmail.com
 */
public interface IKeyValueFactory<K,V> {
	
	IKeyValueFactory<K,V> setSampleNumber(int numSamples);
	IKeyValueFactory<K,V> setKeyLength(int keyLength);
	IKeyValueFactory<K,V> setLimit(int limit);
	void reset();
	void build();
	KeyValuePair <K,V> next();
	boolean hasNext();
		
}
