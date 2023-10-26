package ru.secteam.teamwork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class TeamworkApplication {
	public static void main(String[] args) {
		SpringApplication.run(TeamworkApplication.class, args);
		log.info("Бот запущен");
	}

}
