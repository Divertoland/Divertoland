package com.websocket.divertoland;

import com.websocket.divertoland.domain.User;
import com.websocket.divertoland.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.websocket.divertoland")
public class DivertolandApplication {


	public static void main(String[] args) {
		SpringApplication.run(DivertolandApplication.class, args);
	}

}
