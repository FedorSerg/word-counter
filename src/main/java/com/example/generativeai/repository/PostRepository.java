package com.example.generativeai.repository;

import com.example.generativeai.entity.PersonEntity;
import com.example.generativeai.entity.PostEntity;
import java.util.List;

public interface PostRepository {

  List<PostEntity> findAll();

  List<PostEntity> findAllBySubscriptions(PersonEntity authPerson);

  PostEntity save(PostEntity post);

  PostEntity updateLikeOnPost(PersonEntity authPerson, Long postId);
}
