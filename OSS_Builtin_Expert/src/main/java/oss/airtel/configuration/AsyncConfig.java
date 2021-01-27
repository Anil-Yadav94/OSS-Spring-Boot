package oss.airtel.configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer, WebMvcConfigurer {

	@Bean(name = "asyncExecutor")
	public Executor asyncExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(250);
	    executor.setMaxPoolSize(500);
	    executor.setQueueCapacity(1000);
	    executor.setThreadNamePrefix("XMLFetchAsyncThread-");
	    executor.setAllowCoreThreadTimeOut(true);
	    executor.setKeepAliveSeconds(100);
	    executor.initialize();
	    return executor;
	}
	
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
	    return new SimpleAsyncUncaughtExceptionHandler();
	}
	
	@Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(5)));
    }
	
//	@Bean
//	protected WebMvcConfigurer webMvcConfigurer() {
//	    return new WebMvcConfigurerAdapter() {
//	        @Override
//	        public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//	            configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(5)));
//	        }
//	    };
//	}
	
}
