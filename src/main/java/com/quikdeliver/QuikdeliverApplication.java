package com.quikdeliver;

import com.quikdeliver.entity.Role;
import com.quikdeliver.model.BucketName;
import com.quikdeliver.service.UserService;
import com.quikdeliver.utility.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableConfigurationProperties
public class QuikdeliverApplication {
	@RequestMapping("/")
	public String home() {
		return "Application Running Successfully!";
	}

	public static void main(String[] args) {
		SpringApplication.run(QuikdeliverApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			if(!userService.isRolesSet()){
				userService.saveRole(new Role(null,"ROLE_ADMIN"));
				userService.saveRole(new Role(null,"ROLE_VO"));
				userService.saveRole(new Role(null,"ROLE_DRIVER"));
				userService.saveRole(new Role(null,"ROLE_CUSTOMER"));
			}
		};
	}
}
