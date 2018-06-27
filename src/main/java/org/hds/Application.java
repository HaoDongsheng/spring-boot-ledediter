package org.hds;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("org.hds.mapper")
@ComponentScan(basePackages = {"org.hds.service","org.hds.service.impl","org.hds.web"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}