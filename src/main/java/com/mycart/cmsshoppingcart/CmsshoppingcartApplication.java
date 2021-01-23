package com.mycart.cmsshoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableAutoConfiguration
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class CmsshoppingcartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsshoppingcartApplication.class, args);
	}

}
