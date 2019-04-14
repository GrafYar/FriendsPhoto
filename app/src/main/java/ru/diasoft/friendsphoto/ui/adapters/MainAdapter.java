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
import ru.diasoft.friendsphoto.network.resources.ItemRes;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{
    private Context mContext;
    ArrayList<ItemRes> mFriendsList;

    public MainAdapter (Context context, ArrayList<ItemRes> friendsList){
        this.mContext = context;
        this.mFriendsList = friendsList;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend,viewGroup,false);
        //mContext = viewGroup.getContext();
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ItemRes item = mFriendsList.get(i);
        Picasso.with(mContext)
                .load(item.getPhoto50())
              //  .error(mContext.getResources().getDrawable(R.drawable.placeholder))
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
        private ItemClickListener mItemClickListener;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener!= null){
                mItemClickListener.onItemClickListener(getAdapterPosition());
            }
        }

        public interface ItemClickListener {
            void onItemClickListener (int position);
        }
    }
}
