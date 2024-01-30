package com.example.generativeai.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.generativeai.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class AuthorizationControllerTest extends AbstractTest {

  private static final String JOHN_DOE_LOGIN = "JohnDoe";
  private static final String EMMA_WILSON_LOGIN = "EmmaWilson";

  @Test
  void testAuthorization() throws Exception {
    mvc.perform(get("/auth/me").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    mvc.perform(post("/auth/login/{personLogin}", JOHN_DOE_LOGIN)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    mvc.perform(get("/auth/me").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.login").value(JOHN_DOE_LOGIN));

    mvc.perform(post("/auth/login/{personLogin}", EMMA_WILSON_LOGIN)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    mvc.perform(get("/auth/me").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.login").value(EMMA_WILSON_LOGIN));

    mvc.perform(post("/auth/logout")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    mvc.perform(get("/auth/me").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
