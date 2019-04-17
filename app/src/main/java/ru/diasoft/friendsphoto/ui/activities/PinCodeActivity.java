package ru.diasoft.friendsphoto.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.managers.DataManager;
import ru.diasoft.friendsphoto.utils.ConstantManager;

public class PinCodeActivity extends AppCompatActivity {

    @BindView(R.id.first_label) TextView mFirstLabel;
    @BindView(R.id.second_label) TextView mSecondLabel;
    @BindView(R.id.third_label) TextView mThirdLabel;
    @BindView(R.id.first_value) EditText mFirstValue;
    @BindView(R.id.second_value) EditText mSecondValue;
    @BindView(R.id.third_value) EditText mThirdValue;
    DataManager mDataManager;
    String mPinCode;
    ValueCallback<Boolean> mValueCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);
        ButterKnife.bind(this);
 /*       android.webkit.CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(mValueCallback);*/
       /* android.webkit.CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
            // a callback which is executed when the cookies have been removed
            @Override
            public void onReceiveValue(Boolean aBoolean) {
                Log.d("Cookies", "Cookie removed: " + aBoolean);
            }
        });*/

        mDataManager = DataManager.getInstance(this);
        mPinCode = mDataManager.getPreferencesManager().loadUserPinCode();
        
        if (mPinCode == null) {
            createPinCode();
        } else {
            checkPinCode();
        }
    }

    private void createPinCode() {
        mFirstLabel.setVisibility(View.VISIBLE);
        mFirstLabel.setText(getString(R.string.create_pin));
        mFirstValue.setVisibility(View.VISIBLE);

        mSecondLabel.setVisibility(View.VISIBLE);
        mSecondLabel.setText(getString(R.string.repeat_create_pin));
        mSecondValue.setVisibility(View.VISIBLE);

        mFirstValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (mSecondValue.length()== 4 && s.length() == 4) {
                    if(mFirstValue.getText().toString().equals(mSecondValue.getText().toString())) {
                        mDataManager.getPreferencesManager().saveUserPinCode(String.valueOf(mFirstValue.getText()));
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(),"Не совпадает PIN",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mSecondValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() == 4) {
                    if(mFirstValue.getText().toString().equals(mSecondValue.getText().toString())) {
                        mDataManager.getPreferencesManager().saveUserPinCode(String.valueOf(mFirstValue.getText()));
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(),"Не совпадает PIN",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void checkPinCode() {
        mFirstLabel.setVisibility(View.VISIBLE);
        mFirstLabel.setText(getString(R.string.enter_pin));
        mFirstValue.setVisibility(View.VISIBLE);

        mFirstValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() == 4) {
                    if (mPinCode.equals(mFirstValue.getText().toString())) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(),"Неверный PIN",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
