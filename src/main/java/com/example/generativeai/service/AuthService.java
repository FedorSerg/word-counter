package com.example.generativeai.service;

import com.example.generativeai.dto.PersonDto;

public interface AuthService {

  Long checkIfLoggedInAndReturnAuthPersonId();

  PersonDto getAuthPersonInfo();

  void login(String personLogin);

  void logout();
}
