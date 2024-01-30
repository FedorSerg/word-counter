package com.example.generativeai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing a person.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonDto {
  private Long id;
  private String login;

}
