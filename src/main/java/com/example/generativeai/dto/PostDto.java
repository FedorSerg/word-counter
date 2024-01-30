package com.example.generativeai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing a post.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostDto {
  private Long id;
  private String title;
  private String body;
  private String authorLogin;
  private Integer likeCount;
}
