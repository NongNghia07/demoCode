package com.example.demo;

import com.example.demo.DTO.RoleDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class DemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(
//			AuthenticationService service
//	) {
//		return args -> {
//			Set<RoleDTO> roleDTOS = new HashSet<>();
//			roleDTOS.add(new RoleDTO(2, ""));
//			roleDTOS.add(new RoleDTO(3, ""));
//			var admin = UserDTO.builder()
//					.hoTen("Hoàng Khánh Nhi")
//					.email("nhi@gmail.com")
//					.password("123")
//					.roles(roleDTOS)
//					.build();
//			service.register(admin).getAccessToken();
//		};
//	}
}
