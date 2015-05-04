package com.blogspot.denizstij.benchmark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.blogspot.denizstij.benchmark.IBenchmarkTask.EBenchmarkTaskType;
import com.blogspot.denizstij.benchmark.factory.IKeyValueFactory;
import com.blogspot.denizstij.benchmark.factory.RandomStringLongKeyValueFactory;

/**
 * @author Deniz Turan, http://denizstij.blogspot.co.uk, denizstij AT gmail.com
 */
@SuppressWarnings("rawtypes")
public class BenchmarkMain {
		
    public static void main(String[] args) throws Exception {
        BenchmarkMain benchmark = new BenchmarkMain();
        benchmark.runBenchmark();        
    }
    
	public void runBenchmark() throws Exception {
		BenchmarkController controller= new BenchmarkController();;
		
        int numIterations = Integer.parseInt(System.getProperty("NUM_ITERATIONS", "2"));                
        int numWarmupTasks = Integer.parseInt(System.getProperty("NUM_WARMUP_TASKS", "100"));
        int numofProducer=Integer.parseInt(System.getProperty("NUM_PRODUCER", "10"));
        int keyLength=Integer.parseInt(System.getProperty("KEY_LENGTH", "8"));  
        int[] numTasks=getTaskNumbers();		
        int maxNumTasks = numTasks[numTasks.length-1];
                
        Map<EMapType,List<Long>>  putResults= new LinkedHashMap<EMapType,List<Long>>();
        
        Map<EMapType,List<Long>>  getResults= new LinkedHashMap<EMapType,List<Long>>();
        
		ExecutorService producerExecutor = Executors.newFixedThreadPool(numofProducer);

		IKeyValueFactory factory= new RandomStringLongKeyValueFactory();
		factory.setKeyLength(keyLength).setSampleNumber(maxNumTasks).setLimit(numWarmupTasks).build();

		EMapType[] values = EMapType.values();
        System.out.println("****START WARMUP*****");        
        for (EMapType eMapType : values) {
        	System.out.println("Warming up map "+eMapType.name());
        	controller.runOneBenchmark(producerExecutor,MapFactory.create(eMapType),EBenchmarkTaskType.PUT,factory,numWarmupTasks,1);	
        	controller.runOneBenchmark(producerExecutor,MapFactory.getCache(eMapType),EBenchmarkTaskType.GET,factory,numWarmupTasks,1);
		}				
        System.out.println("****END WARMUP*****");

        // lets force a GC to prevent further possible GC
        System.gc();
        Thread.sleep(1000);
        
        for (EMapType eMapType : values) {
        	System.out.println("Runing map "+eMapType.name());
        	List<Long> putResultList = new ArrayList<Long>();
        	List<Long> getResultList = new ArrayList<Long>();
        	
            for (int numTask : numTasks) {
            	System.out.println(numTask+" num task "+eMapType.name());
            	
            	long resPut=controller.runOneBenchmark(producerExecutor,MapFactory.create(eMapType),EBenchmarkTaskType.PUT,factory,numTask,numIterations);
            	putResultList.add(resPut);
            	System.out.println("Put results:"+putResultList);
            	
            	long resGet=controller.runOneBenchmark(producerExecutor,MapFactory.getCache(eMapType),EBenchmarkTaskType.GET,factory,numTask,numIterations);
            	getResultList.add(resGet);
            	System.out.println("Get results:"+getResultList);
            }
            putResults.put(eMapType,putResultList);
            getResults.put(eMapType, getResultList);
		}				

        System.out.println("Number of Iterations = " + numIterations);
        System.out.println("Number of warm up tasks executed per run = " + numWarmupTasks);
        System.out.println("Number of tasks executed= " + Arrays.toString(numTasks));
        System.out.println("Number of tasks producer= " + numofProducer);
        System.out.println("Symbols length= " + keyLength);
        printResults("Put",numTasks,putResults);
        printResults("Get",numTasks,getResults);   
        List<Runnable> activeTasks = producerExecutor.shutdownNow();
        System.out.println(activeTasks.toString()+ "|||"+activeTasks.size()+" task has not finished yet!!!");
    }
    
    private void printResults(String msg, int[] numTasks, Map<EMapType, List<Long>> resultMap) {
    	System.out.println("*************************************");    	
    	System.out.println(msg);
    	
    	System.out.print(",");
    	for (int  numTask : numTasks) {
    		System.out.print(numTask+",");
    	}    	    	
    	System.out.println("");
    	Set<EMapType> keySet = resultMap.keySet();
    	for (EMapType eMapType : keySet) {
    		System.out.print(eMapType.name()+",");
    		List<Long> resultList = resultMap.get(eMapType);
    		for (Long res : resultList) {
    			System.out.print(res+",");
			}
			System.out.println("");
		}
    	
    	System.out.println("*************************************");
		
	}

	public int [] getTaskNumbers() throws Exception {		
    	String numTaskStr=System.getProperty("NUM_TASKS", "10,100");
    	
        String numTaskStrArr[] = numTaskStr.split(",");
        int arr[] = new int[numTaskStrArr.length];
        for (int i=0;i<arr.length;i++) {
        	arr[i]=Integer.parseInt(numTaskStrArr[i]);			
		}
        System.out.println(numTaskStr+Arrays.toString(arr));
        return arr;
    }    
}
