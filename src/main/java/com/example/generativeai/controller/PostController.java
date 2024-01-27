package com.example.generativeai.controller;

import com.example.generativeai.dto.PostDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing activities with posts.
 */
@RestController
@RequestMapping("/posts")
public class PostController {

  @GetMapping("/all")
  public String getPostById() {
    return "Get all Posts";
  }

  @GetMapping("/all/followed")
  public String getPostsByFollowedAuthors() {
    return "Get Posts by followed authors";
  }

  @GetMapping("/authorLogin/{authorLogin}")
  public String getPostsByAuthorLogin(@PathVariable String authorLogin) {
    return "Get Posts by author: " + authorLogin;
  }

  @PostMapping
  public String createPost(@RequestBody PostDto dto) {
    return "Create Post: " + dto.getName();
  }

  @PutMapping("/update-like/{id}")
  public String updatePost(@PathVariable Long id, @RequestBody PostDto dto) {
    return "Update Post with ID " + id + " to: " + dto.getName();
  }
}