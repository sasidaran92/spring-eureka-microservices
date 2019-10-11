package com.sasidaran.movieinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieInfoServiceApplication {

    public static void main(String[] args) {
        String s = new String("Hello World");
        s= "hello world 2";
        SpringApplication.run(MovieInfoServiceApplication.class, args);
    }

}
