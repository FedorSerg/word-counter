package com.example.generativeai.service.impl;

import static java.util.stream.Collectors.toList;

import com.example.generativeai.dto.PostDto;
import com.example.generativeai.entity.PersonEntity;
import com.example.generativeai.entity.PostEntity;
import com.example.generativeai.repository.AuthRepository;
import com.example.generativeai.repository.PostRepository;
import com.example.generativeai.service.AuthService;
import com.example.generativeai.service.PostService;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link PostService} providing operations related to posts.
 * This class includes methods for retrieving posts, creating posts, and updating likes on posts.
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final AuthService authService;
  private final AuthRepository authRepository;

  /**
   * Retrieves all posts.
   *
   * @return A list of {@link PostDto} representing all posts.
   */
  @Override
  public List<PostDto> getAllPosts() {
    return postRepository.findAll().stream()
        .map(this::mapEntityToDto).collect(toList());
  }

  /**
   * Retrieves posts authored by the users followed by the authenticated user.
   *
   * @return A list of {@link PostDto} representing posts by followed authors.
   */
  @Override
  public List<PostDto> getPostsByFollowedAuthors() {
    Long authPersonId = authService.checkIfLoggedInAndReturnAuthPersonId();
    return postRepository.findAllBySubscriptions(authPersonId).stream()
        .map(this::mapEntityToDto).collect(toList());
  }

  /**
   * Creates a new post and returns its representation.
   *
   * @param dto The {@link PostDto} containing post information.
   * @return The created {@link PostDto}.
   */
  @Override
  public PostDto createAndReturn(@NonNull PostDto dto) {
    authService.checkIfLoggedInAndReturnAuthPersonId();
    PersonEntity authPerson = authRepository.getAuthorizedPerson();
    PostEntity newEntity = PostEntity.builder()
        .title(dto.title()).body(dto.body()).author(authPerson)
        .build();
    return mapEntityToDto(postRepository.save(newEntity));
  }

  /**
   * Updates the like on a post and returns its updated representation.
   *
   * @param postId The ID of the post to update the like.
   * @return The updated {@link PostDto} with like count.
   */
  @Override
  public PostDto updateLikeAndReturn(@NonNull Long postId) {
    Long authPersonId = authService.checkIfLoggedInAndReturnAuthPersonId();
    return mapEntityToDto(postRepository.updateLikeOnPost(authPersonId, postId));
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
