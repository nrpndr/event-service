package com.cineevent.eventservice;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventServiceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EventServiceApplication.class);
		app.setBannerMode(Mode.OFF);
		app.run(args);
	}

}
