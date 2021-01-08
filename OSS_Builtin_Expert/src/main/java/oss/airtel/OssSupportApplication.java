package oss.airtel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OssSupportApplication {

	public static void main(String[] args) {
		SpringApplication.run(OssSupportApplication.class, args);
	}

}
