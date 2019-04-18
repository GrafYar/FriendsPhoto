package ru.diasoft.friendsphoto.network.services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import ru.diasoft.friendsphoto.network.resources.FriendsListRes;
import ru.diasoft.friendsphoto.network.resources.GalleryListRes;

/**
 * Interface requests for retrofit
 */
public interface RestService {

    @POST("method/friends.get?")
    @FormUrlEncoded
    Call<FriendsListRes> getFriendsJson(@Field("v") String version, @Field("access_token") String token, @Field("fields") String fields);

    @POST("method/photos.get?")
    @FormUrlEncoded
    Call<GalleryListRes> getGalleryJson(@Field("v") String version, @Field("access_token") String token, @Field("owner_id") String ownerId, @Field("album_id") String albumId);
}
