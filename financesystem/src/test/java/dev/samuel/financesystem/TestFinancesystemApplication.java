package dev.samuel.financesystem;

import org.springframework.boot.SpringApplication;

public class TestFinancesystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(FinancesystemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
