package ru.diasoft.friendsphoto.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.ui.adapters.MainAdapter;
import ru.diasoft.friendsphoto.ui.fragments.LoginFragment;
import ru.diasoft.friendsphoto.ui.fragments.MainFragment;
import ru.diasoft.friendsphoto.utils.NetworkStatusChecker;

import android.util.Log;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements MainAdapter.ViewHolder.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(NetworkStatusChecker.isNetworkAvailable(this)) {
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
    public void onItemClickListener(int position) {
        Toast.makeText(this, Integer.toString(position), Toast.LENGTH_SHORT).show();
        Log.d("Position", Integer.toString(position));
    }
}
