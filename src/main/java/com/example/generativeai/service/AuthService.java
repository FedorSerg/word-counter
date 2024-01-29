package com.example.generativeai.service;

import com.example.generativeai.dto.PersonDto;

public interface AuthService {

  PersonDto getAuthPersonInfo();

  void login(String personLogin);

  void logout();
}
