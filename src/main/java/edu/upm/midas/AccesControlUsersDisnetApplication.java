package edu.upm.midas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration
@EnableCircuitBreaker
@EnableHystrix
@EnableScheduling
public class AccesControlUsersDisnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccesControlUsersDisnetApplication.class, args);
	}
}
