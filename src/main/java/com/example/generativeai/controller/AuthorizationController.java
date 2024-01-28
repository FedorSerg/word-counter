package com.example.generativeai.controller;

import com.example.generativeai.dto.PersonDto;
import com.example.generativeai.repository.PersonRepository;
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
  private final PersonRepository personRepository;

  @GetMapping("/find-by-login/{login}")
  public ResponseEntity<PersonDto> test(@PathVariable String login) {
    var q = personRepository.findByLogin(login);
    return ResponseEntity.of(Optional.of(
        PersonDto.builder().id(q.getId()).login(q.getLogin()).build()
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
