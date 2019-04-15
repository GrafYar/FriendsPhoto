package ru.diasoft.friendsphoto.ui.adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.network.resources.FriendsItemRes;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<FriendsItemRes> mFriendsList;
    private ViewHolder.ItemClickListener mItemClickListener;

    public MainAdapter (Context context, ArrayList<FriendsItemRes> friendsList, ViewHolder.ItemClickListener itemClickListener){
        this.mContext = context;
        this.mFriendsList = friendsList;
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend,viewGroup,false);
        //mContext = viewGroup.getContext();
        return new ViewHolder(view, mItemClickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        FriendsItemRes item = mFriendsList.get(i);
        holder.mId = item.getId();
        Picasso.with(mContext)
                .load(item.getPhoto50())
                .placeholder(mContext.getResources().getDrawable(R.drawable.camera_50))
                .error(mContext.getResources().getDrawable(R.drawable.camera_50))
                .into(holder.mFriendImage);
        String fullName = item.getFirstName() + " " + item.getLastName();
        holder.mFriendName.setText(fullName);
    }

    @Override
    public int getItemCount() {
        return mFriendsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.friend_image) CircleImageView mFriendImage;
        @BindView(R.id.friend_name) TextView mFriendName;
        int mId;
        private ItemClickListener mItemClickListener;

        private ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mItemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener!= null){
                mItemClickListener.onItemClickListener(getAdapterPosition(), mId);
            }
        }

        public interface ItemClickListener {
            void onItemClickListener (int position, int id);
        }
    }
}
