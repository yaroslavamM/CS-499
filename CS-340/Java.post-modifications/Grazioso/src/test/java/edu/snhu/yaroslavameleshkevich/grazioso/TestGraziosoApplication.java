package edu.snhu.yaroslavameleshkevich.grazioso;

import org.springframework.boot.SpringApplication;

public class TestGraziosoApplication {

	public static void main(String[] args) {
		SpringApplication.from(GraziosoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
