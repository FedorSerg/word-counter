package com.example.generativeai.repository;

import com.example.generativeai.entity.PersonEntity;

public interface AuthRepository {

  PersonEntity getAuthorizedPerson();

  void insertAuthorizedPerson();

  void dropAuthorizedPerson();

}
