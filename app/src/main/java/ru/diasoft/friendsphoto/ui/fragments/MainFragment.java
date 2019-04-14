package ru.diasoft.friendsphoto.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.managers.DataManager;
import ru.diasoft.friendsphoto.network.resources.FriendsListRes;
import ru.diasoft.friendsphoto.network.resources.ItemRes;
import ru.diasoft.friendsphoto.network.services.RetrofitService;
import ru.diasoft.friendsphoto.ui.activities.LoginActivity;
import ru.diasoft.friendsphoto.ui.adapters.MainAdapter;


public class MainFragment extends Fragment {

//    private OnFragmentInteractionListener mListener;
    private static final String TAG =  " MainFragment";
    private static final String ACCESS_DENIED = "access_denied";
    private RecyclerView mRecyclerView;
    private static final int REQUEST_CODE = 200;
    private DataManager mDataManager;
    private MainAdapter.ViewHolder.ItemClickListener mItemClickListener;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainAdapter.ViewHolder.ItemClickListener) {
            mItemClickListener = (MainAdapter.ViewHolder.ItemClickListener) context;
        }
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataManager = DataManager.getInstance();
        loadFriends();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
      //  loadFriends();
        return rootView;
    }

    private void loadFriends() {

     //   mRecyclerView.setAdapter(new MainAdapter());
        String token = mDataManager.getPreferencesManager().loadUserToken();
        String fields = "id,first_name, last_name, photo_50, online";

        RetrofitService.getInstance()
                .getJSONApi()
             //   .getFriendsJson("5.59","6878d93736e2cb520adc4a97fcbecfa6f60e7cff8eec624c5ac36fe9b5edcca99bef7d8b841120f968506") //неверный
            //    .getFriendsJson("5.52","3325c48142a670e42db0fcc817d7fd46351d5e5511951214bac6cb77c70d31af97c0caa0f0ab6c88bd1f2")
                .getFriendsJson("5.59",token, fields)
                .enqueue(new Callback<FriendsListRes>() {
                    @Override
                    public void onResponse(Call<FriendsListRes> call, Response<FriendsListRes> response) {
                        try {
                            if (response.code() != REQUEST_CODE || response.body().getResponse() == null) {

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivityForResult(intent, REQUEST_CODE);
                            }

                            ArrayList<ItemRes> friendsList = new ArrayList<>(response.body().getResponse().getItems());


//
//                            ((MainActivity) getActivity())
//                                    .setActionBarTitle(mTitleApp);
//                            ((MainActivity) getActivity())
//                                    .setActionBarImage(mTitleImageURL);

                            LinearLayoutManager layoutManager
                                    = new LinearLayoutManager(getContext());
                            mRecyclerView.setLayoutManager(layoutManager);

                            MainAdapter mainAdapter = new MainAdapter(getContext(), friendsList, mItemClickListener);
                            mRecyclerView.setAdapter(mainAdapter);

                        } catch (NullPointerException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<FriendsListRes> call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String token = data.getStringExtra(LoginActivity.ACCESS_TOKEN);
                if (token.equals(ACCESS_DENIED)) {
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getContext(), token, Toast.LENGTH_SHORT).show();
                    mDataManager.getPreferencesManager().saveUserToken(token);
                    loadFriends();
                }
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = getView().findViewById(R.id.friends_list);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mItemClickListener = null;
    }

}
