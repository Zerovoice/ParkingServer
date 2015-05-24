package cn.zerovoice.server.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

	private static ExecutorService mExecutor = Executors.newFixedThreadPool(2);
	
	public static void submit(Runnable r){
		mExecutor.submit(r);
	}
	
}
