package ru.diasoft.friendsphoto.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.managers.DataManager;
import ru.diasoft.friendsphoto.network.resources.FriendsListRes;
import ru.diasoft.friendsphoto.network.resources.FriendsItemRes;
import ru.diasoft.friendsphoto.network.services.RetrofitService;
import ru.diasoft.friendsphoto.storage.models.Friend;
import ru.diasoft.friendsphoto.storage.models.FriendDao;
import ru.diasoft.friendsphoto.ui.activities.LoginActivity;
import ru.diasoft.friendsphoto.ui.activities.MainActivity;
import ru.diasoft.friendsphoto.ui.activities.StartActivity;
import ru.diasoft.friendsphoto.ui.adapters.MainAdapter;
import ru.diasoft.friendsphoto.utils.NetworkStatusChecker;


public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

//    private OnFragmentInteractionListener mListener;
    private static final String TAG =  " MainFragment";
    private static final String ACCESS_DENIED = "access_denied";
    private RecyclerView mRecyclerView;
    private static final int REQUEST_CODE = 100;
    private DataManager mDataManager;
    private MainAdapter.ViewHolder.ItemClickListener mItemClickListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FriendDao mFriendDao;
    ValueCallback<Boolean> mValueCallback;

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
//        mDataManager = DataManager.getInstance();
//        loadFriends();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mDataManager = DataManager.getInstance(getContext());
        mFriendDao = mDataManager.getDaoSession().getFriendDao();
        loadFriends();
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(
                  Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return rootView;
    }

    private void loadFriends() {

     //   mRecyclerView.setAdapter(new MainAdapter());
        String token = mDataManager.getPreferencesManager().loadUserToken();
        String fields = "id,first_name,last_name,photo_100,online";

        if(NetworkStatusChecker.isNetworkAvailable(getContext())) {
            //    Fragment fragment = new MainFragment();


            RetrofitService.getInstance()
                    .getJSONApi()
                   // .getFriendsJson("5.59","6878d93736e2cb520adc4a97fcbecfa6f60e7cff8eec624c5ac36fe9b5edcca99bef7d8b841120f968506", fields) //неверный
                    .getFriendsJson("5.59",token, fields)
                    .enqueue(new Callback<FriendsListRes>() {
                        @Override
                        public void onResponse(Call<FriendsListRes> call, Response<FriendsListRes> response) {
                            try {
                                if (response.code() != 200 || response.body().getResponse() == null) {
                                    logout();
                                }
                                else {

                                    List<Friend> allFriends = new ArrayList<>();

                                    for (FriendsItemRes friendsItemRes: response.body().getResponse().getItems()) {
                                        allFriends.add(new Friend(friendsItemRes));
                                    }

                                    mFriendDao.insertOrReplaceInTx(allFriends);
                                    List<Friend> friendsList = new ArrayList<>();



                                    LoadFromDBTask loadFromDBTask = new LoadFromDBTask(getActivity(), mItemClickListener);
                                    loadFromDBTask.execute();

                                }

                            } catch (NullPointerException e) {
                                Log.e(TAG, e.toString());
                            }
                        }
                        @Override
                        public void onFailure(Call<FriendsListRes> call, Throwable t) {
                            Log.e(TAG, t.toString());
                        }
                    });
        } else {
            Toast.makeText(getContext(),
                    "Нет подключения!", Toast.LENGTH_SHORT).show();
            LoadFromDBTask loadFromDBTask = new LoadFromDBTask(getActivity(), mItemClickListener);
            loadFromDBTask.execute();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String token = data.getStringExtra(LoginActivity.ACCESS_TOKEN);
                if (token.equals(ACCESS_DENIED)) {
                    if (getActivity()!= null) {
                        getActivity().finish();
                    }
                }
                else {
                    Toast.makeText(getContext(), token, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, token);
                    mDataManager.getPreferencesManager().saveUserToken(token);
                    loadFriends();
                }
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.friends_list);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mItemClickListener = null;
    }

    @Override
    public void onRefresh() {
        loadFriends();
     //   mSwipeRefreshLayout.setRefreshing(false);
    }

    static class LoadFromDBTask extends AsyncTask<Void, Void, Void> {
        private List<Friend> mFriendsList;
        private MainAdapter.ViewHolder.ItemClickListener mItemClickListener;

        private WeakReference<FragmentActivity> activityReference;

        // only retain a weak reference to the activity
        LoadFromDBTask(FragmentActivity context, MainAdapter.ViewHolder.ItemClickListener itemClickListener) {
            activityReference = new WeakReference<>(context);
            mItemClickListener = itemClickListener;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DataManager mDataManager = DataManager.getInstance(activityReference.get());
            mFriendsList = mDataManager.getFriendListFromDb();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            FragmentActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            RecyclerView recyclerView = activity.findViewById(R.id.friends_list);
            SwipeRefreshLayout mSwipeRefreshLayout = activity.findViewById(R.id.swipe_refresh_layout);

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(layoutManager);

            MainAdapter mainAdapter = new MainAdapter(activity, mFriendsList, mItemClickListener);
            recyclerView.setAdapter(mainAdapter);

            mSwipeRefreshLayout.setRefreshing(false);

        }
    }

    public void logout() {
        mDataManager.getPreferencesManager().saveUserToken(null);
        mDataManager.getPreferencesManager().saveUserPinCode(null);
        mDataManager.getDaoSession().getFriendDao().deleteAll();
        android.webkit.CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(mValueCallback);
        Intent intent = new Intent(getContext(), StartActivity.class);
        startActivity(intent);
        if(getActivity() != null) {
            getActivity().finish();
        }
    }

}
