package ru.diasoft.friendsphoto.network.resources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * POJO class for retrofit responses friends photos
 */
public class GalleryListRes {

    @SerializedName("response")
    @Expose
    private GalleryResponseListRes response;

    public GalleryResponseListRes getResponse() {
        return response;
    }

    public void setResponse(GalleryResponseListRes response) {
        this.response = response;
    }

}
