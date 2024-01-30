package com.example.generativeai.service.impl;

import com.example.generativeai.repository.SubscriptionRepository;
import com.example.generativeai.service.AuthService;
import com.example.generativeai.service.SubscriptionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link SubscriptionService} providing operations related to user subscriptions.
 *
 * <p>This class includes methods for following and unfollowing other users.</p>
 */
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

  private final AuthService authService;
  private final SubscriptionRepository subscriptionRepository;

  /**
   * Follows another user based on the provided login.
   *
   * @param personLogin The login of the user to follow.
   */
  @Override
  public void follow(@NonNull String personLogin) {
    Long authPersonId = authService.checkIfLoggedInAndReturnAuthPersonId();
    subscriptionRepository.follow(authPersonId, personLogin);
  }

  /**
   * Unfollows another user based on the provided login.
   *
   * @param personLogin The login of the user to unfollow.
   */
  @Override
  public void unfollow(@NonNull String personLogin) {
    Long authPersonId = authService.checkIfLoggedInAndReturnAuthPersonId();
    subscriptionRepository.unfollow(authPersonId, personLogin);
  }
}
