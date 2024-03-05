package com.example.demo.controller;

import com.example.demo.dto.PostDto;
import com.example.demo.service.PostService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  /**
   * Get all posts.
   */
  @GetMapping("/all")
  public ResponseEntity<List<PostDto>> getAllPosts() {
    return ResponseEntity.of(Optional.of(
        postService.getAllPosts()
    ));
  }

  /**
   * Get posts by followed authors.
   */
  @GetMapping("/all/followed")
  public ResponseEntity<List<PostDto>> getPostsByFollowedAuthors() {
    return ResponseEntity.of(Optional.of(
        postService.getPostsByFollowedAuthors()
    ));
  }

  /**
   * Create a new post.
   */
  @PostMapping
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto dto) {
    return ResponseEntity.of(Optional.of(
        postService.createAndReturn(dto)
    ));
  }

  /**
   * Like or remove like from a post.
   */
  @PutMapping("/update-like/{id}")
  public ResponseEntity<PostDto> updateLikeOnPost(@PathVariable Long id) {
    return ResponseEntity.of(Optional.of(
        postService.updateLikeAndReturn(id)
    ));
  }
}
