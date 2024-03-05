package com.example.demo.repository;

import com.example.demo.entity.PersonEntity;

public interface PersonRepository {

  PersonEntity findByLogin(String login);
}
