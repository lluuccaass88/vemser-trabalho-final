package br.com.logisticadbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class
LogisticaDbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticaDbcApplication.class, args);
	}


}
