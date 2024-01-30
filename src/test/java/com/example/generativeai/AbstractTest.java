package com.example.generativeai;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.example.generativeai.config.TestPostgresqlContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles({"test", "tc", "tc-auto"})
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Testcontainers
public abstract class AbstractTest {

  @Autowired
  protected MockMvc mvc;
  @Autowired
  protected ObjectMapper objectMapper;

  @Container
  public static PostgreSQLContainer<TestPostgresqlContainer> postgreSQLContainer =
      TestPostgresqlContainer.getInstance();
}
