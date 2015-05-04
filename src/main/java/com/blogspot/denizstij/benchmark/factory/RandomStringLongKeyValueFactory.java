package com.blogspot.denizstij.benchmark.factory;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author Deniz Turan, http://denizstij.blogspot.co.uk, denizstij AT gmail.com
 */
@SuppressWarnings({"unchecked"})
public class RandomStringLongKeyValueFactory implements IKeyValueFactory<String, Long> {
    
	private KeyValuePair<String, Long> [] keyValueArr;	
	private int keyLenght;
	private int numSamples;
	private AtomicInteger index= new AtomicInteger(0);
	private volatile int limit;
	private boolean hasMore=true;
	
	
	public IKeyValueFactory<String, Long> setKeyLength(int keyLength) {
		this.keyLenght=keyLength;
		return this;
	}
	
	
	public IKeyValueFactory<String, Long> setSampleNumber(int numSamples) {
		this.numSamples=numSamples;
		keyValueArr= new KeyValuePair[numSamples];
		return this;
	}
	
	public KeyValuePair<String, Long> next() {
		int i=index.getAndIncrement();
		if (i>=limit){
			hasMore=false;
			return null;
		}
		return keyValueArr[i];		
	}
	
	public void build() {
		System.out.println("Genearing "+numSamples+"  random strings with length "+keyLenght);
		Random random= new Random(0);
		for (int i=0;i<numSamples;i++){
			String key=RandomStringUtils.randomAlphanumeric(keyLenght);
			long value=random.nextLong();
			keyValueArr[i]=new KeyValuePair<String, Long>(key, value);
		}
		System.out.println("Geneared "+numSamples+"  random strings with length "+keyLenght);
	}
	
	public void reset() {
		index.set(0);	
		hasMore=true;
	}

	public IKeyValueFactory<String, Long>  setLimit(int limit) {
		this.limit=limit;
		return this;
	}

	public boolean hasNext() {
		return hasMore;
	}	
}
