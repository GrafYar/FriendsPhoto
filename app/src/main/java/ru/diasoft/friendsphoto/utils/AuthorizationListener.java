package ru.diasoft.friendsphoto.utils;

/**
 * Interface listener for login Activity
 */
public interface AuthorizationListener {
    void onAuthStarted();

    void onComplete(String token);

    void onError(String error);
}
