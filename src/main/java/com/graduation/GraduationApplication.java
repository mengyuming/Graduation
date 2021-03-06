package com.graduation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
//@EnableCaching
@EnableAsync
public class GraduationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraduationApplication.class, args);
	}
}
