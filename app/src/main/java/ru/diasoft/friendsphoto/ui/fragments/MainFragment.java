package ru.diasoft.friendsphoto.ui.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.network.resources.FriendsListRes;
import ru.diasoft.friendsphoto.network.services.RetrofitService;
import ru.diasoft.friendsphoto.ui.activities.LoginActivity;


public class MainFragment extends Fragment {

//    private OnFragmentInteractionListener mListener;
private static final String TAG =  " MainFragment";
    private RecyclerView mRecyclerView;
    private static final int REQUEST_CODE = 200;


    public MainFragment() {
        // Required empty public constructor
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

        RetrofitService.getInstance()
                .getJSONApi()
                .getFriendsJson("5.52","6878d93736e2cb520adc4a97fcbecfa6f60e7cff8eec624c5ac36fe9b5edcca99bef7d8b841120f968506") //неверный
            //    .getFriendsJson("5.52","3325c48142a670e42db0fcc817d7fd46351d5e5511951214bac6cb77c70d31af97c0caa0f0ab6c88bd1f2")
                .enqueue(new Callback<FriendsListRes>() {
                    @Override
                    public void onResponse(Call<FriendsListRes> call, Response<FriendsListRes> response) {
                        try {
                            if (response.code() != REQUEST_CODE || response.body().getResponse() == null) {
//                                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                                startActivity(intent);

                            Resources resources = getContext().getResources();
                            String clientId = resources.getString(R.string.client_id);
                         //   Intent intent = LoginActivity.createAuthActivityIntent(getContext(), clientId);
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivityForResult(intent, REQUEST_CODE);
                            }

//                            ArrayList<ArrayList<ItemList>> listAll = new ArrayList<>();
//
//                            ArrayList<ItemList> shares = new ArrayList<ItemList>(response.body().getData().getBlocks().getShares().getList());
//                            ArrayList<ItemList> category = new ArrayList<ItemList>(response.body().getData().getBlocks().getCategories());
//                            ArrayList<ItemList> catalog = new ArrayList<ItemList>(response.body().getData().getBlocks().getCatalog());
//                            String catalogCount = response.body().getData().getCatalogCount();
//
//                            mTitleApp = response.body().getData().getTitle();
//                            mTitleImageURL = response.body().getData().getImage();
//
//                            ((MainActivity) getActivity())
//                                    .setActionBarTitle(mTitleApp);
//                            ((MainActivity) getActivity())
//                                    .setActionBarImage(mTitleImageURL);
//
//                            listAll.add(shares);
//                            listAll.add(category);
//                            listAll.add(catalog);

//                            LinearLayoutManager layoutManager
//                                    = new LinearLayoutManager(getContext());
//                            mRecyclerView.setLayoutManager(layoutManager);
//
//                            MainAdapter mainAdapter = new MainAdapter(getContext(), listAll, shares, category, catalog, catalogCount,
//                                    mCatalogItemClickListener,mCatalogButtonMoreClickListener, mCategoryItemClickListener, mSharesItemClickListener, mSharesButtonMoreClickListener);
//
//                            mRecyclerView.setAdapter(mainAdapter);
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
//        if (data == null) {return;}
//        String name = data.getStringExtra("name");
        String token = data.getStringExtra(LoginActivity.ACCESS_TOKEN);
        Toast.makeText(getContext(), token, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = getView().findViewById(R.id.friends_list);
    }

//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
