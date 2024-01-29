package com.example.generativeai.service.impl;

import static java.util.stream.Collectors.toList;

import com.example.generativeai.dto.PostDto;
import com.example.generativeai.repository.PostRepository;
import com.example.generativeai.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;

  @Override
  public List<PostDto> getAllPosts() {
    return postRepository.findAll().stream()
        .map(p -> PostDto.builder().id(p.getId())
            .title(p.getTitle())
            .body(p.getBody())
            .authorLogin(p.getAuthor().getLogin())
            .build())
        .collect(toList());
  }
}
