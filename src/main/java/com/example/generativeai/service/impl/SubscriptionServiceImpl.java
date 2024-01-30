package com.example.generativeai.service.impl;

import com.example.generativeai.repository.SubscriptionRepository;
import com.example.generativeai.service.AuthService;
import com.example.generativeai.service.SubscriptionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

  private final AuthService authService;
  private final SubscriptionRepository subscriptionRepository;

  @Override
  public void follow(@NonNull String personLogin) {
    Long authPersonId = authService.checkIfLoggedInAndReturnAuthPersonId();
    subscriptionRepository.follow(authPersonId, personLogin);
  }

  @Override
  public void unfollow(@NonNull String personLogin) {
    Long authPersonId = authService.checkIfLoggedInAndReturnAuthPersonId();
    subscriptionRepository.unfollow(authPersonId, personLogin);
  }
}
