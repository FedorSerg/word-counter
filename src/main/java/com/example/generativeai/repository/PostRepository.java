package com.example.generativeai.repository;

import com.example.generativeai.entity.PostEntity;
import java.util.List;

public interface PostRepository {

  List<PostEntity> findAll();
}
