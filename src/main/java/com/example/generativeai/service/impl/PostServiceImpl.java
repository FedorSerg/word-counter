package com.example.generativeai.service.impl;

import static java.util.stream.Collectors.toList;

import com.example.generativeai.dto.PostDto;
import com.example.generativeai.entity.PersonEntity;
import com.example.generativeai.entity.PostEntity;
import com.example.generativeai.repository.AuthRepository;
import com.example.generativeai.repository.PostRepository;
import com.example.generativeai.service.PostService;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final AuthRepository authRepository;

  @Override
  public List<PostDto> getAllPosts() {
    return postRepository.findAll().stream()
        .map(this::mapEntityToDto).collect(toList());
  }

  @Override
  public List<PostDto> getPostsByFollowedAuthors() {
    PersonEntity authPerson = authRepository.getAuthorizedPerson();
    return postRepository.findAllBySubscriptions(authPerson).stream()
        .map(this::mapEntityToDto).collect(toList());
  }

  @Override
  public PostDto createAndReturn(@NonNull PostDto dto) {
    PersonEntity authPerson = authRepository.getAuthorizedPerson();
    PostEntity newEntity = PostEntity.builder()
        .title(dto.getTitle()).body(dto.getBody()).author(authPerson)
        .build();
    return mapEntityToDto(postRepository.save(newEntity));
  }

  @Override
  public PostDto updateLikeAndReturn(@NonNull Long postId) {
    PersonEntity authPerson = authRepository.getAuthorizedPerson();
    return mapEntityToDto(postRepository.updateLikeOnPost(authPerson, postId));
  }

  private PostDto mapEntityToDto(@NonNull PostEntity entity) {
    return PostDto.builder().id(entity.getId())
        .title(entity.getTitle())
        .body(entity.getBody())
        .authorLogin(entity.getAuthor().getLogin())
        .likeCount(entity.getLikedPersons().size())
        .build();
  }
}
