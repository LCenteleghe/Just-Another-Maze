package br.edu.unisinos.pgr.lcenteleghe.assync;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Scheduler {

	public static void repeate(final long millis, String methodName, final Object object){
		try {
			if(millis <= 0){
				throw new IllegalArgumentException("The argument  'milis' must be greater than zero.");
			}
			
			final Method method = object.getClass().getMethod(methodName, null);
			
			new Thread (new Runnable(){
			    @Override
			    public void run(){
			    	while(true){
			    		try {
							method.invoke(object, null);
				    		Thread.sleep(millis);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException | InterruptedException e) {
							throw new RuntimeException(e);
						}

			    	}
			    }
			}).start();
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
		
		
	}

}
