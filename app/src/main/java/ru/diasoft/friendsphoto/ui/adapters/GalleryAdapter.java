package ru.diasoft.friendsphoto.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.diasoft.friendsphoto.R;
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
        //mContext = viewGroup.getContext();
        return new GalleryAdapter.ViewHolder(view, mGalleryList, mItemGalleryClickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.ViewHolder holder, int i) {
        GalleryItemRes item = mGalleryList.get(i);
     //   holder.mId = item.getId();
        Picasso.with(mContext)
                .load(item.getPhoto604())
                .placeholder(mContext.getResources().getDrawable(R.drawable.camera_50))
                .error(mContext.getResources().getDrawable(R.drawable.camera_50))
                .into(holder.mGalleryImage);
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
