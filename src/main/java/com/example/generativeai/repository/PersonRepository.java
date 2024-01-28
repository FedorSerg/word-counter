package com.example.generativeai.repository;

import com.example.generativeai.entity.PersonEntity;

public interface PersonRepository {

  PersonEntity findByLogin(String login);
}
