package com.example.demo.controller;

import com.example.demo.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing user subscriptions.
 */
@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

  private final SubscriptionService subscriptionService;

  /**
   * Follow a person.
   */
  @PostMapping("/follow/{personLogin}")
  public ResponseEntity<Void> followPerson(@PathVariable String personLogin) {
    subscriptionService.follow(personLogin);
    return ResponseEntity.ok().build();
  }

  /**
   * Unfollow a person.
   */
  @PostMapping("/unfollow/{personLogin}")
  public ResponseEntity<Void> unfollowPerson(@PathVariable String personLogin) {
    subscriptionService.unfollow(personLogin);
    return ResponseEntity.ok().build();
  }
}

