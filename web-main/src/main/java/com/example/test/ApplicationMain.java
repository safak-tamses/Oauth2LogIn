package com.example.test;


import com.example.test.Service.DefaultStart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ApplicationMain {
	private static DefaultStart defaultStart ;
	public ApplicationMain(DefaultStart defaultStart) {
		this.defaultStart = defaultStart;
	}

	public static void main(String[] args) {
		SpringApplication.run(ApplicationMain.class, args);
		defaultStart.createDefaultUser();
	}

}
