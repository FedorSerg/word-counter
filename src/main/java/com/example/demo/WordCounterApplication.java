package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WordCounterApplication {

  public static void main(String[] args) {
    SpringApplication.run(WordCounterApplication.class, args);
    System.out.println("http://localhost:8080/swagger-ui/index.html#/");
  }

}
