package com.example.generativeai.service.impl;

import com.example.generativeai.dto.PersonDto;
import com.example.generativeai.exception.DeniedRequestException;
import com.example.generativeai.repository.AuthRepository;
import com.example.generativeai.service.AuthService;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link AuthService} providing stubbed authentication operations.
 *
 * <p>This class provides methods for checking authentication status, retrieving
 * authenticated person information, logging in, and logging out.</p>
 */
@Service
@RequiredArgsConstructor
public class AuthServiceStub implements AuthService {

  private final AuthRepository authRepository;

  /**
   * Checks if a user is logged in and returns the ID of the authenticated person.
   *
   * @return The ID of the authenticated person.
   * @throws DeniedRequestException If the request is not authorized.
   */
  @Override
  public Long checkIfLoggedInAndReturnAuthPersonId() {
    Optional<PersonDto> authPerson = Optional.ofNullable(this.getAuthPersonInfo());
    if (authPerson.isEmpty()) {
      throw new DeniedRequestException("Not authorized");
    } else {
      return authPerson.get().id();
    }
  }

  /**
   * Retrieves information about the authenticated person.
   *
   * @return A {@link PersonDto} representing the authenticated person.
   */
  @Override
  public PersonDto getAuthPersonInfo() {
    return Optional.ofNullable(authRepository.getAuthorizedPerson())
        .map(p -> PersonDto.builder()
            .id(p.getId())
            .login(p.getLogin())
            .build())
        .orElse(null);
  }

  /**
   * Logs in a user with the specified login.
   *
   * @param personLogin The login of the person to log in.
   */
  @Override
  public void login(@NonNull String personLogin) {
    authRepository.insertAuthorizedPerson(personLogin);
  }

  /**
   * Logs out the authenticated user.
   */
  @Override
  public void logout() {
    authRepository.dropAuthorizedPerson();
  }
}
