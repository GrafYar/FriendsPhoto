package ru.diasoft.friendsphoto.managers;

import android.content.Context;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import ru.diasoft.friendsphoto.utils.PicassoCache;
import ru.diasoft.friendsphoto.storage.models.DaoSession;
import ru.diasoft.friendsphoto.storage.models.Friend;
import ru.diasoft.friendsphoto.utils.FriendsPhotoApplication;

/**
 * Realizes singleton for single data access point
 */
public class DataManager {

    private static DataManager INSTANCE = null;
    private PreferencesManager mPreferencesManager;
    private DaoSession mDaoSession;
    private Picasso mPicasso;

    public DataManager(Context context) {
        this.mPreferencesManager = new PreferencesManager();
        this.mPicasso = new PicassoCache(context).getPicassoInstance();
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

    /**
     *Returns list of friends from Data Base
     * @return List<Friend>
     */
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
