package ru.diasoft.friendsphoto.managers;

import android.content.Context;

import com.squareup.picasso.Picasso;

import ru.diasoft.friendsphoto.network.PicassoCache;

public class DataManager {

    private static DataManager INSTANCE = null;
    private PreferencesManager mPreferencesManager;

    public Context getContext() {
        return mContext;
    }

    private Context mContext;

    private Picasso mPicasso;


    public DataManager(Context context) {
        this.mPreferencesManager = new PreferencesManager();
        this.mContext = context;
        this.mPicasso = new PicassoCache(mContext).getPicassoInstance();
    }

    public static DataManager getInstance(Context context) {
        if (INSTANCE==null) {
            INSTANCE = new DataManager(context.getApplicationContext());
        }
        return INSTANCE;
    }

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    public Picasso getPicasso() {
        return mPicasso;
    }
}
