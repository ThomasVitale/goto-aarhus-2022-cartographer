package com.thomasvitale.musicservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicServiceApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions.route()
			.GET("/", request -> ServerResponse.ok().bodyValue(List.of(
				new Music("Måneskin"),
				new Music("Guns 'n' Roses"),
				new Music("Led Zeppelin")
			)))
			.build();
	}

}

record Music(String artist){}
