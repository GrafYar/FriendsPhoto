
package ru.diasoft.friendsphoto.network.resources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * POJO class for retrofit responses of friends
 */
public class FriendsItemRes {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("photo_100")
    @Expose
    private String photo100;
    @SerializedName("online")
    @Expose
    private Integer online;
    @SerializedName("online_app")
    @Expose
    private String onlineApp;
    @SerializedName("online_mobile")
    @Expose
    private Integer onlineMobile;
    @SerializedName("deactivated")
    @Expose
    private String deactivated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto100() {
        return photo100;
    }

    public void setPhoto100(String photo50) {
        this.photo100 = photo50;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public String getOnlineApp() {
        return onlineApp;
    }

    public void setOnlineApp(String onlineApp) {
        this.onlineApp = onlineApp;
    }

    public Integer getOnlineMobile() {
        return onlineMobile;
    }

    public void setOnlineMobile(Integer onlineMobile) {
        this.onlineMobile = onlineMobile;
    }

    public String getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(String deactivated) {
        this.deactivated = deactivated;
    }

}
