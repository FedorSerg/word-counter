package com.example.generativeai.dto;

import lombok.Builder;

/**
 * Data Transfer Object (DTO) for representing a post.
 */
@Builder
public record PostDto(Long id, String title, String body,
                      String authorLogin, Integer likeCount) {
}
