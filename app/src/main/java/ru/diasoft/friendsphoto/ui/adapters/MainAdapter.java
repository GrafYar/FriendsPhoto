package ru.diasoft.friendsphoto.ui.adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.diasoft.friendsphoto.R;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;

    public MainAdapter (){
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend,viewGroup,false);
        mContext = viewGroup.getContext();
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ItemClickListener mItemClickListener;
        private ViewHolder(View itemView) {
            super(itemView);
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
