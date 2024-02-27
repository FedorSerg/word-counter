package com.example.generativeai.service;

import lombok.NonNull;

public interface SubscriptionService {

  void follow(@NonNull String personLogin);

  void unfollow(@NonNull String personLogin);
}
