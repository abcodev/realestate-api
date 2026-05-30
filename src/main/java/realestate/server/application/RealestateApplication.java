package realestate.server.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class realestateApplication {

	public static void main(String[] args) {
		SpringApplication.run(realestateApplication.class, args);
	}

}
