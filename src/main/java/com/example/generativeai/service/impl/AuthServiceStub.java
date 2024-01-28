package com.example.generativeai.service.impl;

import com.example.generativeai.dto.PersonDto;
import com.example.generativeai.repository.AuthRepository;
import com.example.generativeai.repository.PersonRepository;
import com.example.generativeai.service.AuthService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceStub implements AuthService {

  private final AuthRepository authRepository;
  private final PersonRepository personRepository;

  @Override
  public boolean checkLogin() {
    return false;
  }

  @Override
  public PersonDto getAuthPersonInfo() {
    return null;
  }

  @Override
  public void login(@NonNull String personLogin) {
    authRepository.insertAuthorizedPerson();
  }

  @Override
  public void logout() {

  }
}
