
package ru.diasoft.friendsphoto.network.resources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FriendsListRes {

    @SerializedName("response")
    @Expose
    private ResponseListRes response;

    public ResponseListRes getResponse() {
        return response;
    }

    public void setResponse(ResponseListRes response) {
        this.response = response;
    }

}
