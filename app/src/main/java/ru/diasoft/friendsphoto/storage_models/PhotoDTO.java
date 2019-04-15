package ru.diasoft.friendsphoto.storage_models;

import android.os.Parcel;
import android.os.Parcelable;

import ru.diasoft.friendsphoto.network.resources.GalleryItemRes;
import ru.diasoft.friendsphoto.network.resources.GalleryListRes;

public class PhotoDTO implements Parcelable {

    private String mPhoto;

    public PhotoDTO(GalleryItemRes photo) {
        this.mPhoto = photo.getPhoto604();
    }

    protected PhotoDTO(Parcel in) {
        mPhoto = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPhoto);
    }

    public String getPhoto() {
        return mPhoto;
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
