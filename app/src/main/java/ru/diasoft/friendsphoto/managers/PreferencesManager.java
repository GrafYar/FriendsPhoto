package ru.diasoft.friendsphoto.managers;

import android.content.SharedPreferences;

import ru.diasoft.friendsphoto.utils.ConstantManager;
import ru.diasoft.friendsphoto.utils.FriendsPhotoApplication;

public class PreferencesManager {

    private SharedPreferences mSharedPreferences;
    private static final String AUTH_TOKEN = ConstantManager.AUTH_TOKEN;

    public PreferencesManager() {
        this.mSharedPreferences = FriendsPhotoApplication.getSharedPreferences();
    }

    public void saveUserToken (String token) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(AUTH_TOKEN, token);
        editor.apply();
    }

    public String loadUserToken () {
        return mSharedPreferences.getString(ConstantManager.AUTH_TOKEN,"null");
    }
}
