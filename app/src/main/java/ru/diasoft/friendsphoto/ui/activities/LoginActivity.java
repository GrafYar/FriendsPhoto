package ru.diasoft.friendsphoto.ui.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.net.URI;

import ru.diasoft.friendsphoto.R;
import ru.diasoft.friendsphoto.network.services.AuthorizationListener;

public class LoginActivity extends AppCompatActivity implements AuthorizationListener {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String AUTH_ERROR = "error";

    private String mClientId;
    private String mDisplay;
    private String mRedirectUri;
    private String mScope;
    private String mResponseType;
    private String mVersion;
    private String mAuthUrlTemplate;

    private WebView webView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        webView = findViewById(R.id.login_web_view);

        mClientId = getString(R.string.client_id);
        mDisplay = getString(R.string.display);
        mRedirectUri = getString(R.string.redirect_uri);
        mScope = getString(R.string.scope);
        mResponseType = getString(R.string.response_type);
        mVersion = getString(R.string.version);
        mAuthUrlTemplate = getString(R.string.auth_url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onAuthStarted();
        String url = String.format(mAuthUrlTemplate, mClientId, "&", mDisplay, "&", mRedirectUri, "&", mScope, "&", mResponseType, "&", mVersion);
        URI uri = URI.create(url);
        webView.setWebViewClient(new OAuthWebClient(this));
        webView.loadUrl(uri.toString());
    }

    @Override
    public void onAuthStarted() {
    }

    @Override
    public void onComplete(String token) {
        Intent intent = new Intent();
        intent.putExtra(ACCESS_TOKEN, token);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onError(String error) {
        Intent intent = new Intent();
        intent.putExtra(AUTH_ERROR, error);
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    private class OAuthWebClient extends WebViewClient {
        private AuthorizationListener listener;

        public OAuthWebClient(AuthorizationListener listener) {
            this.listener = listener;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @SuppressWarnings("deprecation") @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(view.getResources().getString(R.string.redirect_uri))) {
               String token = getAccessToken(url);
                if (token.contains("Error")) {
                    listener.onError(token);
                } else {
                    listener.onComplete(token);
                }
                return true;
            }
            return false;
        }

        @TargetApi(Build.VERSION_CODES.N) @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            if (url.startsWith(view.getResources().getString(R.string.redirect_uri))) {
                String token = getAccessToken(url);
                if (token.contains("Error")) {
                    listener.onError(token);
                } else {
                    listener.onComplete(token);
                }
                return true;
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            listener.onError(error.toString());
        }
    }

    private String getAccessToken(String response) {
        String[] params = response.split("&");
        return params[0].split("=")[1];
    }
}
