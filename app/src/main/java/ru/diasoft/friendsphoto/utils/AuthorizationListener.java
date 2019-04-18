package ru.diasoft.friendsphoto.utils;

public interface AuthorizationListener {
    void onAuthStarted();

    void onComplete(String token);

    void onError(String error);
}
