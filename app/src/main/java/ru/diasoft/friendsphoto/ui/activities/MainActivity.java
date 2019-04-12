package ru.diasoft.friendsphoto.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.ui.fragments.LoginFragment;
import ru.diasoft.friendsphoto.ui.fragments.MainFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
    }
}
