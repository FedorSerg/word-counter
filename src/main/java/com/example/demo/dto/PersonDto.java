package com.example.demo.dto;

import lombok.Builder;

/**
 * Data Transfer Object (DTO) for representing a person.
 */
@Builder
public record PersonDto(Long id, String login) {
}
