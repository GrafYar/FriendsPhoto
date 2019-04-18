package ru.diasoft.friendsphoto.managers;

import android.content.SharedPreferences;
import ru.diasoft.friendsphoto.utils.ConstantManager;
import ru.diasoft.friendsphoto.utils.FriendsPhotoApplication;

/**
 * Realizes saving users small data
 */
public class PreferencesManager {

    private SharedPreferences mSharedPreferences;
    private static final String AUTH_TOKEN = ConstantManager.AUTH_TOKEN;
    private static final String AUTH_PINCODE = ConstantManager.AUTH_PINCODE;


    public PreferencesManager() {
        this.mSharedPreferences = FriendsPhotoApplication.getSharedPreferences();
    }

    /**
     * Saves user token for requests
     * @param token - user token
     */
    public void saveUserToken (String token) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(AUTH_TOKEN, token);
        editor.apply();
    }

    /**
     * Retuns user token for requests
     * @return user token
     */
    public String loadUserToken () {
        return mSharedPreferences.getString(ConstantManager.AUTH_TOKEN,null);
    }

    /**
     * Saves use pin code for fast auth
     * @param pinCode user pin code
     */
    public void saveUserPinCode (String pinCode) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(AUTH_PINCODE, pinCode);
        editor.apply();
    }

    /**
     * Returns use pin code for fast auth
     * @return user pin code
     */
    public String loadUserPinCode () {
        return mSharedPreferences.getString(ConstantManager.AUTH_PINCODE,null);
    }
}
