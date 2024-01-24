package br.com.parquimetro2adjt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Parquimetro2adjtApplication {

	public static void main(String[] args) {
		SpringApplication.run(Parquimetro2adjtApplication.class, args);
	}

}
