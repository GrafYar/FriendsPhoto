package ru.diasoft.friendsphoto.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.network.resources.FriendsListRes;
import ru.diasoft.friendsphoto.network.services.RetrofitService;
import ru.diasoft.friendsphoto.ui.adapters.MainAdapter;
import ru.diasoft.friendsphoto.utils.ConstantManager;


public class MainFragment extends Fragment {

//    private OnFragmentInteractionListener mListener;
private static final String TAG =  " MainFragment";
    private RecyclerView mRecyclerView;


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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        loadFriends();
        return rootView;
    }

    private void loadFriends() {

     //   mRecyclerView.setAdapter(new MainAdapter());

        RetrofitService.getInstance()
                .getJSONApi()
                .getMainJson("5.52","6878d93736e2cb520adc4a97fcbecfa6f60e7cff8eec624c5ac36fe9b5edcca99bef7d8b841120f968506")
                .enqueue(new Callback<FriendsListRes>() {
                    @Override
                    public void onResponse(Call<FriendsListRes> call, Response<FriendsListRes> response) {
                        try {

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
