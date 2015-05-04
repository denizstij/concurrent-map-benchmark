package com.blogspot.denizstij.benchmark;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import com.blogspot.denizstij.benchmark.IBenchmarkTask.EBenchmarkTaskType;
import com.blogspot.denizstij.benchmark.factory.IKeyValueFactory;
import com.blogspot.denizstij.benchmark.factory.KeyValuePair;

/**
 * @author Deniz Turan, http://denizstij.blogspot.co.uk, denizstij AT gmail.com
 */
@SuppressWarnings("rawtypes")
public class BenchmarkController {

	public long runOneBenchmark(ExecutorService producerExecutor,
										Map map,
										EBenchmarkTaskType benchmarkTaskType, 
										IKeyValueFactory factory, 
										int numMaxTask, 
										int numIterations) throws InterruptedException {

		long result = 0;
		final IBenchmarkTask benchmarkTask = BenchmarkFactory.create(benchmarkTaskType);
		benchmarkTask.setMap(map);
		factory.setLimit(numMaxTask);

		for (int i = 0; i < numIterations; i++) {
			CountDownLatch latch= new CountDownLatch(numMaxTask);
			System.out.println(benchmarkTaskType.name()+":"+i+". iteration with limit:"+numMaxTask+" for map:"+map.getClass().getName());			
			factory.reset();
			
			if (EBenchmarkTaskType.PUT.equals(benchmarkTaskType)){
				map.clear();
			}			
			System.gc();
			Thread.sleep(500);
			long now = System.nanoTime();
			producerExecutor.execute(new TaskRunnable(benchmarkTask, factory,latch));
			try {				
				latch.await();
			} catch (InterruptedException e) {			
				e.printStackTrace();
			}
			long finished = System.nanoTime();
			long elapsedTime = (finished - now);
			result+=elapsedTime;
			int size = map.size();
			System.out.println(" Size:"+size+" elapsedTime:"+elapsedTime);
		}
		return result/numIterations/1000;
	}

	private static class TaskRunnable implements Runnable {
		private IBenchmarkTask benchmarkTask;
		private IKeyValueFactory factory;
		private CountDownLatch latch;

		public TaskRunnable(IBenchmarkTask benchmarkTask,
				IKeyValueFactory factory, CountDownLatch latch) {
			this.benchmarkTask = benchmarkTask;
			this.factory = factory;
			this.latch=latch;
		}

		public void run() {
			while (factory.hasNext()) {
				final KeyValuePair keyValuePair = factory.next();
				if (keyValuePair == null) {
					return;
				}
	
				benchmarkTask.run(keyValuePair);
				latch.countDown();						
			}
		}

		@Override
		public String toString() {
			return "TaskRunnable [benchmarkTask=" + benchmarkTask + ", latch="
					+ latch.getCount() + "]";
		}
	}
}
