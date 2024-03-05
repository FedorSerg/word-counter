package com.example.demo.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.AbstractTest;
import com.example.demo.dto.PostDto;
import com.fasterxml.jackson.core.type.TypeReference;
import java.nio.charset.StandardCharsets;
import java.util.List;
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

  @Test
  void testPostsByFollowedAuthors_notAuthorized() throws Exception {
    mvc.perform(get("/auth/me").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    mvc.perform(get("/posts/all/followed").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void testPostsByFollowedAuthors() throws Exception {
    mvc.perform(post("/auth/login/{personLogin}", JOHN_DOE_LOGIN)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    mvc.perform(get("/posts/all/followed").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(jsonPath("$.length()").value(6));

    mvc.perform(post("/auth/login/{personLogin}", EMMA_WILSON_LOGIN)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    mvc.perform(get("/posts/all/followed").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  void testCreatePost_notAuthorized() throws Exception {
    mvc.perform(get("/auth/me").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    mvc.perform(post("/posts").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(PostDto.builder().build())))
        .andExpect(status().isForbidden()); // not authorized
  }

  @Test
  void testCreatePost() throws Exception {
    String newPostTitle = "New Post Title";

    String result = mvc.perform(get("/posts/all").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn().getResponse()
        .getContentAsString(StandardCharsets.UTF_8);
    List<PostDto> allPosts = objectMapper.readValue(result, new TypeReference<>() {});
    assertTrue(allPosts.stream().noneMatch(p -> p.title().equals(newPostTitle)));
    PostDto newPost = PostDto.builder().title(newPostTitle).body("text").build();

    mvc.perform(post("/auth/login/{personLogin}", JOHN_DOE_LOGIN)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    result = mvc.perform(post("/posts").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newPost)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    PostDto createdPost = objectMapper.readValue(result, PostDto.class);

    assertEquals(newPostTitle, createdPost.title());
    assertEquals(newPost.body(), createdPost.body());
    assertEquals(JOHN_DOE_LOGIN, createdPost.authorLogin());

    result = mvc.perform(get("/posts/all").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn().getResponse()
        .getContentAsString(StandardCharsets.UTF_8);
    allPosts = objectMapper.readValue(result, new TypeReference<>() {});
    assertTrue(allPosts.stream().anyMatch(p -> p.title().equals(newPostTitle)));
  }

  @Test
  void testUpdateLikeOnPost_notAuthorized() throws Exception {
    mvc.perform(get("/auth/me").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    mvc.perform(put("/posts/update-like/{id}", 1)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void testUpdateLikeOnPost() throws Exception {
    String result = mvc.perform(get("/posts/all").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn().getResponse()
        .getContentAsString(StandardCharsets.UTF_8);
    List<PostDto> allPosts = objectMapper.readValue(result, new TypeReference<>() {});
    PostDto post = allPosts.stream().findAny().orElseThrow();
    int likeCount = post.likeCount();

    mvc.perform(post("/auth/login/{personLogin}", EMMA_WILSON_LOGIN)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    mvc.perform(put("/posts/update-like/{id}", post.id())
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    result = mvc.perform(get("/posts/all").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn().getResponse()
        .getContentAsString(StandardCharsets.UTF_8);
    allPosts = objectMapper.readValue(result, new TypeReference<>() {});
    PostDto updatedPost = allPosts.stream().filter(p -> p.id().equals(post.id()))
        .findFirst().orElseThrow();
    assertEquals(likeCount + 1, updatedPost.likeCount());

    // unlike post
    mvc.perform(put("/posts/update-like/{id}", post.id())
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    result = mvc.perform(get("/posts/all").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn().getResponse()
        .getContentAsString(StandardCharsets.UTF_8);
    allPosts = objectMapper.readValue(result, new TypeReference<>() {});
    updatedPost = allPosts.stream().filter(p -> p.id().equals(post.id()))
        .findFirst().orElseThrow();
    assertEquals(likeCount, updatedPost.likeCount());
  }
}
