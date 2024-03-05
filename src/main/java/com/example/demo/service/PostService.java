package com.example.demo.service;

import com.example.demo.dto.PostDto;
import java.util.List;

public interface PostService {

  List<PostDto> getAllPosts();

  List<PostDto> getPostsByFollowedAuthors();

  PostDto createAndReturn(PostDto dto);

  PostDto updateLikeAndReturn(Long id);
}
