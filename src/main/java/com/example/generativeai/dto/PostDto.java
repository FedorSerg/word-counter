package com.example.generativeai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
