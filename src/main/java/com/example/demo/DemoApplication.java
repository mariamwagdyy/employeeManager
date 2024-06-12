package com.example.demo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.exc.StreamWriteException;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws StreamWriteException, IOException {
		SpringApplication.run(DemoApplication.class, args);
		EmployeeController employeeController = new EmployeeController();
		employeeController.createOrLoadFile(employeeController.filePath);
	}

}
