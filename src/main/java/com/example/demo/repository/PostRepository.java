package com.example.demo.repository;

import com.example.demo.entity.PostEntity;
import java.util.List;

public interface PostRepository {

  List<PostEntity> findAll();

  List<PostEntity> findAllBySubscriptions(Long authPersonId);

  PostEntity save(PostEntity post);

  PostEntity updateLikeOnPost(Long authPersonId, Long postId);
}
