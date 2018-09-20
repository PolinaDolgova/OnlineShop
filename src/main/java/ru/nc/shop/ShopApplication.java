package ru.nc.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource("classpath:appconfig/appconfig-root.xml")
public class ShopApplication {

	public static void main(String[] args) throws Exception {

		SpringApplication.run(ShopApplication.class, args);
	}
}