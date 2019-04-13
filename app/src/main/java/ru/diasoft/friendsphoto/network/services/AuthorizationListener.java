package ru.diasoft.friendsphoto.network.services;

public interface AuthorizationListener {
    void onAuthStarted();

    void onComplete(String token);

    void onError(String error);
}
