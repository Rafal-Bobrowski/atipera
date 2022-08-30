package com.example.Atiperatask;

import com.example.Atiperatask.Service.GithubInfoDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class AtiperaTaskApplication {

	public static void main(String[] args){
		SpringApplication.run(AtiperaTaskApplication.class, args);
	}

}
