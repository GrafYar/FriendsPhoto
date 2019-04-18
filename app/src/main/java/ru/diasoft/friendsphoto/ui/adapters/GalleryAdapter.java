package ru.diasoft.friendsphoto.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.managers.DataManager;
import ru.diasoft.friendsphoto.network.resources.GalleryItemRes;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<GalleryItemRes> mGalleryList;
    private GalleryAdapter.ViewHolder.ItemGalleryClickListener mItemGalleryClickListener;

    public GalleryAdapter (Context context, ArrayList<GalleryItemRes> galleryList, GalleryAdapter.ViewHolder.ItemGalleryClickListener itemGalleryClickListener){
        this.mContext = context;
        this.mGalleryList = galleryList;
        this.mItemGalleryClickListener = itemGalleryClickListener;
    }

    @NonNull
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery,viewGroup,false);

        return new GalleryAdapter.ViewHolder(view, mGalleryList, mItemGalleryClickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull final GalleryAdapter.ViewHolder holder, int i) {
        final GalleryItemRes item = mGalleryList.get(i);

        // Trying load from cache if can't then load from internet
        DataManager.getInstance(mContext).getPicasso()
                .load(getMaxImage(item))
                .placeholder(mContext.getResources().getDrawable(R.drawable.camera_50))
                .error(mContext.getResources().getDrawable(R.drawable.camera_50))
                .fit()
                .centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.mGalleryImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("123", " load from cache");
                    }

                    @Override
                    public void onError() {
                        DataManager.getInstance(mContext).getPicasso()
                                .load(getMaxImage(item))
                                .placeholder(mContext.getResources().getDrawable(R.drawable.camera_50))
                                .error(mContext.getResources().getDrawable(R.drawable.camera_50))
                                .fit()
                                .centerCrop()
                                .into(holder.mGalleryImage, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Log.d("123", " Can not fetch image");
                                    }
                                });
                    }
                });
    }

    /**
     * Returns max image for current object
     * @param item object of photos
     * @return max image for current object
     */
    private String getMaxImage(GalleryItemRes item) {
        if (item.getPhoto2560()!= null) {
            return item.getPhoto2560();
        } else if (item.getPhoto1280()!= null) {
            return item.getPhoto1280();
        } else if (item.getPhoto807()!= null) {
            return item.getPhoto807();
        } else if (item.getPhoto604()!= null) {
            return item.getPhoto604();
        } else if (item.getPhoto130()!= null) {
            return item.getPhoto130();
        } else if (item.getPhoto75()!= null) {
            return item.getPhoto75();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mGalleryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.gallery_img) ImageView mGalleryImage;
        int mId;
        private ArrayList<GalleryItemRes> mGalleryList;
        private GalleryAdapter.ViewHolder.ItemGalleryClickListener mItemGalleryClickListener;

        private ViewHolder(View itemView, ArrayList<GalleryItemRes> galleryList, GalleryAdapter.ViewHolder.ItemGalleryClickListener itemGalleryClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mItemGalleryClickListener = itemGalleryClickListener;
            itemView.setOnClickListener(this);
            this.mGalleryList = galleryList;
        }

        @Override
        public void onClick(View v) {
            if(mItemGalleryClickListener!= null){
                mItemGalleryClickListener.onItemGalleryClickListener(getAdapterPosition(), mId, mGalleryList);
            }
        }

        public interface ItemGalleryClickListener {
            void onItemGalleryClickListener (int position, int id, ArrayList<GalleryItemRes> galleryList);
        }
    }
}
