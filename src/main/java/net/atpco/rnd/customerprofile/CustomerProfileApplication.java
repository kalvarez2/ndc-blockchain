package net.atpco.rnd.customerprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("net.atpco.rnd.customerprofile")
public class CustomerProfileApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerProfileApplication.class, args);
	}
}
