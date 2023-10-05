package ru.secteam.teamwork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class TeamworkApplication {
	public static void main(String[] args) {
		SpringApplication.run(TeamworkApplication.class, args);
		log.info("Бот запущен");
	}

}
