package ru.diasoft.friendsphoto.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.managers.DataManager;
import ru.diasoft.friendsphoto.network.resources.FriendsListRes;
import ru.diasoft.friendsphoto.network.resources.GalleryItemRes;
import ru.diasoft.friendsphoto.network.resources.GalleryListRes;
import ru.diasoft.friendsphoto.network.services.RetrofitService;
import ru.diasoft.friendsphoto.ui.activities.LoginActivity;
import ru.diasoft.friendsphoto.ui.adapters.GalleryAdapter;
import ru.diasoft.friendsphoto.ui.adapters.MainAdapter;
import ru.diasoft.friendsphoto.utils.ConstantManager;

public class GalleryFragment extends Fragment {

    private static final int REQUEST_CODE = 200;
    private static final String TAG =  " GalleryFragment";
    private DataManager mDataManager;
    private RecyclerView mRecyclerView;
    private ArrayList<GalleryItemRes> mGalleryList;
    private String mId;
    private GalleryAdapter.ViewHolder.ItemGalleryClickListener mItemGalleryClickListener;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GalleryAdapter.ViewHolder.ItemGalleryClickListener) {
            mItemGalleryClickListener = (GalleryAdapter.ViewHolder.ItemGalleryClickListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mId = Integer.toString(bundle.getInt(ConstantManager.FRIEND_ID));
            Log.d("ID ", mId);
            Toast.makeText(getActivity(), mId, Toast.LENGTH_SHORT).show();
        }
        mDataManager = DataManager.getInstance();
        loadGallery();
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = getView().findViewById(R.id.friends_gallery);
    }

    private void loadGallery() {

        //   mRecyclerView.setAdapter(new MainAdapter());
        String token = mDataManager.getPreferencesManager().loadUserToken();
        String albumId = "profile";

        RetrofitService.getInstance()
                .getJSONApi()
                //   .getFriendsJson("5.59","6878d93736e2cb520adc4a97fcbecfa6f60e7cff8eec624c5ac36fe9b5edcca99bef7d8b841120f968506") //неверный
                //    .getFriendsJson("5.52","3325c48142a670e42db0fcc817d7fd46351d5e5511951214bac6cb77c70d31af97c0caa0f0ab6c88bd1f2")
                .getGalleryJson("5.59",token, mId, albumId)
                .enqueue(new Callback<GalleryListRes>() {
                    @Override
                    public void onResponse(Call<GalleryListRes> call, Response<GalleryListRes> response) {
                        try {
                            if (response.code() != 200 || response.body().getResponse() == null) {

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivityForResult(intent, REQUEST_CODE);
                            }

                            mGalleryList = new ArrayList<>(response.body().getResponse().getItems());


//                            ((MainActivity) getActivity())
//                                    .setActionBarTitle(mTitleApp);
//                            ((MainActivity) getActivity())
//                                    .setActionBarImage(mTitleImageURL);

                            GridLayoutManager layoutManager
                                    = new GridLayoutManager(getContext(),2);
                            mRecyclerView.setLayoutManager(layoutManager);

                            GalleryAdapter galleryAdapter = new GalleryAdapter(getContext(), mGalleryList, mItemGalleryClickListener);
                            mRecyclerView.setAdapter(galleryAdapter);

                        } catch (NullPointerException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<GalleryListRes> call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mItemGalleryClickListener = null;
    }
}
