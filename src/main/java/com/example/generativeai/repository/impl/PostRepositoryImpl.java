package com.example.generativeai.repository.impl;

import com.example.generativeai.entity.PostEntity;
import com.example.generativeai.repository.PostRepository;
import com.example.generativeai.utils.HibernateSessionFactoryUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

  private final HibernateSessionFactoryUtils hibernateSessionFactoryUtils;

  @Override
  public List<PostEntity> findAll() {
    return hibernateSessionFactoryUtils.findAllData(PostEntity.class);
  }
}
