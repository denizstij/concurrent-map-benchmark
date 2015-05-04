package com.blogspot.denizstij.benchmark;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javolution.util.FastMap;

import com.google.common.collect.MapMaker;
import com.gs.collections.impl.map.mutable.ConcurrentHashMapUnsafe;

/**
 * @author Deniz Turan, http://denizstij.blogspot.co.uk, denizstij AT gmail.com
 */
@SuppressWarnings({"rawtypes"})
public class MapFactory {

	private static Map<EMapType, Map> cache= new HashMap<EMapType, Map>();

	public static Map create(EMapType mapType) {
		int initialCapacity=16;
		int concurrencyLevel=16;
		Map map=null;
		switch (mapType){
		case Gauva_ConcurrentHashMap:			
			map=new MapMaker().initialCapacity(initialCapacity).concurrencyLevel(concurrencyLevel).makeMap();
			break;
		case GS_ConcurrentHashMap:
			map= new com.gs.collections.impl.map.mutable.ConcurrentHashMap(initialCapacity);
			break;		
		case GS_ConcurrentHashMapUnsafe:
			map= new ConcurrentHashMapUnsafe(initialCapacity);
			break;		
		case Javolution_FastMap:
			map=new FastMap().shared();
			break;
		case Oracle_ConcurrentHashMap:
			map= new ConcurrentHashMap(initialCapacity,0.75f,concurrencyLevel);
			break;
		default:
			break;
			
		}
		cache.put(mapType,map);
		return map;
	}

	public static Map getCache(EMapType eMapType) {		
		return cache.get(eMapType);
	}

}
