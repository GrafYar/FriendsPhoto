package ru.diasoft.friendsphoto.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ru.diasoft.friendsphoto.managers.DataManager;

/**
 * Class of first activity for switching auth (fast or login)
 */
public class StartActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private static final String ACCESS_DENIED = "access_denied";
    DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataManager = DataManager.getInstance(this);
        String token = mDataManager.getPreferencesManager().loadUserToken();
        if (token == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Intent intent = new Intent(this, PinCodeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String token = data.getStringExtra(LoginActivity.ACCESS_TOKEN);
                if (token.equals(ACCESS_DENIED)) {
                    finish();
                }
                else {
                    mDataManager.getPreferencesManager().saveUserToken(token);
                    Intent intent = new Intent(this, PinCodeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}
