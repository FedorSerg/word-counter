package com.example.generativeai.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.generativeai.AbstractTest;
import com.example.generativeai.dto.PostDto;
import com.fasterxml.jackson.core.type.TypeReference;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class SubscriptionControllerTest extends AbstractTest {

  @Test
  void testFollowUnfollowPerson_notAuthorized() throws Exception {
    mvc.perform(get("/auth/me").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    mvc.perform(post("/subscriptions/follow/{personLogin}", JOHN_DOE_LOGIN)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
    mvc.perform(post("/subscriptions/unfollow/{personLogin}", JOHN_DOE_LOGIN)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void testFollowUnfollowPerson() throws Exception {
    mvc.perform(post("/auth/login/{personLogin}", JOHN_DOE_LOGIN)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    String result = mvc.perform(get("/posts/all/followed")
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    List<PostDto> followedPosts = objectMapper.readValue(result, new TypeReference<>() {});
    assertTrue(followedPosts.stream().noneMatch(p -> p.authorLogin().equals(EMMA_WILSON_LOGIN)));

    mvc.perform(post("/subscriptions/follow/{personLogin}", EMMA_WILSON_LOGIN)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    result = mvc.perform(get("/posts/all/followed")
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    followedPosts = objectMapper.readValue(result, new TypeReference<>() {});
    assertTrue(followedPosts.stream().anyMatch(p -> p.authorLogin().equals(EMMA_WILSON_LOGIN)));

    mvc.perform(post("/subscriptions/unfollow/{personLogin}", EMMA_WILSON_LOGIN)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    result = mvc.perform(get("/posts/all/followed")
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isNotEmpty())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    followedPosts = objectMapper.readValue(result, new TypeReference<>() {});
    assertTrue(followedPosts.stream().noneMatch(p -> p.authorLogin().equals(EMMA_WILSON_LOGIN)));
  }
}
