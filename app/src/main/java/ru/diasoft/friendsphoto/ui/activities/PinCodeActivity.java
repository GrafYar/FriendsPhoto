package ru.diasoft.friendsphoto.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.managers.DataManager;

/**
 * Class for entering or creating pin code for fast auth
 */
public class PinCodeActivity extends AppCompatActivity {

    @BindView(R.id.first_label) TextView mFirstLabel;
    @BindView(R.id.second_label) TextView mSecondLabel;
    @BindView(R.id.first_value) EditText mFirstValue;
    @BindView(R.id.second_value) EditText mSecondValue;
    DataManager mDataManager;
    String mPinCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);
        ButterKnife.bind(this);

        mDataManager = DataManager.getInstance(this);
        mPinCode = mDataManager.getPreferencesManager().loadUserPinCode();
        
        if (mPinCode == null) {
            createPinCode();
        } else {
            checkPinCode();
        }
    }

    /**
     * Creates new pin code for new user
     */
    private void createPinCode() {
        mFirstLabel.setVisibility(View.VISIBLE);
        mFirstLabel.setText(getString(R.string.create_pin));
        mFirstValue.setVisibility(View.VISIBLE);

        mSecondLabel.setVisibility(View.VISIBLE);
        mSecondLabel.setText(getString(R.string.repeat_create_pin));
        mSecondValue.setVisibility(View.VISIBLE);

        // Check is full first edit text
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
        // Check is full second edit text
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

    /**
     * Checks existing pin code
     */
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
