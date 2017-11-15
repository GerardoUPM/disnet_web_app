package edu.upm.midas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccesControlUsersDisnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccesControlUsersDisnetApplication.class, args);
	}
}
