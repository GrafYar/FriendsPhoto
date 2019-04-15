
package ru.diasoft.friendsphoto.network.resources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FriendsListRes {

    @SerializedName("response")
    @Expose
    private FriendsResponseListRes response;

    public FriendsResponseListRes getResponse() {
        return response;
    }

    public void setResponse(FriendsResponseListRes response) {
        this.response = response;
    }

}
