package ru.diasoft.friendsphoto.utils;

import android.content.Context;
import com.squareup.picasso.Picasso;
import com.jakewharton.picasso.OkHttp3Downloader;

/**
 * Class for Caching pictures in Picasso
 */
public class PicassoCache {

    private Context mContext;
    private Picasso mPicassoInstance;

    public PicassoCache(Context context) {
        this.mContext = context;
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(context, Integer.MAX_VALUE);
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(okHttp3Downloader);

        mPicassoInstance = builder.build();
        Picasso.setSingletonInstance(mPicassoInstance);
    }

    public Picasso getPicassoInstance() {
        if(mPicassoInstance == null) {
            new PicassoCache(mContext);
            return mPicassoInstance;
        }

        return mPicassoInstance;
    }

}
