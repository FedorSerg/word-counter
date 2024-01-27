package com.example.generativeai.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

  @PostMapping("/follow/{authorLogin}")
  public String subscribeToAuthor(@PathVariable String authorLogin) {
    return "You follow author now: " + authorLogin;
  }

  @PostMapping("/unfollow/{authorLogin}")
  public String unsubscribeFromAuthor(@PathVariable String authorLogin) {
    return "You've unfollowed the author: " + authorLogin;
  }
}
