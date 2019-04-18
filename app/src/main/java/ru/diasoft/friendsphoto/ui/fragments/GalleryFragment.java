package ru.diasoft.friendsphoto.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.managers.DataManager;
import ru.diasoft.friendsphoto.network.resources.GalleryItemRes;
import ru.diasoft.friendsphoto.network.resources.GalleryListRes;
import ru.diasoft.friendsphoto.network.services.RetrofitService;
import ru.diasoft.friendsphoto.ui.adapters.GalleryAdapter;
import ru.diasoft.friendsphoto.utils.ConstantManager;

/**
 * Fragment to load and show photos from current friend
 */
public class GalleryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = ConstantManager.TAG_PREFIX + " GalleryFragment";
    private DataManager mDataManager;
    private RecyclerView mRecyclerView;
    private ArrayList<GalleryItemRes> mGalleryList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String mId;
    private GalleryAdapter.ViewHolder.ItemGalleryClickListener mItemGalleryClickListener;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        // Taking friend id for request
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mId = Integer.toString(bundle.getInt(ConstantManager.FRIEND_ID));
        }

        //Taking DataManager and initialising Swipe Refresher
        mDataManager = DataManager.getInstance(getContext());
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_photo_layout);
        mSwipeRefreshLayout.setColorSchemeColors(
                Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        loadGallery();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.friends_gallery);
        }
    }

    /**
     * Loads photos from VK by retrofit request with params:
     * Version - version of API VK
     * Token - key for request
     * Friend Id - id friend for loading his photo
     * album id - type of albums to load
     */
    private void loadGallery() {

        String token = mDataManager.getPreferencesManager().loadUserToken();
        mSwipeRefreshLayout.setRefreshing(true);

        // Loading photos by Retrofit
        RetrofitService.getInstance()
                .getJSONApi()
                .getGalleryJson(ConstantManager.VERSION, token, mId, ConstantManager.ALBUM_ID)
                .enqueue(new Callback<GalleryListRes>() {
                    @Override
                    public void onResponse(@NonNull Call<GalleryListRes> call, @NonNull Response<GalleryListRes> response) {
                        try {
                            if(response.body()!=null){
                                mGalleryList = new ArrayList<>(response.body().getResponse().getItems());
                            }
                            // Creating layoutManager and gives it to RecyclerView
                            GridLayoutManager layoutManager
                                    = new GridLayoutManager(getContext(),2);
                            mRecyclerView.setLayoutManager(layoutManager);
                            // Creating Adapter and giving it list of photos and ClickListener
                            GalleryAdapter galleryAdapter = new GalleryAdapter(getContext(), mGalleryList, mItemGalleryClickListener);
                            mRecyclerView.setAdapter(galleryAdapter);
                            // Switch of SwipeRefresh
                            mSwipeRefreshLayout.setRefreshing(false);
                        } catch (NullPointerException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<GalleryListRes> call, @NonNull Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mItemGalleryClickListener = null;
    }
}
