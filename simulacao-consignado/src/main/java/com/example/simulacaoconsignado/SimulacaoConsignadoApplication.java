package com.example.simulacaoconsignado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SimulacaoConsignadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulacaoConsignadoApplication.class, args);
	}

}
