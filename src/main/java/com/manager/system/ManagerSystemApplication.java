package com.manager.system;

import com.manager.system.util.UserUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ManagerSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerSystemApplication.class, args);
	}

}
