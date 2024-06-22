package com.example.aluracursos.challengeLiteralura;

import com.example.aluracursos.challengeLiteralura.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Literalura2Application implements CommandLineRunner {

	@Autowired
	private Principal principal;

	public static void main(String[] args) {
		SpringApplication.run(Literalura2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.muestraElMenu();
	}
}
