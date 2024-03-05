package com.example.demo.dto;

import lombok.Builder;

/**
 * Data Transfer Object (DTO) for representing a post.
 */
@Builder
public record PostDto(Long id, String title, String body,
                      String authorLogin, Integer likeCount) {
}
