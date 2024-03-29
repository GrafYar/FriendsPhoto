package ru.diasoft.friendsphoto.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import java.util.ArrayList;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.managers.DataManager;
import ru.diasoft.friendsphoto.network.resources.GalleryItemRes;
import ru.diasoft.friendsphoto.storage.models.PhotoDTO;
import ru.diasoft.friendsphoto.ui.adapters.GalleryAdapter;
import ru.diasoft.friendsphoto.ui.adapters.MainAdapter;
import ru.diasoft.friendsphoto.ui.fragments.GalleryFragment;
import ru.diasoft.friendsphoto.ui.fragments.MainFragment;
import ru.diasoft.friendsphoto.utils.ConstantManager;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements MainAdapter.ViewHolder.ItemClickListener,
        GalleryAdapter.ViewHolder.ItemGalleryClickListener{

    private static final String TAG = ConstantManager.TAG_PREFIX + " MainActivity";
    private DataManager mDataManager;
    ValueCallback<Boolean> mValueCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataManager = DataManager.getInstance(this);
        setActionBarTitle(getString(R.string.app_title), false);

        loadData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setActionBarTitle(getString(R.string.app_title), false);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                break;
        }
        onBackPressed();
        return true;
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
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }


    private void loadData(){
        Fragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
    }

    @Override
    public void onItemClickListener(int position, int id, String fullName) {
        Log.d("Position", Integer.toString(position));
        Fragment fragment = new GalleryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantManager.FRIEND_ID, id);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(null).commit();
        setActionBarTitle(fullName, true);
    }

    @Override
    public void onItemGalleryClickListener(int position, int id, ArrayList<GalleryItemRes> galleryList) {
        PhotoDTO photoDTO = new PhotoDTO(galleryList.get(position));
        Intent intent = new Intent(this, PhotoGalleryActivity.class);
        intent.putExtra(ConstantManager.PARCELABLE_KEY, photoDTO);
        startActivity(intent);
    }

    /**
     *  Enables back button in action bar and set title
     * @param title - text to action bar
     * @param bool - true enables back button in action bar
     */
    public void setActionBarTitle(String title, boolean bool) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(bool);
            getSupportActionBar().setHomeButtonEnabled(bool);
        }
    }
}
