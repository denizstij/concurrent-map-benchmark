package com.blogspot.denizstij.benchmark.factory;

/**
 * @author Deniz Turan, http://denizstij.blogspot.co.uk, denizstij AT gmail.com
 */
public class KeyValuePair  <K,V>  {
	private final K key;
	private final V value;
	
	public KeyValuePair(K key, V value){
		this.key=key;
		this.value=value;
	}
	
	/**
	 * @return the key
	 */
	public K getKey() {
		return key;
	}
	/**
	 * @return the value
	 */
	public V getValue() {
		return value;
	}
}
