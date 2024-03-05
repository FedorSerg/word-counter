package com.example.demo.repository;

import com.example.demo.entity.PersonEntity;

public interface AuthRepository {

  PersonEntity getAuthorizedPerson();

  void insertAuthorizedPerson(String personLogin);

  void dropAuthorizedPerson();

}
