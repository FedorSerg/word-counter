package com.example.generativeai.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test config for integration tests.
 * Init database. Disable security.
 */
@ComponentScan(value = "com.example.generativeai")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = {PostgresTestConfig.class, FlywayAutoConfiguration.class})
public class TestConfig {
}
