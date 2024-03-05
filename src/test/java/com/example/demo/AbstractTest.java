package com.example.demo;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.config.TestPostgresqlContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

  protected static final String JOHN_DOE_LOGIN = "JohnDoe";
  protected static final String EMMA_WILSON_LOGIN = "EmmaWilson";

  @Container
  public static PostgreSQLContainer<TestPostgresqlContainer> postgreSQLContainer =
      TestPostgresqlContainer.getInstance();

  @BeforeEach
  void logout() throws Exception {
    mvc.perform(post("/auth/logout")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
