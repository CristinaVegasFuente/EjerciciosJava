package com.example.democron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//crisss acuerdate de esta anotacion
//sin esta anotacion no se va a ejecutar nada programado en spring, es IMPRESCINDIBLE!!!
@EnableScheduling
@SpringBootApplication
public class DemocronApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemocronApplication.class, args);
	}

}
