package com.example.demo.controller;

import com.example.demo.dto.PersonDto;
import com.example.demo.service.AuthService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user authentication and authorization.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {

  private final AuthService authService;

  /**
   * Get information about the currently authorized user.
   */
  @GetMapping("/me")
  public ResponseEntity<PersonDto> getAuthorizedUser() {
    return ResponseEntity.of(Optional.ofNullable(
        authService.getAuthPersonInfo()
    ));
  }

  /**
   * Log in a user with the specified login.
   */
  @PostMapping("/login/{personLogin}")
  public ResponseEntity<Void> login(@PathVariable String personLogin) {
    authService.login(personLogin);
    return ResponseEntity.ok().build();
  }

  /**
   * Log out the currently authorized user.
   */
  @PostMapping("/logout")
  public ResponseEntity<Void> logout() {
    authService.logout();
    return ResponseEntity.ok().build();
  }

}
