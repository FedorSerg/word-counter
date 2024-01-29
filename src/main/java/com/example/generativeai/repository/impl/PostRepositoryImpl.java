package com.example.generativeai.repository.impl;

import com.example.generativeai.entity.PostEntity;
import com.example.generativeai.repository.PostRepository;
import com.example.generativeai.utils.HibernateSessionUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

  private final HibernateSessionUtils<PostEntity> hibernateSessionUtils;

  @Override
  public List<PostEntity> findAll() {
    return hibernateSessionUtils.findAllData(PostEntity.class);
  }
}
