package com.example.generativeai.repository.impl;

import com.example.generativeai.entity.PersonEntity;
import com.example.generativeai.repository.AuthRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepositoryImpl implements AuthRepository {

  @Override
  public PersonEntity getAuthorizedPerson() {
    return null;
  }

  @Override
  public void insertAuthorizedPerson() {

  }

  @Override
  public void dropAuthorizedPerson() {

  }
}
