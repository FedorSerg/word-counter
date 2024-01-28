package com.example.generativeai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.generativeai.entity"})
public class GenerativeaiApplication {

  public static void main(String[] args) {
    SpringApplication.run(GenerativeaiApplication.class, args);
    System.out.println("http://localhost:8080/swagger-ui/index.html#/");
  }

}
