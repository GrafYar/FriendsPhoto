package ru.diasoft.friendsphoto.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;

import ru.diasoft.friendsphoto.storage.models.DaoMaster;
import ru.diasoft.friendsphoto.storage.models.DaoSession;


public class FriendsPhotoApplication extends Application {
    public static SharedPreferences sSharedPreferences;

    private static DaoSession sDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "freindsphoto-db");
        Database db = helper.getWritableDb();
        sDaoSession = new DaoMaster(db).newSession();

        
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }

    public static DaoSession getDaoSession() {
        return sDaoSession;
    }
}
