
package ru.diasoft.friendsphoto.network.resources;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseListRes {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("items")
    @Expose
    private List<ItemRes> items = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ItemRes> getItems() {
        return items;
    }

    public void setItems(List<ItemRes> items) {
        this.items = items;
    }

}
