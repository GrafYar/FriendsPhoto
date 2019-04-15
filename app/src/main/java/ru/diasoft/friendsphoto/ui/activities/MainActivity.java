package ru.diasoft.friendsphoto.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.network.resources.GalleryItemRes;
import ru.diasoft.friendsphoto.storage_models.PhotoDTO;
import ru.diasoft.friendsphoto.ui.adapters.GalleryAdapter;
import ru.diasoft.friendsphoto.ui.adapters.MainAdapter;
import ru.diasoft.friendsphoto.ui.fragments.GalleryFragment;
import ru.diasoft.friendsphoto.ui.fragments.LoginFragment;
import ru.diasoft.friendsphoto.ui.fragments.MainFragment;
import ru.diasoft.friendsphoto.utils.ConstantManager;
import ru.diasoft.friendsphoto.utils.NetworkStatusChecker;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MainAdapter.ViewHolder.ItemClickListener,
        GalleryAdapter.ViewHolder.ItemGalleryClickListener{

    Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
    }


    private void loadData(){
        if(NetworkStatusChecker.isNetworkAvailable(this)) {
            //    Fragment fragment = new MainFragment();
            Fragment fragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment).commit();

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Нет подключения!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onItemClickListener(int position, int id) {
      //  Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, Integer.toString(position), Toast.LENGTH_SHORT).show();
        Log.d("Position", Integer.toString(position));
        Fragment fragment = new GalleryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantManager.FRIEND_ID, id);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onItemGalleryClickListener(int position, int id, ArrayList<GalleryItemRes> galleryList) {
     //   galleryList.get(position).
        PhotoDTO photoDTO = new PhotoDTO(galleryList.get(position));
        Intent intent = new Intent(this, PhotoGalleryActivity.class);
        intent.putExtra(ConstantManager.PARCELABLE_KEY, photoDTO);
        startActivity(intent);
    }
}
