package com.example.generativeai.service.impl;

import com.example.generativeai.dto.PersonDto;
import com.example.generativeai.exception.DeniedRequestException;
import com.example.generativeai.repository.AuthRepository;
import com.example.generativeai.service.AuthService;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceStub implements AuthService {

  private final AuthRepository authRepository;

  @Override
  public Long checkIfLoggedInAndReturnAuthPersonId() {
    Optional<PersonDto> authPerson = Optional.ofNullable(this.getAuthPersonInfo());
    if (authPerson.isEmpty()) {
      throw new DeniedRequestException("Not authorized");
    } else {
      return authPerson.get().getId();
    }
  }

  @Override
  public PersonDto getAuthPersonInfo() {
    return Optional.ofNullable(authRepository.getAuthorizedPerson())
        .map(p -> PersonDto.builder()
            .id(p.getId())
            .login(p.getLogin())
            .build())
        .orElse(null);
  }

  @Override
  public void login(@NonNull String personLogin) {
    authRepository.insertAuthorizedPerson(personLogin);
  }

  @Override
  public void logout() {
    authRepository.dropAuthorizedPerson();
  }
}
