package com.example.generativeai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenerativeaiApplication {

  public static void main(String[] args) {
    SpringApplication.run(GenerativeaiApplication.class, args);
    System.out.println("http://localhost:8080/swagger-ui/index.html#/");
  }

}
