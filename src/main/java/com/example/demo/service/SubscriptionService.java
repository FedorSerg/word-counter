package com.example.demo.service;

import lombok.NonNull;

public interface SubscriptionService {

  void follow(@NonNull String personLogin);

  void unfollow(@NonNull String personLogin);
}
