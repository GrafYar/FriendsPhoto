package ru.diasoft.friendsphoto.network.services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import ru.diasoft.friendsphoto.network.resources.FriendsListRes;

public interface RestService {

    @POST("method/friends.getOnline?")
    @FormUrlEncoded
    Call<FriendsListRes> getMainJson(@Field("v") String version, @Field("access_token") String token);
}
