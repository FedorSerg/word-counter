package com.example.generativeai.repository;

public interface SubscriptionRepository {
  void follow(Long authPersonId, String personToFollowLogin);

  void unfollow(Long authPersonId, String personToUnfollowLogin);
}
