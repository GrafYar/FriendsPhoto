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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.network.resources.FriendsItemRes;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.diasoft.friendsphoto.storage.models.Friend;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{
    private Context mContext;
    private List<Friend> mFriendsList;
    private ViewHolder.ItemClickListener mItemClickListener;

    public MainAdapter (Context context, List<Friend> friendsList, ViewHolder.ItemClickListener itemClickListener){
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
        Friend item = mFriendsList.get(i);
        holder.mId = item.getFriendId();
        Picasso.with(mContext)
                .load(item.getPhoto())
                .placeholder(mContext.getResources().getDrawable(R.drawable.camera_50))
                .error(mContext.getResources().getDrawable(R.drawable.camera_50))
                .into(holder.mFriendImage);
        holder.mFullName = item.getFirstName() + " " + item.getLastName();
        holder.mFriendName.setText(holder.mFullName);
    }

    @Override
    public int getItemCount() {
        return mFriendsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.friend_image) CircleImageView mFriendImage;
        @BindView(R.id.friend_name) TextView mFriendName;
        int mId;
        String mFullName;
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
                mItemClickListener.onItemClickListener(getAdapterPosition(), mId, mFullName);
            }
        }

        public interface ItemClickListener {
            void onItemClickListener (int position, int id, String fullName);
        }
    }
}
