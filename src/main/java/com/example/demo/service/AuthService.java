package com.example.demo.service;

import com.example.demo.dto.PersonDto;

public interface AuthService {

  Long checkIfLoggedInAndReturnAuthPersonId();

  PersonDto getAuthPersonInfo();

  void login(String personLogin);

  void logout();
}
