package com.example.generativeai.service;

import com.example.generativeai.dto.PostDto;
import java.util.List;

public interface PostService {

  List<PostDto> getAllPosts();
}
