package oss.airtel;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class OssSupportApplication {
	
	@Bean(name = "asyncExecutor")
	public Executor asyncExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(200);
	    executor.setMaxPoolSize(500);
	    executor.setQueueCapacity(1000);
	    executor.setThreadNamePrefix("XMLFetchAsyncThread-");
	    executor.initialize();
	    return executor;
	}

	public static void main(String[] args) {
		SpringApplication.run(OssSupportApplication.class, args);
	}

}
