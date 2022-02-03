package com.bruno.bdb;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Ireal Digital Bank", version = "0.0.1-SNAPSHOT", description = "Simple Digital Bank"))
@SecurityScheme(name = "bdb-api", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@EnableEncryptableProperties
public class BasicDigitalBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicDigitalBankApplication.class, args);
	}

}
