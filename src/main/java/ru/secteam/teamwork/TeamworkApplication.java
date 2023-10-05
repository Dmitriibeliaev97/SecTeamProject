package ru.secteam.teamwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeamworkApplication {
	private static final Logger logger = LoggerFactory.getLogger(TeamworkApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TeamworkApplication.class, args);
		logger.info("Бот запущен");
	}

}
