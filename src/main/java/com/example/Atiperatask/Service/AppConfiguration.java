package com.example.Atiperatask.Service;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class AppConfiguration {

    @Bean
    public HttpClient httpClient(){
        return HttpClient.newHttpClient();
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }
}
