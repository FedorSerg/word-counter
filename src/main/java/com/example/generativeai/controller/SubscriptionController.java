package com.example.generativeai.controller;

import com.example.generativeai.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

  private final SubscriptionService subscriptionService;

  @PostMapping("/follow/{personLogin}")
  public ResponseEntity<Void> subscribeToPerson(@PathVariable String personLogin) {
    subscriptionService.follow(personLogin);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/unfollow/{personLogin}")
  public ResponseEntity<Void> unsubscribeFromPerson(@PathVariable String personLogin) {
    subscriptionService.unfollow(personLogin);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

