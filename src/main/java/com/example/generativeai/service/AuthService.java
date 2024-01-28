package com.example.generativeai.service;

import com.example.generativeai.dto.PersonDto;

public interface AuthService {

  boolean checkLogin();

  PersonDto getAuthPersonInfo();

  void login(String personLogin);

  void logout();
}
