package com.example.generativeai.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.generativeai.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class PostControllerTest extends AbstractTest {

  @Test
  void testGetAllPosts() throws Exception {
    mvc.perform(get("/posts/all").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isNotEmpty());
  }
}
