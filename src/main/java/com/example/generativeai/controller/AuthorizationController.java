package com.example.generativeai.controller;

import com.example.generativeai.dto.PersonDto;
import com.example.generativeai.service.AuthService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {

  private final AuthService authService;

  @GetMapping("/me")
  public ResponseEntity<PersonDto> getAuthorizedUser() {
    return ResponseEntity.of(Optional.ofNullable(
        authService.getAuthPersonInfo()
    ));
  }

  @PostMapping("/login/{personLogin}")
  public ResponseEntity<Void> login(@PathVariable String personLogin) {
    authService.login(personLogin);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout() {
    authService.logout();
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
