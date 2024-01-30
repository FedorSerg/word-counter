package com.example.generativeai.config;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Postgres testcontainer configuration.
 */
public class PostgresTestConfig {
  public static final PostgreSQLContainer postgreSQLContainer;

  static {
    postgreSQLContainer = new PostgreSQLContainer<>("postgres:10.12")
        .withUsername("test")
        .withPassword("test")
        .withDatabaseName("test");
    postgreSQLContainer.start();
  }
}

