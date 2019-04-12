package ru.diasoft.friendsphoto.network.resources;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FriendsListRes {

    @SerializedName("response")
    @Expose
    private List<Integer> response = null;

    public List<Integer> getResponse() {
        return response;
    }

    public void setResponse(List<Integer> response) {
        this.response = response;
    }

}