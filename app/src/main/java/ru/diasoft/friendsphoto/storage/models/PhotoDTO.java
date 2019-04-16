package ru.diasoft.friendsphoto.storage.models;

import android.os.Parcel;
import android.os.Parcelable;

import ru.diasoft.friendsphoto.network.resources.GalleryItemRes;

public class PhotoDTO implements Parcelable {

    private String mPhoto75;
    private String mPhoto130;
    private String mPhoto604;
    private String mPhoto807;
    private String mPhoto1280;
    private String mPhoto2560;

    public PhotoDTO(GalleryItemRes galleryItemRes) {
        this.mPhoto75 = galleryItemRes.getPhoto75();
        this.mPhoto130 = galleryItemRes.getPhoto130();
        this.mPhoto604 = galleryItemRes.getPhoto604();
        this.mPhoto807 = galleryItemRes.getPhoto807();
        this.mPhoto1280 = galleryItemRes.getPhoto1280();
        this.mPhoto2560 = galleryItemRes.getPhoto2560();
    }


    protected PhotoDTO(Parcel in) {
        mPhoto75 = in.readString();
        mPhoto130 = in.readString();
        mPhoto604 = in.readString();
        mPhoto807 = in.readString();
        mPhoto1280 = in.readString();
        mPhoto2560 = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPhoto75);
        dest.writeString(mPhoto130);
        dest.writeString(mPhoto604);
        dest.writeString(mPhoto807);
        dest.writeString(mPhoto1280);
        dest.writeString(mPhoto2560);
    }

    public String getPhoto75() {
        return mPhoto75;
    }

    public String getPhoto130() {
        return mPhoto130;
    }

    public String getPhoto604() {
        return mPhoto604;
    }

    public String getPhoto807() {
        return mPhoto807;
    }

    public String getPhoto1280() {
        return mPhoto1280;
    }

    public String getPhoto2560() {
        return mPhoto2560;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PhotoDTO> CREATOR = new Parcelable.Creator<PhotoDTO>() {
        @Override
        public PhotoDTO createFromParcel(Parcel in) {
            return new PhotoDTO(in);
        }

        @Override
        public PhotoDTO[] newArray(int size) {
            return new PhotoDTO[size];
        }
    };
}
