package ru.diasoft.friendsphoto.ui.fragments;

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
import ru.diasoft.friendsphoto.ui.activities.StartActivity;
import ru.diasoft.friendsphoto.ui.adapters.MainAdapter;
import ru.diasoft.friendsphoto.utils.ConstantManager;
import ru.diasoft.friendsphoto.utils.NetworkStatusChecker;

/**
 * Fragment to load and show list of friends
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String  TAG = ConstantManager.TAG_PREFIX + " MainFragment";
    private DataManager mDataManager;
    private MainAdapter.ViewHolder.ItemClickListener mItemClickListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FriendDao mFriendDao;
    ValueCallback<Boolean> mValueCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainAdapter.ViewHolder.ItemClickListener) {
            mItemClickListener = (MainAdapter.ViewHolder.ItemClickListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mDataManager = DataManager.getInstance(getContext());
        mFriendDao = mDataManager.getDaoSession().getFriendDao();
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(
                  Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        loadFriends();

        return rootView;
    }

    /**
     * Loads photos from VK by retrofit request with params:
     * Version - version of API VK
     * Token - key for request
     * Fields - fields to request from server
     */
    private void loadFriends() {
        String token = mDataManager.getPreferencesManager().loadUserToken();
        mSwipeRefreshLayout.setRefreshing(true);
        if (getContext()!=null) {
            if (NetworkStatusChecker.isNetworkAvailable(getContext())) {
                // Loading photos by Retrofit
                RetrofitService.getInstance()
                        .getJSONApi()
                        .getFriendsJson(ConstantManager.VERSION, token, ConstantManager.FIELDS)
                        .enqueue(new Callback<FriendsListRes>() {
                            @Override
                            public void onResponse(@NonNull Call<FriendsListRes> call, @NonNull Response<FriendsListRes> response) {
                                try {
                                    if (response.code() != 200 || response.body().getResponse() == null) {
                                        logout();
                                    } else {
                                        List<Friend> allFriends = new ArrayList<>();

                                        // Adding list of friends from response in friends entity and insert it in db
                                        for (FriendsItemRes friendsItemRes : response.body().getResponse().getItems()) {
                                            allFriends.add(new Friend(friendsItemRes));
                                        }
                                        mFriendDao.insertOrReplaceInTx(allFriends);

                                        //Creating async task for load friends from db
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mItemClickListener = null;
    }

    @Override
    public void onRefresh() {
        loadFriends();
    }

    /**
     * Class AsyncTask for async load friends from db
     */
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
            SwipeRefreshLayout swipeRefreshLayout = activity.findViewById(R.id.swipe_refresh_layout);
            // Creating layoutManager and gives it to RecyclerView
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(layoutManager);
            // Creating Adapter and giving it list of friends and ClickListener
            MainAdapter mainAdapter = new MainAdapter(activity, mFriendsList, mItemClickListener);
            recyclerView.setAdapter(mainAdapter);
            // Switch of SwipeRefresh
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * Deletes token, db, pincode, coockies and starts StartActivity
     */
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
