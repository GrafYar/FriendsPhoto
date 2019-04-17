package ru.diasoft.friendsphoto.managers;

import android.content.Context;

import com.squareup.picasso.Picasso;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

import ru.diasoft.friendsphoto.network.PicassoCache;
import ru.diasoft.friendsphoto.storage.models.DaoMaster;
import ru.diasoft.friendsphoto.storage.models.DaoSession;
import ru.diasoft.friendsphoto.storage.models.Friend;
import ru.diasoft.friendsphoto.utils.FriendsPhotoApplication;

public class DataManager {

    private static DataManager INSTANCE = null;
    private PreferencesManager mPreferencesManager;

    private DaoSession mDaoSession;

    public Context getContext() {
        return mContext;
    }

    private Context mContext;

    private Picasso mPicasso;


    public DataManager(Context context) {
        this.mPreferencesManager = new PreferencesManager();
        this.mContext = context;
        this.mPicasso = new PicassoCache(mContext).getPicassoInstance();
        this.mDaoSession = FriendsPhotoApplication.getDaoSession();
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

    public List<Friend> getFriendListFromDb() {
        List<Friend> friendList = new ArrayList<>();
        try {
            friendList = mDaoSession.queryBuilder(Friend.class).build().list();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return friendList;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
