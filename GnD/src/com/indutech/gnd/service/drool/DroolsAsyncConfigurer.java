package com.indutech.gnd.service.drool;

import java.util.concurrent.Executor;

import org.springframework.scheduling.annotation.AsyncConfigurer;

public class DroolsAsyncConfigurer implements AsyncConfigurer {

	@Override
	public Executor getAsyncExecutor() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*@Bean
	public DroolBootStrap asyncTask() {
	     return new DroolBootStrap();
	}
	@Override
	public Executor getAsyncExecutor() {
		 ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
         executor.setCorePoolSize(5);
         executor.setMaxPoolSize(10);
         executor.setQueueCapacity(10);
         executor.initialize();
         return executor;
	}
*/
}
